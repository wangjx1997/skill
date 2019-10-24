package wjx.skill.service;

import wjx.skill.vo.Menu;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/30 18:44
 */
public interface MenuService {
    List<Menu> listMenusByNameLike(String name, Integer from, Integer pageSize);

    List<Menu> listMenus();

    Integer getTotal(String name);

    Menu findByPermissions(String permissions, String url);

    /**
     * 保存用户
     *
     * @param menu
     * @return
     */
    void saveMenu(Menu menu);

    /**
     * 保存用户
     *
     * @param menu
     * @return
     */
    void updateMenu(Menu menu);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void removeMenu(Integer id);

    Menu getMenuById(Integer id);

}
