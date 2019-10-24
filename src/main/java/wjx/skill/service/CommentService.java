package wjx.skill.service;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import wjx.skill.model.Comment;

import java.util.List;

/**
 * Comment 服务接口.
 */
public interface CommentService {
	/**
	 * 根据评论id获取 Comment
	 * @param id
	 * @return
	 */
	Comment getCommentById(Integer id);

	/**
	 * 根据文章id获取 Comment
	 * @param articleId
	 * @return
	 */
	List<Comment> listCommentById(Integer articleId);
	/**
	 * 根据评论id删除评论
	 * @param id
	 * @return
	 */
	void removeComment(Integer id);
	/**
	 * 根据文章id删除评论
	 * @param article_id
	 * @return
	 */
	void removeByArticleId(Integer article_id);

	void createComment(Comment comment);
}
