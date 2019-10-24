package wjx.skill.controller;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wjx.skill.dto.LoginDto;
import wjx.skill.dto.PageDto;
import wjx.skill.enums.ResultEnum;
import wjx.skill.exception.SysException;
import wjx.skill.model.User;
import wjx.skill.util.ResultUtil;
import wjx.skill.vo.ResultVo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/skill")
@Validated
public class HomeController {
    @Autowired
    RedisSessionDAO redisSessionDAO;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/t")
    public String tests() {

        return "test/11";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    //@PostMapping("/login")
    public ResultVo login(@RequestParam("username")String username, @RequestParam("password") String password){
        if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
            return ResultUtil.fail(ResultEnum.USERNAME_NULL);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        logger.info("【用户登录】:{}",username);
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            token.clear();
            throw new SysException(ResultEnum.USER_ERROR);
        }

        User user = (User) subject.getPrincipal();
        logger.info("【登录成功】");
        user.setPassword(null);
        Object tokens = subject.getSession().getId();
        LoginDto loginDto = new LoginDto();
        loginDto.setToken(tokens);
        return ResultUtil.success(loginDto);
    }

    /**
     * 未授权
     * @return
     */
    //@GetMapping("/unauthorized")
    public ResultVo unauthorized(){
        return ResultUtil.fail(ResultEnum.NO_PERMISSIONS);
    }

    /**
     * 退出登录
     * @return
     */
    //@DeleteMapping("/logout")
    public ResultVo logout(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
        obj = spc.getPrimaryPrincipal();
        User user = (User) obj;
        subject.logout();
        logger.info("{}已退出登录", JSON.toJSONString(user));
        return ResultUtil.success("退出登录");
    }

    /**
     * 未登录
     * @return
     */
    //@GetMapping("/unlogin")
    public ResultVo unlogin(){
        return ResultUtil.fail(ResultEnum.USER_SESSION_TIMEOUT);
    }

    /**
     * 在线人数
     * @return
     */
    //@GetMapping("/online")
    public ResultVo test(){
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        Map<String,String> username = new HashMap<>();
        int count = 0;
        for(Session session : sessions){
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            //String test = DefaultSubjectContext.PRINCIPALS_SESSION_KEY;
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            User user = (User) obj;
            if(null!=user){
                count++;
            }
           username.put(user.getUsername(),user.getName());
        }
        PageDto pageDto = new PageDto();
        pageDto.setTotal(count);

        return ResultUtil.success(pageDto);
    }
}
