package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ExtCproductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.FileUploadUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(value = "/cargo/extCproduct")
public class ExtCproductController extends BaseController {
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;

    @RequestMapping(value = "/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        //1.查询货物的生产厂家
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(example);
        request.setAttribute("factoryList", factoryList);
        //2.查询货物下的所有附件
        ExtCproductExample example1 = new ExtCproductExample();
        ExtCproductExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andContractProductIdEqualTo(contractProductId);
        PageInfo info = extCproductService.findAll(example1, page, size);
        request.setAttribute("page", info);
        //3.设置页面的基础id
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-list";
    }

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @RequestMapping(value = "/edit")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) throws Exception {
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId() + "&contractProductId=" + extCproduct.getContractProductId();
    }

    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {
        //1.设置页面的基本参数：id
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        //2.查询附件
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);
        //3.查询生产厂家
        FactoryExample example2 = new FactoryExample();
        FactoryExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(example2);
        request.setAttribute("factoryList", factoryList);
        return "cargo/extc/extc-update";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId + "&contractProductId=" + contractProductId;
    }
}
