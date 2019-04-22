package cn.itcast.web.shiro;

import cn.itcast.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 用户密码比较
     * 1.对用户输入的密码进行加密
     * 2.比较用户输入的密码和数据库密码是否一致
     *
     * @param token 用户界面输入的邮箱和密码
     * @param info  安全数据：用户对象user
     *              <p>
     *              111111 + 固定值（加盐）  =  xxxxxx
     */
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.获取用户账户和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String email = upToken.getUsername();
        String loginPassword = new String(upToken.getPassword());
        //2.对登录密码进行加密
        //加密：使用MD5加密
        String md5Paasword = Encrypt.md5(loginPassword, email);
        //info.getPrincipals();//获取安全数据，用户对象
        //获取数据库密码
        String sbpassword = (String) info.getCredentials();
        //true:登录成功，false：抛出异常
        return md5Paasword.equals(sbpassword);
    }
}
