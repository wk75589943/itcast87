package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductVo;
import cn.itcast.vo.ExportResult;
import cn.itcast.vo.ExportVo;
import cn.itcast.web.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cargo/export")
public class ExportController extends BaseController {
    @Reference
    private ContractService contractService;
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;

    /**
     * 合同管理列表
     */
    @RequestMapping(value = "contractList", name = "合同管理列表")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1);
        PageInfo info = contractService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/export/export-contractList";
    }

    /**
     * 出口报运管理列表
     */
    @RequestMapping(value = "/list", name = "出口报运管理列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        ExportExample example = new ExportExample();
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo info = exportService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/export/export-list";
    }

    /**
     * 报运界面
     */
    @RequestMapping(value = "toExport", name = "报运界面")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    /**
     * 新增报运单
     */
    @RequestMapping(value = "edit", name = "报运界面")
    public String edit(Export export) {
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        if (StringUtils.isEmpty(export.getId())) {
            exportService.save(export);
        } else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 报运单查看界面
     */
    @RequestMapping(value = "toView", name = "报运单查看界面")
    public String toView(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        return "cargo/export/export-view";
    }

    /**
     * 更新报运单界面
     */
    @RequestMapping(value = "toUpdate", name = "更新报运单界面")
    public String toUpdate(String id) {
        //1.根据id查询报运单
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        //2.查询此报运单下的所有商品
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List eps = exportProductService.findAll(example);
        request.setAttribute("eps", eps);
        return "cargo/export/export-update";
    }

    /**
     * 出口报运提交
     * 将状态由0改为1
     */
    @RequestMapping(value = "/submit", name = "出口报运提交")
    public String submit(String id) {
        //1.构造出口报运对象
        Export export = exportService.findById(id);
        //2.设置id
        export.setId(id);
        //3.设置状态
        export.setState(1);
        //4.更新
        exportService.update(export);
        //跳转到修改界面
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 出口报运提交
     * 将状态由1改为0
     */
    @RequestMapping(value = "/cancel", name = "出口报运提交")
    public String cancel(String id) {
        //1.构造出口报运对象
        Export export = exportService.findById(id);
        //2.设置id
        export.setId(id);
        //3.设置状态
        export.setState(0);
        //4.更新
        exportService.update(export);
        //跳转到修改界面
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id) {
        exportService.delete(id);
        //跳转到修改界面
        return "redirect:/cargo/export/list.do";
    }
    /**
     * 电子报运
     * @param id
     * @return
     */
    @RequestMapping("/exportE")
    public String exportE(String id){
        //1.根据报运单id查询报运单对象
        Export export = exportService.findById(id);
        //2.根据报运单Id查询报运商品列表
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);
        //3.构造电子报运的VO对象，并赋值
        ExportVo vo = new ExportVo();
        BeanUtils.copyProperties(export,vo);
        vo.setExportId(export.getId());
        //构造报运商品数据
        List<ExportProductVo> products = new ArrayList<ExportProductVo>();
        for (ExportProduct ep : eps) {
            ExportProductVo epv = new ExportProductVo();
            BeanUtils.copyProperties(ep,epv);
            epv.setExportProductId(ep.getId());
            products.add(epv);
        }
        vo.setProducts(products);
        //4.电子报运
        WebClient client = WebClient.create("http://localhost:9090/ws/export/user");
        client.post(vo);
        //5.查询报运结果
        client = WebClient.create("http://localhost:9090/ws/export/user/"+id);
        ExportResult result = client.get(ExportResult.class);
        //6.调用service完成报运结果的入库
        exportService.updateE(result);
        return "redirect:/cargo/export/list.do";
    }
}
