package wjx.skill.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.service.MenuService;
import wjx.skill.vo.Menu;

import java.util.List;

/**
 * 后台管理控制器
 *
 * @author WangJX
 * @date 2018/12/3 14:26
 */

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取后台管理主页面
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("/skill/manager:read")
    public ModelAndView listUsers(Model model) {

        List<Menu> list = menuService.listMenus();
//        list.add(new Menu("用户管理", "/users","/skill/users:read"));
//        list.add(new Menu("角色管理", "/roles","/skill/roles:read"));
//        list.add(new Menu("权限管理", "/resources","/skill/resources:read"));
        //list.add(new Menu("文章管理", "/skills"));
        //list.add(new Menu("评论管理", "/comments"));
//        list.add(new Menu("菜单管理", "/menus","/skill/menus:read"));
        model.addAttribute("list", list);
        return new ModelAndView("admins/index", "model", model);
    }


}