package wjx.skill.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.dto.PageDto;
import wjx.skill.model.User;

import java.util.List;

/**
 * Created by WangJX on 2018/10/29.
 */
@Repository
public interface UserMapper {
    User findByUserName(String username);
    /**
     * 获取总数
     * @param username
     * @return
     */
    Integer getTotal(@Param("username") String username);
    /**
     * 保存用户
     * @param user
     * @return
     */
    void saveUser(User user);
    /**
     * 删除用户
     * @param id
     * @return
     */
    void removeUser(Integer id);
    /**
     * 更新用户
     * @param user
     * @return
     */
    void updateUser(User user);
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
     * @param pageDto
     * @return
     */
    List<User> listUsersByNameLike(PageDto pageDto);

    /**
     * 更具名称列表查询
     * @param userNames
     * @return
     */
    List<User> listUsersByUserNames(List<String> userNames);

    String getAvatarById(String userName);


}
