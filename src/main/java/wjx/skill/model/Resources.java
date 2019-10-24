package wjx.skill.model;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 资源权限
 * Created by WangJX on 2018/10/29.
 */
public class Resources implements Serializable{
    private Integer id;  //资源id
    @NotEmpty(message = "资源名称不能为空")
    private String name;    //资源名称
    @NotEmpty(message = "资源权限不能为空")
    private String permissions; //资源权限

    private Integer type;   //资源类型

    private Integer pid;    //父资源id

    private Integer sort;

    private String createTime;

    private boolean checked=false;

    public Resources() {
    }

    public Resources(Integer id, String name, String permissions, Integer type, Integer pid) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
        this.type = type;
        this.pid = pid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
        this.name = name == null ? null : name.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions='" + permissions + '\'' +
                ", type=" + type +
                ", pid=" + pid +
                ", sort=" + sort +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}