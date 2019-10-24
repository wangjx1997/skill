package wjx.skill.service;


import wjx.skill.dto.SkillDto;
import wjx.skill.model.Skill;

import java.util.List;

/**
 * Skill 服务接口.
 *
 */
public interface SkillService {

	/**
	 * 根据分类id查询文章
	 * @param catalogId
	 * @return
	 */
	List<Skill> getByCatalogId(Integer userId,Integer catalogId);


	/**
	 * 获取总数
	 * @param user_id
	 * @return
	 */
	Integer getTotal(Integer user_id, String title,Integer catalogId);

	/**
	 * 保存文章
	 * @param skill
	 * @return
	 */
	void saveBlog(Skill skill);
	
	/**
	 * 删除文章
	 * @param id
	 * @return
	 */
	void removeBlog(Integer id);
	
	/**
	 * 更新文章
	 * @param skill
	 * @return
	 */
	void updateBlog(Skill skill);
	
	/**
	 * 根据id获取文章
	 * @param id
	 * @return
	 */
	Skill getBlogById(Integer id);
	
	/**
	 * 根据用户名进行分页模糊查询（最新）
	 * @param user_id
	 * @return
	 */
	List<SkillDto> listSkillsByTitleLike(Integer user_id, String title, Integer from, Integer pageSize);
 
	/**
	 * 根据用户名进行分页模糊查询（最热）
	 * @param user_id
	 * @return
	 */
	List<SkillDto> listSkillsByTitleLikeAndSort(Integer user_id, String title, Integer from, Integer pageSize);

	/**
	 * 根据分类进行查询
	 * @param catalogId
	 * @param from
	 * @return
	 */
	List<SkillDto> listSkillsByCatalog(Integer user_id,Integer catalogId,  Integer from, Integer pageSize);

	/**
	 * 用来更新es库
	 * @param skill
	 */
	void updateEs(Skill skill);

	/**
	 * 阅读量递增
	 * @param id
	 */
	SkillDto readingIncrease(Integer id);

	/**
	 * 发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	Skill createComment(Integer blogId, String commentContent, Integer userId);

	/**
	 * 删除评论
	 * @param blogId
	 * @param commentId
	 * @return
	 */
	void removeComment(Integer blogId, Integer commentId);
}
