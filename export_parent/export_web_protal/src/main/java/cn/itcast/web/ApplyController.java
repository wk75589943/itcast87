package cn.itcast.web;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {


    /**
     * dubbo
     * 功能：重试
     * 当第一次请求失败的时候，默认会继续重新调用2次
     * 第一次失败
     * 调用两次（全部成功）
     * retries:配置重试次数：关键业务需要设置0
     */
    //远程调用
    @Reference(retries = 0)
    private CompanyService companyService;

    /**
     * 企业申请的控制器方法
     * 保存企业
     *
     * @return
     */
    @RequestMapping("/apply")
    public @ResponseBody
    String apply(Company company) {
        try {
            company.setState(0);
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "2";
        }
    }
}
