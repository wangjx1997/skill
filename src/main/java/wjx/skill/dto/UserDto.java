package wjx.skill.dto;

import wjx.skill.model.Role;

import java.io.Serializable;
import java.util.List;

/**
 * 用户类
 * Created by WangJX on 2018/10/29.
 */

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // 用户的唯一标识

    private String name;

    private String email;

    private String username; // 用户账号，用户登录时的唯一标识

    private String password; // 登录时密码

    private String avatar; // 头像图片地址

    private String createTime; //创建时间

    private List<Role> roles;

    private Role role;

    private String roleName;

    public UserDto() {
    }

    public UserDto(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
        StringBuilder sb = new StringBuilder();
        if (roles.size() > 0) {
            roles.forEach(roleInfo ->
                    sb.append(roleInfo.getRoleName()).append(",")
            );
            this.roleName = sb.substring(0, sb.length() - 1);
        } else {
            this.roleName = "无";
        }
    }
}
