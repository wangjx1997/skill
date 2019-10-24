package wjx.skill.dto;

import wjx.skill.model.Catalog;
import wjx.skill.model.Comment;
import wjx.skill.model.User;

import java.io.Serializable;
import java.util.List;

/**
 * Skill 实体
 */
public class SkillDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; // 用户的唯一标识
	private String title;
	private String summary;
	private String content;
	private String htmlContent; // 将 md 转为 html
	private Integer user_id;
	private User user;
	private String createTime;
	private Integer readSize; // 访问量、阅读量
	private Integer commentSize;  // 评论量
	private Integer voteSize;  // 点赞量
	private String tags;  // 标签
	private List<Comment> comments; //评论
	private Integer catalog_id;
	private Catalog catalog;
	public SkillDto() {
		// TODO Auto-generated constructor stub
	}
	public SkillDto(String title, String summary, String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Integer getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(Integer catalog_id) {
		this.catalog_id = catalog_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public Integer getReadSize() {
		return readSize;
	}

	public void setReadSize(Integer readSize) {
		this.readSize = readSize;
	}

	public Integer getCommentSize() {
		return commentSize;
	}

	public void setCommentSize(Integer commentSize) {
		this.commentSize = commentSize;
	}

	public Integer getVoteSize() {
		return voteSize;
	}

	public void setVoteSize(Integer voteSize) {
		this.voteSize = voteSize;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
