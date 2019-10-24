package wjx.skill.vo;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 菜单 值对象.
 *
 * @author WangJX
 * @date 2018/11/28 15:26
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    @NotEmpty(message = "菜单名称不能为空")
    private String name;
    @NotEmpty(message = "菜单Url不能为空")
    private String url;
    @NotEmpty(message = "资源权限不能为空")
    private String permissions;
    private String createTime;

    public Menu() {
    }

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Menu(String name, String url, String permissions) {
        this.name = name;
        this.url = url;
        this.permissions = permissions;
    }

    public Menu(Integer id, String name, String url, String permissions) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.permissions = permissions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }
}
