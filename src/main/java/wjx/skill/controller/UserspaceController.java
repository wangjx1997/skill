package wjx.skill.controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.dto.PageDto;
import wjx.skill.dto.SkillDto;
import wjx.skill.enums.ResultEnum;
import wjx.skill.exception.SysException;
import wjx.skill.model.Catalog;
import wjx.skill.model.Skill;
import wjx.skill.model.User;
import wjx.skill.model.Vote;
import wjx.skill.service.CatalogService;
import wjx.skill.service.SkillService;
import wjx.skill.service.UserService;
import wjx.skill.service.VoteService;
import wjx.skill.util.PasswordHelper;
import wjx.skill.vo.Response;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户主页控制器
 * Created by WangJX on 2018/12/3.
 */
@Controller
@RequestMapping(value = "/u")
@Validated
public class UserspaceController {

    private static final Logger logger = LoggerFactory.getLogger(UserspaceController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用户主页
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        User user = userService.findByUserName(username);
        Subject subject = SecurityUtils.getSubject();
        logger.info("subject.getPrincipal(){}", JSONObject.toJSONString(subject.getPrincipal()));
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/skills";
    }

    /**
     * 获取个人设置
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/profile")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            logger.info("未登录");
            model.addAttribute("error", ResultEnum.USER_SESSION_TIMEOUT.getMsg());
            return new ModelAndView("/unauthorized", "errorModel", model);
        }
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     *
     * @param user
     * @param
     * @param
     * @return
     */
    @PostMapping("/{username}/profile")
    public ResponseEntity<Response> saveProfile(@PathVariable("username") String username, @Valid User user, BindingResult bindingResult) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new SysException(ResultEnum.USER_SESSION_TIMEOUT);
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        // 判断密码是否做了变更
        PasswordHelper helper = new PasswordHelper();
        String md5Passowrd = helper.encryptPassword(user.getUsername(), user.getPassword());

