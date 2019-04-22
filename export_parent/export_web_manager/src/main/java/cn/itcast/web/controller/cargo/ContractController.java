package cn.itcast.web.controller.cargo;

import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    //购销合同列表分页

    /**
     * companyId:
     * 当前操作用户所属的企业id
     */
    @RequestMapping(value = "/list", name = "购销合同列表分页")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        //1.构造example对象
        ContractExample example = new ContractExample();
        //按创时间倒序排序
        example.setOrderByClause("create_time desc");
        //2.构造criteria
        ContractExample.Criteria criteria = example.createCriteria();
        //3.添加条件
        criteria.andCompanyIdEqualTo(companyId);
        //细粒度查询条件
        /**
         * 根据用户级别进行判断
         *      4：员工
         *      3：本部门
         *      2：管理所有下属部门和人员
         *      1：管理员
         */
        Integer degree = user.getDegree();

        if(degree == 4) { //员工
            criteria.andCreateByEqualTo(user.getId());
        }else if(degree ==3) { //本部门
            criteria.andCreateDeptEqualTo(user.getDeptId());
        }else if(degree ==2) { //管理所有下属部门和人员
            criteria.andCreateDeptLike(user.getDeptId()+"%");
        }

        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 进入新增购销合同页面
     * 1.查询所用购销合同数据，为了构造下拉框数据
     */
    @RequestMapping(value = "/toAdd", name = "新增购销合同界面")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 新增购销合同
     * 1.获取表单数据构造contract对象
     * 2.添加对应的企业属性
     * 更新购销合同
     */
    @RequestMapping(value = "/edit", name = "编辑购销合同")
    public String edit(Contract contract) {
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        //1.判断是否具有id属性
        if (UtilFuns.isEmpty(contract.getId())) {
            //设置创建人
            contract.setCreateBy(super.user.getId());

            //设置创人的部门
            contract.setCreateDept(super.user.getDeptId());
            //2.没有id，保存
            contractService.save(contract);
        } else {
            //3.具有id，更新
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 进入到修改界面
     * 1.获取到id
     * 2.根据id进行查询
     * 3.查询所有的购销合同
     * 4.保存到request域中
     * 5.跳转到修改界面
     */
    @RequestMapping(value = "/toUpdate", name = "进入到修改界面")
    public String toUpdate(String id) {
        //根据id进行查询
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        //跳转到修改界面
        return "cargo/contract/contract-update";
    }

    @RequestMapping(value = "/toView", name = "进入到修改界面")
    public String toView(String id) {
        //根据id进行查询
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        //跳转到修改界面
        return "cargo/contract/contract-view";
    }

    /**
     * 购销合同提交
     * 将状态由0改为1
     */
    @RequestMapping(value = "/submit", name = "购销合同提交")
    public String submit(String id) {
        //1.构造购销合同对象
        Contract contract = new Contract();
        //2.设置id
        contract.setId(id);
        //3.设置状态
        contract.setState(1);
        //4.更新
        contractService.update(contract);
        //跳转到修改界面
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 购销合同取消
     * 将状态由1改为0
     */
    @RequestMapping(value = "/cancel", name = "购销合同取消")
    public String cancel(String id) {
        //判断
        //1.构造购销合同对象
        Contract contract = new Contract();
        //2.设置id
        contract.setId(id);
        //3.设置状态
        contract.setState(0);
        //4.更新
        contractService.update(contract);
        //跳转到修改界面
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 删除购销合同
     * 获取id
     * 调用service删除
     */
    @RequestMapping(value = "/delete", name = "删除购销合同")
    public String delete(String id) {
        contractService.delete(id);
        //跳转到修改界面
        return "redirect:/cargo/contract/list.do";
    }

}
