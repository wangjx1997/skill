package wjx.skill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wjx.skill.mapper.MenuMapper;
import wjx.skill.service.MenuService;
import wjx.skill.vo.Menu;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/2/19 11:12
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> listMenusByNameLike(String name, Integer from, Integer pageSize) {
        return menuMapper.listMenusByNameLike(name, from, pageSize);
    }

    @Override
    public List<Menu> listMenus() {
        return menuMapper.listMenus();
    }

    @Override
    public Integer getTotal(String name) {
        return menuMapper.getTotal(name);
    }

    @Override
    public Menu findByPermissions(String permissions, String url) {
        return menuMapper.findByPermissions(permissions, url);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.saveMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.removeMenu(id);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }
}
