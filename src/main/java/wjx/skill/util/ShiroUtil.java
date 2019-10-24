package wjx.skill.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import wjx.skill.config.shiro.AuthRealm;

/**
 * Created by WangJX on 2018/10/29.
 */
public class ShiroUtil {

    public static void clearAuth(){
        RealmSecurityManager rem = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        AuthRealm realm = (AuthRealm) rem.getRealms().iterator().next();
        realm.clearAuthz();
    }
}
