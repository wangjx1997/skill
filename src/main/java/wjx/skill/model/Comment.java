package wjx.skill.model;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Comment 实体
 */
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id; // 用户的唯一标识

	@NotEmpty(message = "评论内容不能为空")
	@Size(min=2, max=500)
	private String content;
 

	private Integer user_id;

	private Integer article_id;

	private String createTime;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}
	public Comment(Integer user_id,Integer article_id, String content) {
		this.content = content;
		this.user_id = user_id;
		this.article_id = article_id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticle_id() {
		return article_id;
	}

	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
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

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", content='" + content + '\'' +
				", user_id=" + user_id +
				", article_id=" + article_id +
				", createTime='" + createTime + '\'' +
				", user=" + user +
				'}';
	}
}
