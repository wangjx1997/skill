package wjx.skill.model.es;

import org.springframework.data.elasticsearch.annotations.Document;
import wjx.skill.model.Skill;
import wjx.skill.model.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Document(indexName = "skill", type = "article")
@XmlRootElement // MediaType 转为 XML
public class EsSkill implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private Integer blogId; // Blog 的 id
    private String title;
    private String summary;
    private String content;
    private String username;
    private String avatar;
    private String createTime;
    private Integer readSize = 0; // 访问量、阅读量
    private Integer commentSize = 0;  // 评论量
    private Integer voteSize = 0;  // 点赞量

    private String tags;  // 标签

    protected EsSkill() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    public EsSkill(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public EsSkill(Integer blogId, String title, String summary, String content, String username, String avatar, String createTime,
                   Integer readSize, Integer commentSize, Integer voteSize, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public EsSkill(Skill skill, User user) {
        this.blogId = skill.getId();
        this.title = skill.getTitle();
        this.summary = skill.getSummary();
        this.content = skill.getContent();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.createTime = skill.getCreateTime();
        this.readSize = skill.getReadSize();
        this.commentSize = skill.getCommentSize();
        this.voteSize = skill.getVoteSize();
        this.tags = skill.getTags();
    }

    public void update(Skill skill, User user) {
        this.blogId = skill.getId();
        this.title = skill.getTitle();
        this.summary = skill.getSummary();
        this.content = skill.getContent();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.createTime = skill.getCreateTime();
        this.readSize = skill.getReadSize();
        this.commentSize = skill.getCommentSize();
        this.voteSize = skill.getVoteSize();
        this.tags = skill.getTags();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "EsSkill{" +
                "id='" + id + '\'' +
                ", blogId=" + blogId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime='" + createTime + '\'' +
                ", readSize=" + readSize +
                ", commentSize=" + commentSize +
                ", voteSize=" + voteSize +
                ", tags='" + tags + '\'' +
                '}';
    }
}
