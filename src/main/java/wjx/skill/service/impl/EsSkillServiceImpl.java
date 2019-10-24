package wjx.skill.service.impl;


import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchParseException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wjx.skill.controller.SkillController;
import wjx.skill.dto.PageDto;
import wjx.skill.mapper.SkillMapper;
import wjx.skill.model.User;
import wjx.skill.model.es.EsSkill;
import wjx.skill.repository.EsSkillRepository;
import wjx.skill.service.EsSkillService;
import wjx.skill.service.UserService;
import wjx.skill.vo.TagVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * EsBlog 服务.
 */
@Service
public class EsSkillServiceImpl implements EsSkillService {
    private final static Logger logger = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private EsSkillRepository esSkillRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private SkillMapper skillMapper;
    @Autowired
    private TransportClient client;

    //private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
    private static final String EMPTY_KEYWORD = "";

    @Override
    public void removeEsBlog(String id) {
        esSkillRepository.deleteById(id);
    }


    @Override
    public EsSkill updateEsBlog(EsSkill esSkill) {
        return esSkillRepository.save(esSkill);
    }


    @Override
    public EsSkill getEsBlogByBlogId(Integer blogId) {
        return esSkillRepository.findByBlogId(blogId);
    }


//    public Page<EsSkill> newEsSkills(Pageable pageable) {
//        Page<EsSkill> pages = null;
//
//
//        //QueryBuilder builder = QueryBuilders.boolQuery();
//
//        SortBuilder sort = SortBuilders.fieldSort("blogId")//排序字段
//                .order(SortOrder.DESC);//升序或者降序
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withSearchType(SearchType.QUERY_THEN_FETCH)
//                .withIndices("blog").withTypes("blog")
//                .withSort(sort)
//                .build();
//        //pages = esSkillRepository.search(builder,pageable);
//        pages = esSkillRepository.search(searchQuery);
//
//
//        return pages;
//    }

//    public Page<EsSkill> hotEsSkills(Pageable pageable) {
//        Page<EsSkill> pages = null;
//
//
//        //QueryBuilder builder = QueryBuilders.boolQuery();
//
//        SortBuilder sort = SortBuilders.fieldSort("createTime")//排序字段
//                                        .order(SortOrder.DESC);//升序或者降序
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withSearchType(SearchType.QUERY_THEN_FETCH)
//                .withIndices("blog").withTypes("blog")
//                .withSort(sort)
//                .build();
//        //pages = esSkillRepository.search(builder,pageable);
//        pages = esSkillRepository.search(searchQuery);
//
//
//        return pages;
//    }


    @Override
    public Page<EsSkill> listNewestEsBlogs(String keyword, Pageable pageable) throws SearchParseException {
        Page<EsSkill> pages = null;
        Sort sort = new Sort(Direction.DESC, "createTime.keyword");
        //if (pageable.getSort() == null) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        //}

        pages = esSkillRepository.findDistinctEsSkillByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
        //pages = newEsSkills(pageable);
        return pages;
    }

    @Override
    public PageDto search(String keyword, Integer pageIndex, Integer pageSize, String order) {
        SearchResponse response = getSearchResponse(keyword, pageIndex, pageSize, order);


        List<Map<String, Object>> list = new ArrayList<>();
        response.getHits().forEach(skill -> {
            skill.getSourceAsMap().put("avatar", userService.getAvatarById((String) skill.getSourceAsMap().get("username")));
            list.add(skill.getSourceAsMap());
        });


        Long totalHits = response.getHits().getTotalHits();
        Integer totalElements = Integer.parseInt(totalHits.toString());
        Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
        // 是否为首页
        Boolean first = pageIndex == 0 ? true : false;
        // 是否为尾页
        Boolean last = pageIndex + 1 == totalPages ? true : false;

        PageDto page1 = new PageDto(list, pageIndex, totalElements, totalPages, pageSize, first, last);

        return page1;
    }

    private SearchResponse getSearchResponse(String keyword, Integer pageIndex, Integer pageSize, String order) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder shouldBoolQuery = QueryBuilders.boolQuery();
//        if (StringUtils.isEmpty(keyword)) {
//            keyword = "*";
//        }
        keyword = "*" + keyword + "*";
        List<QueryBuilder> should = shouldBoolQuery.should();
        should.add(QueryBuilders.wildcardQuery("title", keyword));
        should.add(QueryBuilders.wildcardQuery("summary", keyword));
        should.add(QueryBuilders.wildcardQuery("content", keyword));
        should.add(QueryBuilders.wildcardQuery("tags", keyword));

        boolQuery.must(shouldBoolQuery);


        SearchRequestBuilder builder = client.prepareSearch("skill")
                .setTypes("article")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setPreference("_primary")
                .setQuery(boolQuery)
                .setSize(pageSize)
                .setFrom(pageIndex * pageSize);

        if ("new".equals(order)) {
            builder.addSort("createTime.keyword", SortOrder.DESC);
        }

        if ("hot".equals(order)) {
            builder.addSort("readSize", SortOrder.DESC).
                    addSort("commentSize", SortOrder.DESC).
                    addSort("voteSize", SortOrder.DESC);
        }
//        System.out.println(builder);
        SearchResponse response = builder.get();
        return response;
    }

    @Override
    public Page<EsSkill> listHotestEsBlogs(String keyword, Pageable pageable) throws SearchParseException {

        Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize");
        //if (pageable.getSort() == null) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        //}

        return esSkillRepository.findDistinctEsSkillByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public PageDto listEsBlogs(Integer pageIndex, Integer pageSize) {
        return this.search(EMPTY_KEYWORD, pageIndex, pageSize, null);
    }


    /**
     * 最新前5
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listTop5NewestEsBlogs() {
        //Page<EsSkill> page = this.listNewestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
        SearchResponse response = getSearchResponse(EMPTY_KEYWORD, 0, 5, "new");
        List<Map<String, Object>> list = new ArrayList<>();
        response.getHits().forEach(skill -> {
            list.add(skill.getSourceAsMap());
        });
        logger.info("new5-{}", JSONObject.toJSONString(list));

        return list;
    }

    /**
     * 最热前5
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listTop5HotestEsBlogs() {
        //Page<EsSkill> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
        SearchResponse response = getSearchResponse("", 0, 5, "hot");
        List<Map<String, Object>> list = new ArrayList<>();
        response.getHits().forEach(skill -> {
            list.add(skill.getSourceAsMap());
        });
        logger.info("hot5-{}", JSONObject.toJSONString(list));

        return list;
    }

    @Override
    public List<TagVO> listTop30Tags() {

        List<TagVO> list = skillMapper.countTags();
        logger.info("tags-{}", JSONObject.toJSONString(list));

        return list;
    }

    @Override
    public List<User> listTop12Users() {

        List<String> usernamelist = new ArrayList<>();
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("skill").withTypes("article")
                .addAggregation(terms("users").field("username.keyword").size(12))
                //.addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("users");

        Iterator<StringTerms.Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
            String username = actiontypeBucket.getKey().toString();
            usernamelist.add(username);
        }
        List<User> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(usernamelist)) {
            list = userService.listUsersByUserNames(usernamelist);
        }
        logger.info("userHot-{}", JSONObject.toJSONString(list));

        return list;
    }
}
