package wjx.skill.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjx.skill.mapper.VoteMapper;
import wjx.skill.model.Skill;
import wjx.skill.model.Vote;
import wjx.skill.service.SkillService;
import wjx.skill.service.VoteService;


/**
 * Vote 点赞服务.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private SkillService skillService;


    @Override
    public Vote getVoteById(Integer id) {
        return voteMapper.getVoteById(id);
    }

    @Override
    public Vote getVoteByUserId(Integer userId) {
        return voteMapper.getVoteByUserId(userId);
    }

    @Override
    @Transactional
    public void createVote(Vote vote) {
        //点赞
        voteMapper.createVote(vote);
        Skill skill = skillService.getBlogById(vote.getArticleId());
        //点赞量加一
        skill.setVoteSize(skill.getVoteSize() + 1);
        skillService.updateBlog(skill);

        skillService.updateEs(skill);
    }

    @Override
    @Transactional
    public void removeVote(Integer id, Integer blogId) {
        //取消点赞
        voteMapper.removeVote(id);
        Skill skill = skillService.getBlogById(blogId);
        //点赞量减一
        skill.setVoteSize(skill.getVoteSize() - 1);
        skillService.updateBlog(skill);

        skillService.updateEs(skill);
    }
}
