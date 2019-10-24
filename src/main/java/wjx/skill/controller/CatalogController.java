package wjx.skill.controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wjx.skill.model.Catalog;
import wjx.skill.model.User;
import wjx.skill.service.CatalogService;
import wjx.skill.service.SkillService;
import wjx.skill.service.UserService;
import wjx.skill.util.ConstraintViolationExceptionHandler;
import wjx.skill.vo.CatalogVO;
import wjx.skill.vo.Response;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 分类 控制器.
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;
    /**
     * 获取分类列表
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "username", required = true) String username, Model model) {
        User user = userService.findByUserName(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user.getId());
        logger.info("分类列表--{}", JSONObject.toJSONString(catalogs));
        // 判断操作用户是否是分类的所有者
        boolean isOwner = false;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user1 = (User) subject.getPrincipal();
            if (user1 != null && user.getUsername().equals(user1.getUsername())) {
                isOwner = true;
                logger.info("用户-{}获取用户-{}的分类列表", user1.getUsername(), user.getUsername());
            }
        } else {
            logger.info("游客获取用户-{}的分类列表", user.getUsername());
        }

        model.addAttribute("isCatalogsOwner", isOwner);
        model.addAttribute("catalogs", catalogs);
        return "/userspace/u :: #catalogRepleace";
    }

    /**
     * 发表分类
     *
     * @param catalogVO
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Validated CatalogVO catalogVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok().body(new Response(false, bindingResult.getFieldError().getDefaultMessage()));
        }
        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        String msg = "修改分类成功";
        try {
            if (catalog.getId() == null) {
                User user = userService.findByUserName(username);
                catalog.setUser_id(user.getId());
                catalogService.saveCatalog(catalog);
                msg = "添加分类成功";
            }

            catalogService.updateCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, msg, null));
    }

    /**
     * 删除分类
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(String username, @PathVariable("id") Integer id) {
        User info = userService.findByUserName(username);
        //需要判断身份
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            if (user != null && info.getUsername().equals(user.getUsername())) {
                if (skillService.getByCatalogId(info.getId(),id).size() > 0) {
                    return ResponseEntity.ok().body(new Response(false, "该分类已有文章，不能删除！", null));
                }
                catalogService.removeCatalog(id);
                logger.info("用户-{}删除分类", user.getUsername());
            } else {
                return ResponseEntity.ok().body(new Response(false, "无权删除分类", null));
            }
        }


        return ResponseEntity.ok().body(new Response(true, "删除分类成功", null));
    }

    /**
     * 获取分类编辑界面
     *
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String getCatalogEdit(Model model) {
        Catalog catalog = new Catalog(null, null);
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }

    /**
     * 根据 Id 获取分类信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String getCatalogById(@PathVariable("id") Integer id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        logger.info("id为-{}的分类-{}",id,JSONObject.toJSONString(catalog));
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }
    public static void main(String[] args) {
        String str = "Spring,Spring-Boot,数据库";
        System.out.println("前"+str);
        str=str.toLowerCase();
        System.out.println("后"+str);
    }
}
