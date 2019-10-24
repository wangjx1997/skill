package wjx.skill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import wjx.skill.dto.ResourcesDto;
import wjx.skill.mapper.ResourcesMapper;
import wjx.skill.mapper.RoleMapper;
import wjx.skill.model.Resources;
import wjx.skill.model.Role;
import wjx.skill.service.RoleService;
import wjx.skill.util.ShiroUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public List<Role> getByUserId(Integer userId) {
        return roleMapper.getByUserId(userId);
    }

    @Override
    public List<String> findRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    public List<Role> listRolesByNameLike(String roleName, Integer from, Integer pageSize) {
        return roleMapper.listRolesByNameLike(roleName, from, pageSize);
    }

    @Override
    public Integer getTotal(String roleName) {
        return roleMapper.getTotal(roleName);
    }

    @Override
    @Transactional
    public void removeRole(Integer id) {
        roleMapper.removeRole(id);
        roleMapper.removeRoleUser(id);
        roleMapper.removeResources(id);
        ShiroUtil.clearAuth();
    }

    @Override
    @Transactional
    public void updateRole(Role role, Integer[] permissions) {
        //roleMapper.updateRole(role);
        roleMapper.removeResources(role.getId());
        roleMapper.saveRoleResources(role.getId(), CollectionUtils.arrayToList(permissions));
        ShiroUtil.clearAuth();
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleMapper.findByRoleName(roleName);
    }

    @Override
    @Transactional
    public void saveRole(Role role, Integer[] permissions) {
        roleMapper.saveRole(role);
        roleMapper.saveRoleResources(role.getId(), CollectionUtils.arrayToList(permissions));
        ShiroUtil.clearAuth();
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    @Override
    public ResourcesDto listResourcesEdit(Integer id) {
        ResourcesDto resourcesDto = new ResourcesDto();
        List<Resources> list1 = resourcesMapper.listResources();

        List<Integer> list2 = resourcesMapper.getResourcesByRoleId(id);
        list1.forEach(info -> {
            if (list2.contains(info.getId())) {
                info.setChecked(true);
            }
        });

        resourcesDto.setResources(list1);
        if (list1.size() == list2.size()) {
            resourcesDto.setCheckedAll(true);
        }
        return resourcesDto;
    }

    @Override
    public List<Integer> getRolesByUserId(Integer userId) {
        return roleMapper.getRolesByUserId(userId);
    }

}
