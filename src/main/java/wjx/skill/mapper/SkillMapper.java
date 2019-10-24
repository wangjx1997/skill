package wjx.skill.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.dto.SkillDto;
import wjx.skill.model.Skill;
import wjx.skill.vo.TagVO;

import java.util.List;

/**
 * Skill dao接口.
 */
@Repository
public interface SkillMapper {

    /**
     * 获取总数
     *
     * @param user_id
     * @return
     */
    Integer getTotal(@Param("user_id") Integer user_id, @Param("title") String title, @Param("catalogId") Integer catalogId);

    /**
     * 保存文章
     *
     * @param skill
     * @return
     */
    void saveBlog(Skill skill);

    void saveTags(@Param("articleId") Integer articleId, @Param("tags") List<String> tags);

    void deleteByArticleId(Integer articleId);

    List<TagVO> countTags();

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    void removeBlog(@Param("id") Integer id);

    /**
     * 更新文章
     *
     * @param skill
     * @return
     */
    void updateBlog(Skill skill);

    /**
     * 根据id获取文章
     *
     * @param id
     * @return
     */
    Skill getBlogById(@Param("id") Integer id);

    List<Skill> getByCatalogId(@Param("userId") Integer userId,@Param("catalogId") Integer catalogId);

    /**
     * 根据id获取文章
     *
     * @param id
     * @return
     */
    SkillDto getSkillById(@Param("id") Integer id);

    /**
     * 根据用户名进行分页模糊查询（最新）
     *
     * @param user_id
     * @return
     */
    List<SkillDto> listSkillsByTitleLike(@Param("user_id") Integer user_id, @Param("title") String title, @Param("from") Integer from, @Param("pageSize") Integer pageSize);

    /**
     * 根据用户名进行分页模糊查询（最热）
     *
     * @param user_id
     * @return
     */
    List<SkillDto> listSkillsByTitleLikeAndSort(@Param("user_id") Integer user_id, @Param("title") String title, @Param("from") Integer from, @Param("pageSize") Integer pageSize);

    /**
     * @param user_id
     * @param catalogId
     * @param from
     * @param pageSize
     * @return
     */
    List<SkillDto> listSkillsByCatalog(@Param("user_id") Integer user_id, @Param("catalogId") Integer catalogId, @Param("from") Integer from, @Param("pageSize") Integer pageSize);
}
