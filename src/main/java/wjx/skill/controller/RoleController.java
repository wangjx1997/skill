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
import wjx.skill.dto.ResourcesDto;
import wjx.skill.model.Role;
import wjx.skill.service.ResourcesService;
import wjx.skill.service.RoleService;
import wjx.skill.vo.Response;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/28 14:26
 */
@RestController
@RequestMapping(value = "/roles")
@Validated
public class RoleController {
    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourcesService resourcesService;
    /**
     *  roles/rolelist :: #mainContainerRepleace 返回页面上的id为mainContainerRepleace的部分
     * 查询所用用户
     * @return
     */
    @GetMapping
    @RequiresPermissions("/skill/roles:read")
    public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") Integer pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") Integer pageSize,
                             @RequestParam(value="name",required=false,defaultValue="") String roleName,
                             Model model) {


        logger.info("roleName-{}", roleName);

        List<Role> list  = roleService.listRolesByNameLike(roleName, pageIndex*pageSize,pageSize);	// 当前所在页面数据列表
        logger.info("list-{}", JSONObject.toJSONString(list));
        Integer totalElements = roleService.getTotal(roleName);
        Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
        Boolean first = pageIndex == 0 ? true : false; //是否为首页
        Boolean last = pageIndex + 1 == totalPages ? true : false; //是否为尾页

        PageDto page = new PageDto(pageIndex,totalElements,totalPages,pageSize,first,last);
        logger.info("page{}", JSONObject.toJSONString(page));
        model.addAttribute("page", page);
        model.addAttribute("roleList", list);

        return new ModelAndView(async==true?"roles/rolelist :: #mainContainerRepleace":"roles/rolelist", "roleModel", model);
    }
    /**
     * 获取 form 表单页面
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    @RequiresPermissions("/skill/role:create")
    public ModelAndView createForm(Model model) {
        model.addAttribute("roles", new Role(null,null,null));
        model.addAttribute("resources", resourcesService.listResources());
        return new ModelAndView("roles/roleadd", "roleModel", model);
    }

    @PostMapping
    public ResponseEntity<Response> create(@Valid Role role, BindingResult bindingResult,@NotNull(message = "请选择资源权限！")Integer[] permissions) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        if (role.getId() != null) {
            roleService.updateRole(role,permissions);
            return ResponseEntity.ok().body(new Response(true, "修改角色成功！"));
        }
        if (roleService.findByRoleName(role.getRoleName()) != null) {
            return ResponseEntity.ok().body(new Response(false, "该角色已存在"));
        }
        roleService.saveRole(role,permissions);
        return ResponseEntity.ok().body(new Response(true, "添加角色成功！"));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("/skill/role:delete")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Model model) {
        roleService.removeRole(id);
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /**
     * 获取修改权限的界面，及数据
     *
     * @param
     * @return
     */
    @GetMapping(value = "edit/{id}")
    @RequiresPermissions({"/skill/role:read","/skill/role:update"})
    public ModelAndView modifyForm(@PathVariable("id") Integer id, Model model) {

        ResourcesDto resourcesDto = roleService.listResourcesEdit(id);
        model.addAttribute("role", roleService.getRoleById(id));
        model.addAttribute("resourcesDto", resourcesDto);
        return new ModelAndView("roles/roleedit", "roleModel", model);
    }
}
