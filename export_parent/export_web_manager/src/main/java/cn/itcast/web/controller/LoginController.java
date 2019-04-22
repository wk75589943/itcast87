package cn.itcast.web.controller;


import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

    /*    @RequestMapping("/login")
        public String login(String email, String password) {
            //1.判断用户和密码是是否为空
            if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
                return "forward:login.jsp";
            }
            //2.根据邮箱查询用户
               User user = userService.findByEmail(email);
            //3.判断密码和账户是否正确
            if (user!=null&&user.getPassword().equals(password)){
                //4.登录成功，把数据保存在session里
                session.setAttribute("loginUser",user);
                //获取当前登录用户的所有权限（菜单）
               List<Module> modules = moduleService.findModuleByUserId(user.getId());
               session.setAttribute("modules",modules);
                //跳到页面
                return "home/main";
            }else{
                //5.登录失败,
                request.setAttribute("error","用户名或密码错误");
                //跳到登录界面
                return "forward:login.jsp";
            }
        }*/

    /**
     * 通过shiro进行登录
     * subject.login（）登录：抛出异常（用户不存在或者用户名密码错误）
     */
    @RequestMapping("/login")
    public String login(String email, String password) {
        try {
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //2.构造用户和密码
            UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);
            //3.借助subject完成用户登录登录
            subject.login(upToken);
            //4.通过shiro获取用户对象，保存到session中
            User user = (User) subject.getPrincipal();//获取安全数据（用户对象）
            session.setAttribute("loginUser", user);
            //获取当前登录用户的所有权限（菜单）
          List<Module> modules = moduleService.findModuleByUserId(user.getId());
            session.setAttribute("modules",modules);
            //跳到页面
            return "home/main";
        } catch (Exception e) {
            e.printStackTrace();
            //5.登录失败,
            request.setAttribute("error", "用户名或密码错误");
            //跳到登录界面
            return "forward:login.jsp";
        }


    }

    //退出
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logout() {
        SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
