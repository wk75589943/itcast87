package cn.itcast.web.controller.cargo;


import cn.itcast.domain.cargo.Entrust;
import cn.itcast.domain.cargo.EntrustExample;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.EntrustService;
import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/cargo/invoice")
public class InvoiceController extends BaseController {
    @Reference
    private InvoiceService invoiceService;
    @Reference
    private EntrustService entrustService;
    @Reference
    private PackingService packingService;
    //购销合同列表分页

    /**
     * companyId:
     * 当前操作用户所属的企业id
     */
    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        //1.构造example对象
        InvoiceExample example = new InvoiceExample();
        //按创时间倒序排序
        example.setOrderByClause("create_time desc");
        //2.构造criteria
        InvoiceExample.Criteria criteria = example.createCriteria();
        //3.添加条件
        criteria.andCompanyIdEqualTo(companyId);
        //4.分页查询
        PageInfo info = invoiceService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/invoice/invoice-list";
    }

    /**
     * 进入发票新建界面
     */
    @RequestMapping(value = "toAdd", name = "发票新增界面")
    public String toAdd(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        EntrustExample example = new EntrustExample();
        EntrustExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1);
        PageInfo info = entrustService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/invoice/invoice-add";
    }
    /**
     * 保存发票
     */
    @RequestMapping(value = "/save",name = "新增发票界面")
    public String save(Invoice invoice){
        invoice.setCompanyId(companyId);
        invoice.setCompanyName(companyName);
        //设置创建人
        invoice.setCreateBy(super.user.getUserName());
        //设置创建部门
        invoice.setCreateDept(super.user.getDeptName());
        invoiceService.save(invoice);
        return "redirect:/cargo/invoice/list.do";
    }
    /**
     * 发票新建界面
     */
    @RequestMapping(value = "/edit",name = "更新发票界面")
    public String edit(Invoice invoice) { ;
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    /**
     * 修改发票内容
     */
    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id) {
        //根据Id查询invoice对象
        Invoice invoice = invoiceService.findById(id);
        request.setAttribute("invoice", invoice);
        //修改后跳转页面
        return "cargo/invoice/invoice-update";
    }

    /**
     * 发票提交
     * 将状态由0改为1
     */
    @RequestMapping(value = "/submit")
    public String submit(String id) {
        //1.构造发票对象
        Invoice invoice = invoiceService.findById(id);
        //2.设置id
        invoice.setInvoiceId(id);
        //3.设置状态
        invoice.setState(1);
        //4.更新
        invoiceService.update(invoice);
        //跳转到修改界面
        return "redirect:/cargo/invoice/list.do";

    }

    /**
     * 发票取消
     * 将状态由1改为0
     */
    @RequestMapping(value = "/cancel")
    public String cancel(String id) {
        //1.构造发票对象
        Invoice invoice = invoiceService.findById(id);
        //2.设置id
        invoice.setInvoiceId(id);
        //3.设置状态
        invoice.setState(0);
        //4.更新
        invoiceService.update(invoice);
        //跳转到修改界面
        return "redirect:/cargo/invoice/list.do";

    }

    /**
     * 发票查看界面
     */
    @RequestMapping(value = "/toView", name = "发票查看界面")
    public String toView(String id) {
        //构造invoice对象
        Invoice invoice = invoiceService.findById(id);
        //发送到request域中
        request.setAttribute("invoice", invoice);
        return "cargo/invoice/invoice-view";
    }
    /**
     * 委托单查看详情
     */
    @RequestMapping(value = "/toViewShow", name = "委托单详情界面")
    public String toViewShow(String id) {
        //构造委托单对象对象
        Entrust entrust = entrustService.findById(id);
        //发送到request域中
        request.setAttribute("entrust", entrust);
        return "cargo/invoice/invoice-viewShow";
    }

    /**
     * 删除发票
     */
    @RequestMapping(value = "/delete")
    public String delete(String id) {
        invoiceService.delete(id);
        //跳转到修改界面
        return "redirect:/cargo/invoice/list.do";
    }
}