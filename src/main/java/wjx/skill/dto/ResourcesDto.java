package wjx.skill.dto;

import wjx.skill.model.Resources;

import java.util.List;

/**
 * @author WangJX
 * @date 2019/2/12 19:00
 */
public class ResourcesDto {
    private List<Resources> resources;
    private boolean checkedAll = false;

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    public boolean isCheckedAll() {
        return checkedAll;
    }

    public void setCheckedAll(boolean checkedAll) {
        this.checkedAll = checkedAll;
    }
}
