package wjx.skill.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.model.Resources;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/30 18:44
 */
@Repository
public interface ResourcesMapper {
    List<Resources> listResourcesByNameLike(@Param("name") String name, @Param("from") Integer from, @Param("pageSize") Integer pageSize);

    List<Resources> listResources();

    Integer getTotal(@Param("name") String name);

    Resources findByPermissions(@Param("permissions") String permissions);

    /**
     * 保存权限
     *
     * @param resources
     * @return
     */
    void saveResources(Resources resources);

    /**
     * 更新
     *
     * @param resources
     * @return
     */
    void updateResources(Resources resources);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    void removeResources(@Param("id") Integer id);
    void removeRoleResources(@Param("id") Integer id);

    Resources getResourcesById(@Param("id")Integer id);

    List<Integer> getResourcesByRoleId(@Param("roleId")Integer roleId);
}
