package wjx.skill.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wjx.skill.mapper.CatalogMapper;
import wjx.skill.model.Catalog;
import wjx.skill.service.CatalogService;

import java.util.List;

/**
 * Catalog 服务.
 * 
 * @since 1.0.0 2017年4月10日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogMapper catalogMapper;

	@Override
	@Transactional
	public void saveCatalog(Catalog catalog) {
		// 判断重复
		Catalog catalog1 = catalogMapper.findByUserIdAndName(catalog.getUser_id(), catalog.getName());
		if(catalog1 !=null) {
			throw new IllegalArgumentException("该分类已经存在了");
		}
		catalogMapper.saveCatalog(catalog);

	}

	@Override
	@Transactional
	public void updateCatalog(Catalog catalog) {
		catalogMapper.updateCatalog(catalog);
	}

	@Override
	@Transactional
	public void removeCatalog(Integer id) {
		catalogMapper.removeCatalog(id);
	}

	@Override
	public Catalog getCatalogById(Integer id) {
		return catalogMapper.getCatalogById(id);
	}

	@Override
	public List<Catalog> listCatalogs(Integer userId) {
		return catalogMapper.listCatalogs(userId);
	}

}
