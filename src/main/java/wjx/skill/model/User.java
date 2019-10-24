package wjx.skill.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户类
 * Created by WangJX on 2018/10/29.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // 用户的唯一标识

    @NotEmpty(message = "姓名不能为空")
    @Size(min=2, max=20)
    private String name;

    @NotEmpty(message = "邮箱不能为空")
    @Size(max=50)
    @Email(message= "邮箱格式不对" )
    private String email;

    @NotEmpty(message = "账号不能为空")
    @Size(min=3, max=20)
    private String username; // 用户账号，用户登录时的唯一标识

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6)
    private String password; // 登录时密码

    private String avatar; // 头像图片地址

    private String createTime; //创建时间

    public User() {
    }

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
