package wjx.skill.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Catalog 实体
 */
public class Catalog implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id; // 用户的唯一标识

    @NotEmpty(message = "名称不能为空")
    @Size(min = 2, max = 30)
    private String name;

    private Integer user_id;

    private String createTime;

    protected Catalog() {
    }

    public Catalog(String name, Integer user_id) {
        this.name = name;
        this.user_id = user_id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user_id=" + user_id +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
