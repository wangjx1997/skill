package wjx.skill.model;

import com.github.rjeschke.txtmark.Processor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Skill 实体
 */
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; // 用户的唯一标识
	@NotEmpty(message = "标题不能为空")
	@Size(min=2, max=50)
	private String title;
	@NotEmpty(message = "摘要不能为空")
	@Size(min=2, max=300)
	private String summary;
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	private String content;
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	private String htmlContent; // 将 md 转为 html
	private Integer user_id;
	private String createTime;
	private Integer readSize; // 访问量、阅读量
	private Integer commentSize;  // 评论量
	private Integer voteSize;  // 点赞量
	private String tags;  // 标签
	private Integer catalog_id; //分类

	public Skill() {
		// TODO Auto-generated constructor stub
	}
	public Skill(String title, String summary, String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
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
		this.htmlContent = Processor.process(content);//转成html
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
		this.tags = tags != null?tags.toLowerCase():tags;
	}

	public Integer getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(Integer catalog_id) {
		this.catalog_id = catalog_id;
	}

	@Override
	public String toString() {
		return "Skill{" +
				"id=" + id +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", content='" + content + '\'' +
				", htmlContent='" + htmlContent + '\'' +
				", user_id=" + user_id +
				", createTime='" + createTime + '\'' +
				", readSize=" + readSize +
				", commentSize=" + commentSize +
				", voteSize=" + voteSize +
				", tags='" + tags + '\'' +
				", catalog_id=" + catalog_id +
				'}';
	}
}
