package wjx.skill.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * Created by WangJX on 2018/10/29.
 */
@Configuration
public class ShiroConfiguration {


    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;



    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager主体
        bean.setSecurityManager(securityManager);
        // 未授权界面
        bean.setUnauthorizedUrl("/unauthorized");
        // 登录成功后要跳转的链接
        bean.setSuccessUrl("/index");
        bean.setLoginUrl("/login");
        /*
            配置哪些页面需要受保护.
                以及访问这些页面需要的权限.
                1). anon 可以被匿名访问
                2). authc 必须认证(即登录)后才可能访问的页面.
                3). logout 登出
                4). roles 角色过滤器
        */
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/admins","authc");
        //filterChainDefinitionMap.put("/users","anon");
        filterChainDefinitionMap.put("/unauthorized","anon");


        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");



        //全部允许访问
        filterChainDefinitionMap.put("/**","anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm, @Qualifier("sessionManager") DefaultWebSessionManager sessionManager){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm.
        manager.setRealm(authRealm);
        // 自定义缓存实现,集成redis
        // 注意:开发时请先关闭，如不关闭热启动会报错
        manager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        manager.setSessionManager(sessionManager);
        // 注入记住我管理器
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }




    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher")CredentialMatcher matcher){
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher(){
        return new CredentialMatcher();
    }


    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":6379");
        //redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        //redisManager.setPassword(password);
        redisManager.setDatabase(database);
        return redisManager;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        cacheManager.setPrincipalIdFieldName("username");
        return cacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean("redisSessionDao")
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     *前后端分离使用这个  需带token访问连接
     * 自定义的Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean("sessionManager")
    public MySessionManager sessionManager(){
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
//    /**
//     * Session Manager
//     * 使用的是shiro-redis开源插件
//     */
//    @Bean("sessionManager")
//    public DefaultWebSessionManager sessionManager(){
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        return sessionManager;
//    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return  creator;
    }

    /**
     * 使授权注解起作用
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 这里是为了能在html页面引用shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
