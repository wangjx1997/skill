package wjx.skill.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjx.skill.dto.PageDto;
import wjx.skill.dto.UserDto;
import wjx.skill.mapper.RoleMapper;
import wjx.skill.mapper.UserMapper;
import wjx.skill.mapper.UserRoleMapper;
import wjx.skill.model.User;
import wjx.skill.model.UserRole;
import wjx.skill.service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJX on 2018/10/29.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Integer getTotal(String userName) {
        return userMapper.getTotal(userName);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    @Transactional
    public void saveUser(User user,List<Integer> roleIds) {
        userMapper.saveUser(user);
        //User user1 = userMapper.findByUserName(user.getUsername());
        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleMapper.insert(new UserRole(user.getId(),roleIds));
        }
    }

    @Override
    @Transactional
    public void removeUser(Integer id) {
        userMapper.removeUser(id);
        userRoleMapper.remove(id);
    }

    @Override
    @Transactional
    public void updateUser(User user,List<Integer> roleIds) {
        userMapper.updateUser(user);
        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleMapper.remove(user.getId());
            userRoleMapper.insert(new UserRole(user.getId(),roleIds));
        }
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<User> listUsers() {
        return userMapper.listUsers();
    }

    @Override
    public List<UserDto> listUsersByNameLike(String userName, Integer from, Integer pageSize) {
        List<User> list =userMapper.listUsersByNameLike(new PageDto(from,pageSize,userName));
        List<UserDto> list1 =new ArrayList<>();
        UserDto user = null;
        for (User info:list) {
            user = new UserDto();
            BeanUtils.copyProperties(info, user);
            user.setRoles(roleMapper.getByUserId(info.getId()));
            list1.add(user);
        }
        return list1;
    }

    @Override
    public List<User> listUsersByUserNames(List<String> userNames) {
        return userMapper.listUsersByUserNames(userNames);
    }

    @Override
    public String getAvatarById(String userName) {
        return userMapper.getAvatarById(userName);
    }
}
