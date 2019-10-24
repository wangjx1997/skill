package wjx.skill.config.shiro;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import wjx.skill.util.PasswordHelper;

/**
 * Created by WangJX on 2018/10/29.
 */
public class CredentialMatcher extends SimpleCredentialsMatcher {
    //登录的校验规则
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
        PasswordHelper helper = new PasswordHelper();
        String md5Passowrd = helper.encryptPassword(((UsernamePasswordToken) token).getUsername(),password);
        String dbPassword = (String) info.getCredentials();
        return this.equals(md5Passowrd,dbPassword);
    }
}
