package wjx.skill.mapper;

import org.springframework.stereotype.Repository;
import wjx.skill.model.Vote;

/**
 * Vote 仓库.
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 1.0.0 2017年4月9日
 */
@Repository
public interface VoteMapper {

    /**
     * 根据id获取 Vote
     *
     * @param id
     * @return
     */
    Vote getVoteById(Integer id);

    /**
     * 点赞
     *
     * @param vote
     * @return
     */
    void createVote(Vote vote);

    /**
     * 根据userId获取 Vote
     *
     * @param userId
     * @return
     */
    Vote getVoteByUserId(Integer userId);

    /**
     * 取消点赞
     *
     * @param voteId
     * @return
     */
    void removeVote(Integer voteId);

    void removeByArticleId(Integer articleId);
}
