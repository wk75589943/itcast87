package cn.itcast.web.controller.stat;

import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;
    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }
    /**
     * 获取厂家销售数据：已ajax的形式请求
     */
    @RequestMapping("/getFactoryData")
    public @ResponseBody List getFactoryData() {
        List list = statService.getFactoryData(companyId);
        return list;
    }
    @RequestMapping("/getSellData")
    public @ResponseBody List getSellData() {
        List list = statService.getSellData(companyId);
        return list;
    }

    @RequestMapping("/getOnlineData")
    public @ResponseBody List getOnlineData() {
        List list = statService.getOnlineData(companyId);
        return list;
    }
}
