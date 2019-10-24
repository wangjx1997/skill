package wjx.skill.service;

import wjx.skill.dto.UserDto;
import wjx.skill.model.User;

import java.util.List;

/**
 * Created by WangJX on 2018/10/29.
 */
public interface UserService {


    /**
     * 获取总数
     * @param userName
     * @return
     */
    Integer getTotal(String userName);

    User findByUserName(String username);

    /**
     * 保存用户
     * @param user
     * @return
     */
    void saveUser(User user,List<Integer> roleIds);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void removeUser(Integer id);

    /**
     * 删除列表里面的用户
     * @param users
     * @return
     */
    //void removeUsersInBatch(List<User> users);

    /**
     * 更新用户
     * @param user
     * @return
     */
    void updateUser(User user,List<Integer> roleIds);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 获取用户列表
     * @param
     * @return
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     * @param userName
     * @return
     */
    List<UserDto> listUsersByNameLike(String userName, Integer from, Integer pageSize);

    /**
     * 更具名称列表查询
     * @param userNames
     * @return
     */
    List<User> listUsersByUserNames(List<String> userNames);

    String getAvatarById(String userName);
}
