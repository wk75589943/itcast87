package cn.itcast.web.shiro;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    /**
     * 授权管理
     * 判断用户是否具有操作权限
     * 参数：PrincipalCollection
     * 安全数据集合
     * 返回值：AuthorizationInfo
     * 用户的所有权限信息
     * <p>
     * 通过用户登录的安全数据，获取用户的所有操作权限并返回
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        //2.得到当前登录的用户对象
        User user = (User) principalCollection.getPrimaryPrincipal();
        //3.通过用户对象id获取用户的所有操作模块
           List<Module> moduleList = moduleService.findModuleByUserId(user.getId());
        //4.构造AuthorizationInfo对象返回
        Set<String> perms = new HashSet<>();
        for (Module module : moduleList) {
            perms.add(module.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将所有可操作模块的名称存入到授权对象中
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 身份认证
     * 用于验证输入的用户名密码
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取到用户界面输入的邮箱和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String email = upToken.getUsername();
        String password = new String(upToken.getPassword());
        //2.根据有邮箱查询用户
        User user = userService.findByEmail(email);//安全数据(用户对象)
        if (user != null) {
            //第一个参数：安全数据（user对象）
            //第二个参数：密码（数据库密码）
            //第三个参数：当前调用realm域的名称（类名即可）
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;
        }
        return null;//subject.login()方法的时候会抛出异常
    }
}

