package wjx.skill.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.model.Role;
import wjx.skill.model.User;
import wjx.skill.service.RoleService;
import wjx.skill.service.UserService;
import wjx.skill.util.PasswordHelper;
import wjx.skill.util.RandomUtil;
import wjx.skill.vo.Response;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;


/**
 * @author WangJX
 * @date 2018/12/6 14:26
 */
@Controller
@Validated
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @GetMapping("/")
    public String root() {
        return "redirect:/skills";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/skills";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("count", online());
        return "login";
    }

    private Integer online() {
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        Map<String,String> username = new HashMap<>();
        int count = 0;
        for (Session session : sessions) {
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            //String test = DefaultSubjectContext.PRINCIPALS_SESSION_KEY;
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            if (null != spc) {
                obj = spc.getPrimaryPrincipal();
                User user = (User) obj;
                if (null != user) {
                    count++;
                }
                username.put(user.getUsername(),user.getName());
            }
        }
        logger.info("在线用户-{}",username);
        return count;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") @NotEmpty(message = "用户名不能为空！") String username, @RequestParam("password") @NotEmpty(message = "密码不能为空！") String password, String rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if (!StringUtils.isEmpty(rememberMe)) {
            token.setRememberMe(true);
        }
        Subject subject = SecurityUtils.getSubject();
        logger.info("【用户登录】:{}", username);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            token.clear();
            return "redirect:/login-error";
        }

        User user = (User) subject.getPrincipal();
        logger.info(JSONObject.toJSONString(user));
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        User user = null;
        try {
            Session session = subject.getSession();
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            user = (User) obj;
            subject.logout();
        } catch (Exception e) {
            return "redirect:/index";
        }
        logger.info("{}已退出登录", JSON.toJSONString(user.getUsername()));
        return "redirect:/index";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
        model.addAttribute("count", online());
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //注册通通用户权限
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response> register(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        if (userService.findByUserName(user.getUsername()) != null) {
            return ResponseEntity.ok().body(new Response(false, "该用户名已存在！"));
        }
        if (user.getAvatar() == null) {
            user.setAvatar("/images/avatar-defualt" + RandomUtil.getRandom(0, 40) + ".jpg");
        }
        PasswordHelper helper = new PasswordHelper();
        String md5Passowrd = helper.encryptPassword(user.getUsername(), user.getPassword());
        user.setPassword(md5Passowrd);
        Role role = roleService.findByRoleName("普通用户");
        List<Integer> roles = new ArrayList<>();
        roles.add(role.getId());
        userService.saveUser(user, roles);
        return ResponseEntity.ok().body(new Response(true, "注册成功", "login"));
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized(Model model) {
        model.addAttribute("error", "对不起你无权访问,请联系管理员！");
        return new ModelAndView("/unauthorized", "errorModel", model);
    }
}