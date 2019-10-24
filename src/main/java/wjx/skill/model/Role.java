package wjx.skill.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 角色
 * Created by WangJX on 2018/10/29.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role implements Serializable{
    private Integer id;
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    private String createTime;

    private boolean checked=false;

    public Role() {
    }

    public Role(Integer id, String roleName, String createTime) {
        this.id = id;
        this.roleName = roleName;
        this.createTime = createTime;
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


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}