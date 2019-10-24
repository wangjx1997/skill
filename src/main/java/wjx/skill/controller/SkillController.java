package wjx.skill.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wjx.skill.dto.PageDto;
import wjx.skill.model.User;
import wjx.skill.service.EsSkillService;
import wjx.skill.service.UserService;
import wjx.skill.vo.TagVO;

import java.util.List;
import java.util.Map;

/**
 * 主页控制器.
 */
@Controller
@RequestMapping("/skills")
public class SkillController {
    private final static Logger logger = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private EsSkillService esSkillService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            Model model) {
        PageDto pageDto = null;
        //Page<EsSkill> page = null;
        List<Map<String, Object>> list = null;
        boolean isEmpty = true; // 系统初始化时，没有技术文章数据
        try {

            if (order.equals("hot")) { // 最热查询
                //Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize"/*, "blogId"*/);
                //Pageable pageable = PageRequest.of(pageIndex, pageSize/*, sort*/);
                //page = esSkillService.listHotestEsBlogs(keyword, pageable);
                pageDto = esSkillService.search(keyword, pageIndex, pageSize, order);

            } else if (order.equals("new")) { // 最新查询
                //Sort sort = new Sort(Direction.DESC, "blogId");
//                Pageable pageable = PageRequest.of(pageIndex, pageSize/*, sort*/);
//                page = esSkillService.listNewestEsBlogs(keyword, pageable);
                pageDto = esSkillService.search(keyword, pageIndex, pageSize, order);
            }

            if (pageDto.getTotalPages() > 0) {
                isEmpty = false;
            }
            logger.info("page-{}", JSONObject.toJSONString(pageDto));
//            throw new Exception();
        } catch (Exception e) {
            //Pageable pageable = PageRequest.of(pageIndex, pageSize);
            pageDto = esSkillService.listEsBlogs(pageIndex,pageSize);
            logger.info("Exception-page-{}", JSONObject.toJSONString(pageDto));
        }

        list = pageDto.getList();    // 当前所在页面数据列表
        pageDto.setList(null);
//        list.forEach(info -> {
//            info.setAvatar(userService.getAvatarById(info.getUsername()));
//        });
        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", pageDto);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<Map<String, Object>> newest = esSkillService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);

            List<Map<String, Object>> hotest = esSkillService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", hotest);

            List<TagVO> tags = esSkillService.listTop30Tags();
            model.addAttribute("tags", tags);

            List<User> users = esSkillService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async == true ? "/index :: #mainContainerRepleace" : "/index");
    }

    @GetMapping("/newest")
    public String listNewestEsBlogs(Model model) {
        List<Map<String, Object>> newest = esSkillService.listTop5NewestEsBlogs();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHotestEsBlogs(Model model) {
        List<Map<String, Object>> hotest = esSkillService.listTop5HotestEsBlogs();
        model.addAttribute("hotest", hotest);
        return "hotest";
    }


}
