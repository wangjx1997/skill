package wjx.skill.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import wjx.skill.model.es.EsSkill;

/**
 * es 存储库.
 *
 */
public interface EsSkillRepository extends ElasticsearchRepository<EsSkill, String> {
 
	/**
	 * 模糊查询(去重)
	 * @param title
	 * @param Summary
	 * @param content
	 * @param tags
	 * @param pageable
	 * @return
	 */
	Page<EsSkill> findDistinctEsSkillByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);
	
	EsSkill findByBlogId(Integer blogId);
}
