package wjx.skill.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wjx.skill.dto.SkillDto;
import wjx.skill.mapper.SkillMapper;
import wjx.skill.mapper.VoteMapper;
import wjx.skill.model.Comment;
import wjx.skill.model.Skill;
import wjx.skill.model.es.EsSkill;
import wjx.skill.service.CommentService;
import wjx.skill.service.EsSkillService;
import wjx.skill.service.SkillService;
import wjx.skill.service.UserService;

import java.util.List;

/**
 * Skill 服务实现.
 */
@Service
public class SkillServiceImpl implements SkillService {

    private static final Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

    @Autowired
    private SkillMapper skillMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private EsSkillService esSkillService;

    @Override
    public List<Skill> getByCatalogId(Integer userId,Integer catalogId) {
        return skillMapper.getByCatalogId(userId,catalogId);
    }

    @Override
    public Integer getTotal(Integer user_id, String title,Integer catalogId) {
        return skillMapper.getTotal(user_id, title,catalogId);
    }

    @Override
    @Transactional
    public void saveBlog(Skill skill) {
        skillMapper.saveBlog(skill);
        Skill returnBlog = skillMapper.getBlogById(skill.getId());

        EsSkill esSkill = new EsSkill(returnBlog,userService.getUserById(skill.getUser_id()));
        esSkillService.updateEsBlog(esSkill);

        if (skill.getTags() != null) {
            skillMapper.saveTags(skill.getId(), CollectionUtils.arrayToList(skill.getTags().split(",")));
        }
    }

    @Override
    @Transactional
    public void removeBlog(Integer id) {
        //删除文章
        skillMapper.removeBlog(id);
        //删除评论
        commentService.removeByArticleId(id);
        //删除点赞
        voteMapper.removeByArticleId(id);
        skillMapper.deleteByArticleId(id);
        EsSkill esSkill = esSkillService.getEsBlogByBlogId(id);

        esSkillService.removeEsBlog(esSkill.getId());
    }

    @Override
    @Transactional
    public void updateBlog(Skill skill) {
        //更新数据库
        skillMapper.updateBlog(skill);
        skillMapper.deleteByArticleId(skill.getId());
        if (skill.getTags() != null) {
            skillMapper.saveTags(skill.getId(), CollectionUtils.arrayToList(skill.getTags().split(",")));
        }
        //更新es
        updateEs(skill);
    }

    @Override
    public Skill getBlogById(Integer id) {
        return skillMapper.getBlogById(id);
    }

    @Override
    public List<SkillDto> listSkillsByTitleLike(Integer user_id, String title, Integer from, Integer pageSize) {
        List<SkillDto> skillDtos = skillMapper.listSkillsByTitleLike(user_id, title, from, pageSize);
        for (SkillDto dtos : skillDtos) {
            dtos.setUser(userService.getUserById(dtos.getUser_id()));
        }
        return skillDtos;
    }

    @Override
    public List<SkillDto> listSkillsByTitleLikeAndSort(Integer user_id, String title, Integer from, Integer pageSize) {
        List<SkillDto> skillDtos = skillMapper.listSkillsByTitleLikeAndSort(user_id, title, from, pageSize);
        for (SkillDto dtos : skillDtos) {
            dtos.setUser(userService.getUserById(dtos.getUser_id()));
        }
        return skillDtos;
    }

    @Override
    public List<SkillDto> listSkillsByCatalog(Integer user_id, Integer catalogId,  Integer from, Integer pageSize) {
        List<SkillDto> skillDtos = skillMapper.listSkillsByCatalog(user_id, catalogId, from, pageSize);
        for (SkillDto dtos : skillDtos) {
            dtos.setUser(userService.getUserById(dtos.getUser_id()));
        }
        return skillDtos;
    }

    /**
     * 用来更新es库
     * @param skill
     */
    @Override
    public void updateEs(Skill skill) {

        //查询更新的文章
        Skill returnBlog = skillMapper.getBlogById(skill.getId());
        //查询es
        EsSkill esSkill = esSkillService.getEsBlogByBlogId(skill.getId());
        //更新es实体
        esSkill.update(returnBlog,userService.findByUserName(esSkill.getUsername()));
        //更新es库
        esSkillService.updateEsBlog(esSkill);

    }

    @Override
    @Transactional
    public SkillDto readingIncrease(Integer id) {
        //访问量加一
        Skill skill = skillMapper.getBlogById(id);

        logger.info("查看文章id{},点击前量-{}", id, skill.getReadSize());
        skill.setReadSize(skill.getReadSize() + 1);

        skillMapper.updateBlog(skill);

        logger.info("查看文章id{},点击后量-{}", id, skillMapper.getSkillById(skill.getId()).getReadSize());

        //更新es
        updateEs(skill);

        return skillMapper.getSkillById(skill.getId());
    }

    @Override
    @Transactional
    public Skill createComment(Integer blogId, String commentContent, Integer userId) {

        Skill skill = skillMapper.getBlogById(blogId);
        //添加评论
        commentService.createComment(new Comment(userId, skill.getId(), commentContent));
        //评论数量加一
        skill.setCommentSize(skill.getCommentSize() + 1);
        skillMapper.updateBlog(skill);
        //更新es
        updateEs(skill);
        return null;
    }

    @Override
    @Transactional
    public void removeComment(Integer blogId, Integer commentId) {
        Skill skill = skillMapper.getBlogById(blogId);
        //删除评论
        commentService.removeComment(commentId);
        //评论数量减一
        skill.setCommentSize(skill.getCommentSize() - 1);
        skillMapper.updateBlog(skill);

        //更新es
        updateEs(skill);
    }
}
