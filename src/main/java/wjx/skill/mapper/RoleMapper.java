package wjx.skill.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.model.Resources;
import wjx.skill.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
@Repository
public interface RoleMapper {

    List<Integer> getRolesByUserId(@Param("userId") Integer userId);

    List<Role> getByUserId(@Param("userId") Integer userId);

    List<Role> getRoles();

    List<String> findRoleByUserId(@Param("userId") Integer userId);

    List<Role> listRolesByNameLike(@Param("roleName") String roleName, @Param("from") Integer from, @Param("pageSize") Integer pageSize);

    Integer getTotal(@Param("roleName") String roleName);

    void removeRole(@Param("id") Integer id);

    void removeResources(@Param("id") Integer id);

    void updateRole(Role role);

    Role findByRoleName(@Param("roleName") String roleName);

    void saveRole(Role role);

    void saveRoleResources(@Param("roleId") Integer roleId, @Param("permissions") List<Integer> permissions);

    Set<String> queryAll();

    Set<String> findResourcesByRoleIds(@Param("roleIds") List<Integer> roleIds);

    void removeRoleUser(Integer id);

    Role getRoleById(Integer id);

    //List<Resources> listResourcesEdit(Integer id);

    List<Resources> getResourcesByRoleId(Integer roleId);
}