        User user1 = userService.findByUserName(username);
        //id不为空判断密码是否修改
        if (!user1.getPassword().equals(user.getPassword())) {
            user.setPassword(md5Passowrd);
        }
        userService.updateUser(user, new ArrayList<>());
        return ResponseEntity.ok().body(new Response(true, "修改成功", "/u/" + username + "/profile"));
    }

    /**
     * 获取编辑头像的界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            logger.info("未登录");
            model.addAttribute("error", ResultEnum.USER_SESSION_TIMEOUT.getMsg());
            return new ModelAndView("/unauthorized", "errorModel", model);
        }
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    private String deleteAvatar(String avatarId) {
        String responseBody = null;
        try {
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpDelete httpDelete = new HttpDelete("http:" + avatarId);
                ResponseHandler<String> responseHandler = response -> {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        redisTemplate.opsForValue().set("Avatar-" + avatarId.replace("//localhost:8081/", ""), "0");
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                };
                responseBody = httpclient.execute(httpDelete, responseHandler);
            }
        } catch (IOException e) {
            redisTemplate.opsForValue().set("Avatar-" + avatarId.replace("//localhost:8081/", ""), "0");
            e.printStackTrace();
        }
        return responseBody;
    }

    /**
     * 保存头像
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        String oldAvatarId = originalUser.getAvatar().replace("//localhost:8081/view/", "");
        String newAvatarId = user.getAvatar().replace("//localhost:8081/view/", "");
        //originalUser.setAvatar(avatarUrl);
        user.setUsername(username);
        //String responseBody = null;
        if (originalUser.getAvatar().contains("//localhost:8081/view/")) {
            //responseBody = deleteAvatar(originalUser.getAvatar().replace("view/", ""));
            try {
                restTemplate.delete("http:" + originalUser.getAvatar().replace("view/", ""));
                logger.info("删除更新前头像成功！");
            } catch (RestClientException e) {
                logger.info("删除更新前头像失败！");
                redisTemplate.opsForValue().set("Avatar-" + oldAvatarId, "2");
                e.printStackTrace();
            }
        }

        while (true) {
            String tag = redisTemplate.opsForValue().get("Avatar-" + oldAvatarId);
            if (!StringUtils.isEmpty(tag)) {
                redisTemplate.delete("Avatar-" + oldAvatarId);

                if ("2".equals(tag)) {
                    ListOperations<String, String> operations = redisTemplate.opsForList();
                    logger.info("删除更新前头像失败！");
                    operations.leftPush("Avatar-error", newAvatarId);
                    return ResponseEntity.ok().body(new Response(true, "更新头像失败！", null));
                }

                if ("1".equals(tag)) {
                    userService.updateUser(user, null);
                    logger.info("更新头像成功！");
                    return ResponseEntity.ok().body(new Response(true, "处理成功！", avatarUrl));
                }


                break;
            }
        }


        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }

    /**
     * 默认最新
     *
     * @param username
     * @param order
     * @param catalogId
     * @param keyword
     * @return
     */
    @GetMapping("/{username}/skills")
    public String listSkillsByOrder(@PathVariable("username") String username,
                                    @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                    @RequestParam(value = "catalog", required = false) Integer catalogId,
                                    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                    @RequestParam(value = "async", required = false) boolean async,
                                    @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    Model model) {
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        List<SkillDto> list = null;
        PageDto page = null;
        if (catalogId != null) {
            Integer totalElements = skillService.getTotal(user.getId(), null, catalogId);
            Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
            Boolean first = pageIndex == 0 ? true : false; //是否为首页
            Boolean last = pageIndex + 1 == totalPages ? true : false; //是否为尾页
            page = new PageDto(pageIndex, totalElements, totalPages, pageSize, first, last);
            list = skillService.listSkillsByCatalog(user.getId(), catalogId, pageIndex * pageSize, pageSize);
            order = "";
        }
        if (order.equals("hot")) { // 最热查询
            Integer totalElements = skillService.getTotal(user.getId(), keyword, null);
            Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
            Boolean first = pageIndex == 0 ? true : false; //是否为首页
            Boolean last = pageIndex + 1 == totalPages ? true : false; //是否为尾页
            page = new PageDto(pageIndex, totalElements, totalPages, pageSize, first, last);
            list = skillService.listSkillsByTitleLikeAndSort(user.getId(), keyword, pageIndex * pageSize, pageSize);
        }
        if (order.equals("new")) { // 最新查询
            Integer totalElements = skillService.getTotal(user.getId(), keyword, null);
            Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
            Boolean first = pageIndex == 0 ? true : false; //是否为首页
            Boolean last = pageIndex + 1 == totalPages ? true : false; //是否为尾页
            page = new PageDto(pageIndex, totalElements, totalPages, pageSize, first, last);
            list = skillService.listSkillsByTitleLike(user.getId(), keyword, pageIndex * pageSize, pageSize);
        }

        logger.info("文章列表——{}", JSONObject.toJSONString(list));

        model.addAttribute("order", order);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
    }


    /**
     * 获取文章展示界面
     *
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/skills/{id}")
    public String getSkillById(@PathVariable("username") String username, @PathVariable("id") Integer id, Model model) {
        // 每次读取，简单的可以认为阅读量增加1次
        SkillDto skill = skillService.readingIncrease(id);
        boolean isBlogOwner = false;
        Vote currentVote = null; // 当前用户的点赞情况
        Subject subject = SecurityUtils.getSubject();
        logger.info("subject.getPrincipal(){}", JSONObject.toJSONString(subject.getPrincipal()));
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                isBlogOwner = true;
            }
            currentVote = voteService.getVoteByUserId(user.getId());
        }
        skill.setUser(userService.findByUserName(username));
        skill.setCatalog(catalogService.getCatalogById(skill.getCatalog_id()));

        logger.info("文章id-{},-内容{}", JSONObject.toJSONString(id), JSONObject.toJSONString(skill));
        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", skill);
        model.addAttribute("currentVote", currentVote);

        return "/userspace/skill";
    }

    /**
     * 获取新增文章的界面
     *
     * @param model
     * @return
     */
    @GetMapping("/{username}/skills/edit")
    public ModelAndView createSkill(@PathVariable("username") String username, Model model) {
        logger.info("用户-{}-获取新增文章的界面", username);
        List<Catalog> catalogs = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                catalogs = catalogService.listCatalogs(user.getId());
            }
        }
        model.addAttribute("blog", new Skill(null, null, null));
        model.addAttribute("username", username);
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("/userspace/edit", "blogModel", model);
    }

    /**
     * 获取编辑文章的界面
     *
     * @param model
     * @return
     */
    @GetMapping("/{username}/skills/edit/{id}")
    public ModelAndView editSkill(@PathVariable("username") String username, @PathVariable("id") Integer id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                Skill skill = skillService.getBlogById(id);
                List<Catalog> catalogs = catalogService.listCatalogs(user.getId());
                logger.info("文章修改获取内容-{}", JSONObject.toJSONString(skill));
                model.addAttribute("username", username);
                model.addAttribute("catalogs", catalogs);
                model.addAttribute("blog", skill);
            } else {
                model.addAttribute("error", "对不起你不是文章所有者,无权访问！");
                return new ModelAndView("/unauthorized", "errorModel", model);
            }
        } else {
            model.addAttribute("error", "对不起你还未登录！");
            return new ModelAndView("/unauthorized", "errorModel", model);
        }
        return new ModelAndView("/userspace/edit", "blogModel", model);
    }

    /**
     * 保存文章
     *
     * @param username
     * @param skill
     * @return
     */
    @PostMapping("/{username}/skills/edit")
    public ResponseEntity<Response> saveSkill(@PathVariable("username") String username, @RequestBody @Valid Skill skill, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        // 对 Catalog 进行空处理
        if (skill.getCatalog_id() == null) {
            return ResponseEntity.ok().body(new Response(false, "未选择分类!没有分类请到个人主页创建！"));
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                if (skill.getId() == null) {
                    skill.setUser_id(user.getId());
                    logger.info("添加文章内容-{}", JSONObject.toJSONString(skill));
                    skillService.saveBlog(skill);
                }
                logger.info("修改文章内容-{}", JSONObject.toJSONString(skill));
                skillService.updateBlog(skill);
            } else {
                return ResponseEntity.ok().body(new Response(false, "身份匹对失败，无权访问！"));
            }
        } else {
            return ResponseEntity.ok().body(new Response(false, "未登录，无权访问！"));
        }
        String redirectUrl = "/u/" + username + "/skills/" + skill.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /**
     * 删除文章
     *
     * @param id
     * @param username
     * @return
     */
    @DeleteMapping("/{username}/skills/{id}")
    public ResponseEntity<Response> deleteSkill(@PathVariable("username") String username, @PathVariable("id") Integer id) {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                logger.info("用户-{},删除文章id-{}，文章内容-{}", username, id, JSONObject.toJSONString(skillService.getBlogById(id)));
                skillService.removeBlog(id);
            } else {
                return ResponseEntity.ok().body(new Response(false, "对不起你不是文章所有者"));
            }
        } else {
            return ResponseEntity.ok().body(new Response(false, "未登录，无权访问！"));
        }
        String redirectUrl = "/u/" + username + "/skills";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }


}
