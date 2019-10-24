package wjx.skill.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wjx.skill.model.Comment;
import wjx.skill.model.Skill;
import wjx.skill.model.User;
import wjx.skill.service.CommentService;
import wjx.skill.service.SkillService;
import wjx.skill.service.UserService;
import wjx.skill.vo.Response;

import java.util.List;


/**
 * 评论 控制器.
 *
 * @author WangJX
 * @date 2018/11/28 14:26
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private SkillService skillService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    /**
     * 获取评论列表
     *
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "blogId", required = true) Integer blogId, Model model) {
        Skill skill = skillService.getBlogById(blogId);
        List<Comment> comments = commentService.listCommentById(blogId);

        for (Comment comment : comments) {
            comment.setUser(userService.getUserById(comment.getUser_id()));
        }

        // 判断操作用户是否是评论的所有者
        String commentOwner = "";
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Session session = subject.getSession();
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            User user = (User) obj;
            if (user != null) {
                commentOwner = user.getUsername();
            }
        }
        logger.info("登录用户名-{}--评论列表-{}", commentOwner, JSONObject.toJSONString(comments));
        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "/userspace/skill :: #mainContainerRepleace";
    }

    /**
     * 发表评论
     *
     * @param blogId
     * @param commentContent
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> createComment(Integer blogId, String commentContent) {
        boolean isAuthenticated = false;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            isAuthenticated = true;
        }
        if (!isAuthenticated) {
            return ResponseEntity.ok().body(new Response(false, "请登录后发表评论!"));
        }
        try {
            User user = (User) subject.getPrincipal();
            skillService.createComment(blogId, commentContent,user.getId());
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "评论成功", null));
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Integer blogId) {

        boolean isOwner = false;
        User user1 = userService.getUserById(commentService.getCommentById(id).getUser_id());
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Session session = subject.getSession();
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();
            User user = (User) obj;
            if (user != null && user1.getUsername().equals(user.getUsername())) {
                isOwner = true;
            }
        }
        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        skillService.removeComment(blogId, id);

        return ResponseEntity.ok().body(new Response(true, "成功删除评论", null));
    }
}
