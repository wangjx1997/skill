package wjx.skill.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.dto.PageDto;
import wjx.skill.dto.UserDto;
import wjx.skill.model.Role;
import wjx.skill.model.User;
import wjx.skill.service.RoleService;
import wjx.skill.service.UserService;
import wjx.skill.util.PasswordHelper;
import wjx.skill.util.RandomUtil;
import wjx.skill.vo.Response;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WangJX
 * @date 2018/11/28 14:26
 */
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * users/list :: #mainContainerRepleace 返回页面上的id为mainContainerRepleace的部分
     * 查询所用用户
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("/skill/users:read")
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String userName,
                             Model model) {


        logger.info("userName-{}", userName);
        // 当前所在页面数据列表
        List<UserDto> list = userService.listUsersByNameLike(userName, pageIndex * pageSize, pageSize);
        logger.info("list-{}", JSONObject.toJSONString(list));
        Integer totalElements = userService.getTotal(userName);
        Integer totalPages = totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1;
        // 是否为首页
        Boolean first = pageIndex == 0 ? true : false;
        // 是否为尾页
        Boolean last = pageIndex + 1 == totalPages ? true : false;

        PageDto page1 = new PageDto(pageIndex, totalElements, totalPages, pageSize, first, last);
        logger.info("page1{}", JSONObject.toJSONString(page1));
        model.addAttribute("page", page1);
        model.addAttribute("userList", list);

        return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel", model);
    }

    /**
     * 获取 form 表单页面
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    @RequiresPermissions("/skill/user:create")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null, null, null, null));
        model.addAttribute("roles", roleService.getRoles());
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 新建和修改用户
     *
     * @param user
     * @param
     * @param
     * @return
     */
    @PostMapping
    //@RequiresPermissions("")
    public ResponseEntity<Response> create(@Valid User user, BindingResult bindingResult,@NotNull(message = "请选择角色！") Integer[] roleId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        PasswordHelper helper = new PasswordHelper();
        String md5Passowrd = helper.encryptPassword(user.getUsername(), user.getPassword());

        if (user.getAvatar() == null) {
            user.setAvatar("/images/avatar-defualt" + RandomUtil.getRandom(0, 40) + ".jpg");
        }
        List<Integer> roleIds = CollectionUtils.arrayToList(roleId);
        // id为空则是添加
        if (user.getId() == null) {
            // 加密密码
            user.setPassword(md5Passowrd);
            if (userService.findByUserName(user.getUsername()) != null) {
                return ResponseEntity.ok().body(new Response(false, "该用户名已存在！"));
            }
            userService.saveUser(user, roleIds);
        } else {
            User user1 = userService.findByUserName(user.getUsername());
            // id不为空判断密码是否修改
            if (!user1.getPassword().equals(user.getPassword())) {
                user.setPassword(md5Passowrd);
            }
            userService.updateUser(user, roleIds);
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("/skill/user:delete")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Model model) {
        userService.removeUser(id);
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /**
     * 获取修改用户的界面，及数据
     *
     * @param
     * @return
     */
    @GetMapping(value = "edit/{id}")
    @RequiresPermissions({"/skill/user:read","/skill/user:update"})
    public ModelAndView modifyForm(@PathVariable("id") Integer id, Model model) {
        User user1 = userService.getUserById(id);
        UserDto user = new UserDto();
        BeanUtils.copyProperties(user1, user);
        List<Integer> userRoles =roleService.getRolesByUserId(id);

        List<Role> roles = roleService.getRoles();

        roles.forEach(info->{
            if (userRoles.contains(info.getId())) {
                info.setChecked(true);
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return new ModelAndView("users/edit", "userModel", model);
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<Response> getAvatar(@PathVariable("userName") String userName, Model model) {
        return ResponseEntity.ok().body(new Response(true, "获取头像成功",userService.getAvatarById(userName)));
    }
}
