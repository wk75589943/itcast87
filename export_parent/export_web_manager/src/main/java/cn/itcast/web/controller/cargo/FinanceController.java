package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.service.cargo.FinanceService;
import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/cargo/finance")
public class FinanceController extends BaseController {
    @Reference
    private FinanceService financeService;
    @Reference
    private InvoiceService invoiceService;

    /**
     * 根据企业ID查询所有的财务表单
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        //创建example对象
        FinanceExample example = new FinanceExample();
        //根据创建是时间倒序排列
        example.setOrderByClause("create_time desc");
        FinanceExample.Criteria criteria = example.createCriteria();
        //根据公司id查询所有的财务表单
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo info = financeService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/finance/finance-list";
    }

    /**
     * 进入新建界面
     */
    @RequestMapping(value = "/toAdd", name = "新建财务表界面")
    public String toAdd(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        InvoiceExample example = new InvoiceExample();
        InvoiceExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1);
        PageInfo info = invoiceService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/finance/finance-add";
    }

    /**
     * 保存财务报表
     */
    @RequestMapping(value = "/save")
    public String save(Finance finance) {
        //企业id，名称，创建人，创建部门都是继承BaseController所以写在这
        //设置企业ID
        finance.setCompanyId(companyId);
        //设置企业名称
        finance.setCompanyName(companyName);
        //创建人
        finance.setCreateBy(super.user.getUserName());
        //创建部门
        finance.setCreateDept(super.user.getDeptName());
        financeService.save(finance);
        return "redirect:/cargo/finance/list.do";
    }
    /**
     * 更新财务报表
     */
    @RequestMapping(value = "/edit")
    public String edit(Finance finance) {

        financeService.update(finance);
        return "redirect:/cargo/finance/list.do";
    }
    /**
     * 查看
     */

    @RequestMapping(value = "/toView")
    public String toView(String id) {
        Finance finance = financeService.findById(id);
        request.setAttribute("finance",finance);
        return "cargo/finance/finance-view";
    }
    /**
     * 提交
     */

    @RequestMapping(value = "/submit")
    public String submit(String id) {
        Finance finance = financeService.findById(id);
        finance.setFinanceId(id);
        finance.setState(1);
        financeService.update(finance);
        return "redirect:/cargo/finance/list.do";
    }
    /**
     * 取消
     */

    @RequestMapping(value = "/cancel")
    public String cancel(String id) {
        Finance finance = financeService.findById(id);
        finance.setFinanceId(id);
        finance.setState(0);
        financeService.update(finance);
        return "redirect:/cargo/finance/list.do";
    }
    /**
     * 删除财务表
     */
    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id) {
        Finance finance = financeService.findById(id);
        request.setAttribute("finance",finance);
        return "cargo/finance/finance-update";
    }
    /**
     * 删除财务表
     */
    @RequestMapping(value = "/delete")
    public String delete(String id) {
        financeService.delete(id);
        return "redirect:/cargo/finance/list.do";
    }


}
