package wjx.skill.config.shiro;


import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import wjx.skill.enums.ResultEnum;
import wjx.skill.exception.SysException;
import wjx.skill.mapper.RoleMapper;
import wjx.skill.model.Role;
import wjx.skill.model.User;
import wjx.skill.service.UserService;

import java.util.*;

/**
 * Created by WangJX on 2018/10/29.
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Autowired
    private RoleMapper roleMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthRealm.class);


    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        logger.info("【授权操作】用户名为：{}，开始授权", user.getUsername());
        Integer userId = user.getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (!"wjx_admin".equals(user.getUsername())) {
            List<Role> roles = roleMapper.getByUserId(userId);
            Set<String> roleName = new HashSet<>();
            List<Integer> roleIds = new ArrayList<>();

            if (roles != null && !roles.isEmpty()) {
                roles.forEach((role) -> {
                    roleName.add(role.getRoleName());
                    roleIds.add(role.getId());
                });
                Set<String> menus = roleMapper.findResourcesByRoleIds(roleIds);

                if (!CollectionUtils.isEmpty(roleName)) {
                    logger.info("【授权操作】当前用户拥有的角色是：{}", JSONObject.toJSONString(roleName));
                    info.addRoles(roleName);
                }

                if (!CollectionUtils.isEmpty(menus)) {
                    info.addStringPermissions(menus);
                }
                logger.info("【授权操作】用户名为：{}，授权完成", user.getUsername());
            }
        } else {
            logger.info("【系统管理员授权操作】wjx_admin");
            Set<String> menus = roleMapper.queryAll();
            info.addStringPermissions(menus);
        }

        //}
        return info;
    }

    /**
     * 认证登录
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        //根据用户名查询密码，由安全管理器负责对比查询出的数据库中的密码和页面输入的密码是否一致
        User user = userService.findByUserName(username);
        logger.info("登录认证{}", JSONObject.toJSONString(user));
        if (user == null) {
            throw new SysException(ResultEnum.USER_NOT_EXIST);
        }

        // 单用户登录,获取当前已登录的用户session列表
        // 当用户重新登录时,踢掉前一个用户
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        for (Session session : sessions) {
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            if (null != spc) {
                obj = spc.getPrimaryPrincipal();
                // 在线的人
                User loginUser = (User) obj;
                if (username.equals(loginUser.getUsername())) {
                    // 如果发现有用户名一样的人，则删除
                    redisSessionDAO.delete(session);
                }
            }
        }

        //最后的比对需要交给安全管理器,三个参数进行初步的简单认证信息对象的包装,由安全管理器进行包装运行
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(), //密码
                this.getClass().getName()  //realm name
        );

        return authenticationInfo;
    }

    public void clearAuthz() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
