package wjx.skill.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wjx.skill.model.User;
import wjx.skill.model.Vote;
import wjx.skill.service.UserService;
import wjx.skill.service.VoteService;
import wjx.skill.vo.Response;


/**
 * 点赞控制器.
 */
@Controller
@RequestMapping("/votes")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    /**
     * 发表点赞
     *
     * @param blogId
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> createVote(Integer blogId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            logger.info("有游客阅读文章并点赞");
            voteService.createVote(new Vote(0, blogId));
            return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
        }
        Vote vote = voteService.getVoteByUserId(user.getId());
        if (vote != null && user.getId().equals(vote.getUserId())) {
            return ResponseEntity.ok().body(new Response(false, "只有一次点赞哟", null));
        }
        voteService.createVote(new Vote(user.getId(), blogId));
        logger.info("用户-{}阅读文章并点赞",user.getUsername());
        return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
    }

    /**
     * 删除点赞
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Integer blogId) {

        boolean isOwner = false;
        User voteUser = userService.getUserById(voteService.getVoteById(id).getUserId());
        // 判断操作用户是否是点赞的所有者
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && voteUser != null && voteUser.getUsername().equals(user.getUsername())) {
                isOwner = true;
                voteService.removeVote(id, blogId);
                logger.info("用户-{}取消点赞",user.getUsername());

            }
        }
        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
    }
}
