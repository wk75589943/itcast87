package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ExtCproductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public void save(ExtCproduct extCproduct) {
        //1.设置基本属性（保存的id）
        extCproduct.setId(UUID.randomUUID().toString());
        //2.根据添加的货物的数量和单价计算总金额
        double amount = 0d;
        if(extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //3.添加附件的总金额
        extCproduct.setAmount(amount);
        //4.保存货物
        extCproductDao.insertSelective(extCproduct);
        //5.根据附件所属的购销合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //6.设置附件数量
        contract.setExtNum(contract.getExtNum() + 1);
        //7.设置总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //8.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //1.获取附件修改之前的金额
        ExtCproduct oldExt = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //2.计算附件修改之后的总金额
        double amount = 0d;
        if(extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //3.设置附件修改之后的总金额
        extCproduct.setAmount(amount);
        //4.更新货物
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //5.根据附件所属的购销合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(oldExt.getContractId());
        //6.计算购销合同的总金额     合同总金额 - 附件修改之前的金额  + 附件修改之后的金额
        contract.setTotalAmount(contract.getTotalAmount() - oldExt.getAmount() + amount);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //1.根据id查询到删除的附件对象
        ExtCproduct ext = extCproductDao.selectByPrimaryKey(id);
        //2.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(ext.getContractId());
        //3.修改购销合同的总金额和附件数量
        contract.setTotalAmount(contract.getTotalAmount()-ext.getAmount());
        contract.setExtNum(contract.getExtNum()-1);
        //4.删除附件
        extCproductDao.deleteByPrimaryKey(id);
        //5.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }
}
