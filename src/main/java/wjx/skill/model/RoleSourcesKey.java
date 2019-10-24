package wjx.skill.model;

/**
 * 角色-资源权限
 * Created by WangJX on 2018/10/29.
 */
public class RoleSourcesKey {
    private Integer roleid;

    private Integer resourcesid;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getResourcesid() {
        return resourcesid;
    }

    public void setResourcesid(Integer resourcesid) {
        this.resourcesid = resourcesid;
    }

    @Override
    public String toString() {
        return "RoleSourcesKey{" +
                "roleid=" + roleid +
                ", resourcesid=" + resourcesid +
                '}';
    }
}