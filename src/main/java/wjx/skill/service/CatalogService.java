package wjx.skill.service;


import wjx.skill.model.Catalog;

import java.util.List;

/**
 * Catalog 服务接口.
 *
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 * @param catalog
	 * @return
	 */
	void saveCatalog(Catalog catalog);


	/**
	 * 更新Catalog
	 * @param catalog
	 * @return
	 */
	void updateCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 * @param id
	 * @return
	 */
	void removeCatalog(Integer id);

	/**
	 * 根据id获取Catalog
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Integer id);
	
	/**
	 * 获取Catalog列表
	 * @return
	 */
	List<Catalog> listCatalogs(Integer userId);
}
