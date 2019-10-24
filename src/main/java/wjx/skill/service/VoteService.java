package wjx.skill.service;

import wjx.skill.model.Vote;

/**
 * Vote 服务接口.
 */
public interface VoteService {
    /**
     * 根据id获取 Vote
     *
     * @param id
     * @return
     */
    Vote getVoteById(Integer id);

    /**
     * 根据userId获取 Vote
     *
     * @param userId
     * @return
     */
    Vote getVoteByUserId(Integer userId);

    /**
     * 点赞
     *
     * @param vote
     * @return
     */
    void createVote(Vote vote);

    /**
     * 删除Vote
     *
     * @param blogId
     * @param id
     * @return
     */
    void removeVote(Integer id,Integer blogId);
}
