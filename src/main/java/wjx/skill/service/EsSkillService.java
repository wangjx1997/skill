package wjx.skill.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wjx.skill.dto.PageDto;
import wjx.skill.model.User;
import wjx.skill.model.es.EsSkill;
import wjx.skill.vo.TagVO;

import java.util.List;
import java.util.Map;

/**
 * Blog 服务接口.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface EsSkillService {
 	
	/**
	 * 删除Blog
	 * @param esId
	 * @return
	 */
	void removeEsBlog(String esId);
	
	/**
	 * 更新 EsBlog
	 * @param esSkill
	 * @return
	 */
	EsSkill updateEsBlog(EsSkill esSkill);
	
	/**
	 * 根据id获取Blog
	 * @param blogId
	 * @return
	 */
	EsSkill getEsBlogByBlogId(Integer blogId);
 
	/**
	 * 最新技术文章列表，分页
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<EsSkill> listNewestEsBlogs(String keyword, Pageable pageable);

	PageDto search(String keyword, Integer pageIndex, Integer pageSize, String order);
	/**
	 * 最热技术文章列表，分页
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<EsSkill> listHotestEsBlogs(String keyword, Pageable pageable);
	
	/**
	 * 技术文章列表，分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageDto listEsBlogs(Integer pageIndex, Integer pageSize);
	/**
	 * 最新前5
	 * @return
	 */
	List<Map<String, Object>> listTop5NewestEsBlogs();
	
	/**
	 * 最热前5
	 * @return
	 */
	List<Map<String, Object>> listTop5HotestEsBlogs();
	
	/**
	 * 最热前 30 标签
	 * @return
	 */
	List<TagVO> listTop30Tags();

	/**
	 * 最热前12用户
	 * @return
	 */
	List<User> listTop12Users();
}
