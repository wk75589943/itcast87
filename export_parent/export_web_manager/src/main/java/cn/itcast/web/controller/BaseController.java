package cn.itcast.web.controller;

import cn.itcast.domain.system.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected String companyId;
    protected String companyName;
    protected User user;

    //protected User user;

    /**
     * @ModelAttribute 配置到方法上：
     * 再执行所有此类中的controler方法之前执行的方法
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;
        //user = session.getAttribute("user");

        //模拟获取当前登录用户的数据
        //初始化当前登录用户所属的企业属性 ： 模拟
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();

        }
        this.user = user;
    }
}
