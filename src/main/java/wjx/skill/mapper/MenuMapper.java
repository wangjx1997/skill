package wjx.skill.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wjx.skill.vo.Menu;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/1/30 18:44
 */
@Repository
public interface MenuMapper {
    List<Menu> listMenusByNameLike(@Param("name") String name, @Param("from") Integer from, @Param("pageSize") Integer pageSize);

    List<Menu> listMenus();

    Integer getTotal(@Param("name") String name);

    Menu findByPermissions(@Param("permissions") String permissions, @Param("url") String url);

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
    void removeMenu(@Param("id") Integer id);

    Menu getMenuById(@Param("id") Integer id);

}
