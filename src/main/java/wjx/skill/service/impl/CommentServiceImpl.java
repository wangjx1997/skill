package wjx.skill.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjx.skill.mapper.CommentMapper;
import wjx.skill.model.Comment;
import wjx.skill.service.CommentService;

import java.util.List;


/**
 * Comment 服务.
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Override
	public Comment getCommentById(Integer id) {

		return commentMapper.getCommentById(id);
	}

	@Override
	public List<Comment> listCommentById(Integer articleId) {
		return commentMapper.listCommentById(articleId);
	}

	@Override
	@Transactional
	public void removeComment(Integer id) {
		commentMapper.removeComment(id);
	}

	@Override
	public void removeByArticleId(Integer article_id) {
		commentMapper.removeByArticleId(article_id);
	}

	@Override
	public void createComment(Comment comment) {
		commentMapper.createComment(comment);
	}



}
