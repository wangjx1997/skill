package wjx.skill.service;

import wjx.skill.model.Resources;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/30 18:44
 */
public interface ResourcesService {
    List<Resources> listResourcesByNameLike(String name, Integer from, Integer pageSize);

    List<Resources> listResources();

    Integer getTotal(String name);

    Resources findByPermissions(String permissions);

    /**
     * 保存用户
     * @param resources
     * @return
     */
    void saveResources(Resources resources);

    /**
     * 保存用户
     * @param resources
     * @return
     */
    void updateResources(Resources resources);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void removeResources(Integer id);

    Resources getResourcesById(Integer id);

}
