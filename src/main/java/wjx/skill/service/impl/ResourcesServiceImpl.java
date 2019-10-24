package wjx.skill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wjx.skill.mapper.ResourcesMapper;
import wjx.skill.model.Resources;
import wjx.skill.service.ResourcesService;
import wjx.skill.util.ShiroUtil;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/30 18:46
 */
@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public List<Resources> listResourcesByNameLike(String name, Integer from, Integer pageSize) {
        return resourcesMapper.listResourcesByNameLike(name, from, pageSize);
    }

    @Override
    public List<Resources> listResources() {
        return resourcesMapper.listResources();
    }

    @Override
    public Integer getTotal(String name) {
        return resourcesMapper.getTotal(name);
    }

    @Override
    public Resources findByPermissions(String permissions) {
        return resourcesMapper.findByPermissions(permissions);
    }

    @Override
    public void saveResources(Resources resources) {
        resourcesMapper.saveResources(resources);
    }

    @Override
    public void updateResources(Resources resources) {
        resourcesMapper.updateResources(resources);
        ShiroUtil.clearAuth();
    }

    @Override
    public void removeResources(Integer id) {
        resourcesMapper.removeResources(id);
        resourcesMapper.removeRoleResources(id);
        ShiroUtil.clearAuth();
    }

    @Override
    public Resources getResourcesById(Integer id) {
        return resourcesMapper.getResourcesById(id);
    }
}
