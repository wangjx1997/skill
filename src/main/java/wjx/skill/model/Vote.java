package wjx.skill.model;

import java.io.Serializable;

/**
 * vote 实体
 */

public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // 用户的唯一标识

    private Integer userId;

    private Integer articleId;

    private String createTime;

    public Vote() {
    }

    public Vote(Integer userId, Integer articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
