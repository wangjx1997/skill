package wjx.skill.service;

import wjx.skill.dto.ResourcesDto;
import wjx.skill.model.Role;

import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
public interface RoleService {
    List<Role> getByUserId(Integer userId);

    List<String> findRoleByUserId(Integer userId);

    List<Role> getRoles();

    List<Role> listRolesByNameLike(String roleName, Integer from, Integer pageSize);

    Integer getTotal(String roleName);

    void removeRole(Integer id);

    void updateRole(Role role, Integer[] permissions);

    Role findByRoleName(String roleName);

    void saveRole(Role role, Integer[] permissions);

    Role getRoleById(Integer id);

    ResourcesDto listResourcesEdit(Integer id);

    List<Integer> getRolesByUserId(Integer userId);

    //List<Resources> getResourcesByRoleId(Integer id);
}
