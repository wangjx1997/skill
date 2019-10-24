package wjx.skill.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.dto.PageDto;
import wjx.skill.model.Resources;
import wjx.skill.service.ResourcesService;
import wjx.skill.vo.Response;

import javax.validation.Valid;
import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/28 14:26
 */
@RestController
@RequestMapping(value = "/resources")
@Validated
public class ResourcesController {
    private final static Logger logger = LoggerFactory.getLogger(ResourcesController.class);
    @Autowired
    private ResourcesService resourcesService;

    /**
     * resources/resourceslist :: #mainContainerRepleace 返回页面上的id为mainContainerRepleace的部分
     * 查询所有权限
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("/skill/resources:read")
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {


        logger.info("roleName-{}", name);

        List<Resources> list = resourcesService.listResourcesByNameLike(name, pageIndex * pageSize, pageSize);    // 当前所在页面数据列表
        logger.info("list-{}", JSONObject.toJSONString(list));
        Integer totalElements = resourcesService.getTotal(name);
        Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
        Boolean first = pageIndex == 0 ? true : false; //是否为首页
        Boolean last = pageIndex + 1 == totalPages ? true : false; //是否为尾页

        PageDto page = new PageDto(pageIndex, totalElements, totalPages, pageSize, first, last);
        logger.info("page{}", JSONObject.toJSONString(page));
        model.addAttribute("page", page);
        model.addAttribute("resourcesList", list);

        return new ModelAndView(async == true ? "resources/resourceslist :: #mainContainerRepleace" : "resources/resourceslist", "resourcesModel", model);
    }

    /**
     * 获取 form 表单页面
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    @RequiresPermissions("/skill/resource:read")
    public ModelAndView createForm(Model model) {
        model.addAttribute("resources", new Resources(null, null, null, null, null));
        return new ModelAndView("resources/resourcesadd", "resourcesModel", model);
    }


    @PostMapping
    public ResponseEntity<Response> create(@Valid Resources resources, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        if (resources.getId() != null) {
            resourcesService.updateResources(resources);
            return ResponseEntity.ok().body(new Response(true, "修改权限成功！"));
        }
        if (resourcesService.findByPermissions(resources.getPermissions()) != null) {
            return ResponseEntity.ok().body(new Response(false, "该资源权限以存在"));
        }
        resourcesService.saveResources(resources);
        return ResponseEntity.ok().body(new Response(true, "添加权限成功！"));
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("/skill/resource:delete")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Model model) {
        try {
            resourcesService.removeResources(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /**
     * 获取修改权限的界面，及数据
     *
     * @param
     * @return
     */
    @GetMapping(value = "edit/{id}")
    @RequiresPermissions({"/skill/resource:read", "/skill/resource:update"})
    public ModelAndView modifyForm(@PathVariable("id") Integer id, Model model) {
        Resources resources = resourcesService.getResourcesById(id);
        model.addAttribute("resources", resources);
        return new ModelAndView("resources/resourcesedit", "resourcesModel", model);
    }
}
