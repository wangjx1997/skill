package wjx.skill.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.model.Catalog;

import java.util.List;

/**
 * Catalog 仓库.
 */
@Repository
public interface CatalogMapper {
    /**
     * 保存Catalog
     *
     * @param catalog
     * @return
     */
    void saveCatalog(Catalog catalog);

    void updateCatalog(Catalog catalog);

    /**
     * 删除Catalog
     *
     * @param id
     * @return
     */
    void removeCatalog(@Param("id") Integer id);

    /**
     * 根据id获取Catalog
     *
     * @param id
     * @return
     */
    Catalog getCatalogById(@Param("id") Integer id);

    /**
     * 获取Catalog列表
     *
     * @return
     */
    List<Catalog> listCatalogs(@Param("userId") Integer userId);

    /**
     * 根据用户查询
     *
     * @param userId
     * @param catalogName
     * @return
     */
    Catalog findByUserIdAndName(@Param("userId") Integer userId, @Param("catalogName") String catalogName);
}
