package wjx.skill.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.model.Comment;

import java.util.List;

/**
 * Comment dao接口.
 *
 */
@Repository
public interface CommentMapper {
	/**
	 * 根据评论id获取 Comment
	 * @param id
	 * @return
	 */
	Comment getCommentById(@Param("id")Integer id);

	/**
	 * 根据文章id获取 Comment
	 * @param articleId
	 * @return
	 */
	List<Comment> listCommentById(@Param("articleId") Integer articleId);
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	void removeComment(@Param("id") Integer id);

	/**
	 * 根据文章id删除评论
	 * @param article_id
	 * @return
	 */
	void removeByArticleId(@Param("article_id")Integer article_id);
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	void createComment(Comment comment);
}
