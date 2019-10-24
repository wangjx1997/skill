package wjx.skill.model;

import java.util.List;

/**
 * 用户-角色类
 * Created by WangJX on 2018/10/29.
 */
public class UserRole {
    private Integer userid;

    private Integer roleid;

    private List<Integer> roleIds;
    private String createTime;

    public UserRole() {
    }

    public UserRole(Integer userid, Integer roleid) {
        this.userid = userid;
        this.roleid = roleid;
    }

    public UserRole(Integer userid, List<Integer> roleIds) {
        this.userid = userid;
        this.roleIds = roleIds;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}