package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    /**
     * 保存货物
     */
    @Override
    public void save(ContractProduct contractProduct) {
        //1.设置基本属性（保存的id）
        contractProduct.setId(UUID.randomUUID().toString());
        //2.根据添加的货物的数量和单价计算总金额
        double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //3.添加货物的总金额
        contractProduct.setAmount(amount);
        //4.保存货物
        contractProductDao.insertSelective(contractProduct);
        //5.根据货物所属的购销合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //6.设置购销合同的货物数量
        contract.setProNum(contract.getProNum() + 1);
        //7.设置购销合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //8.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //1.获取货物修改之前的金额(根据id获取货物对象)
        ContractProduct oldCp = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //2.计算出货物的修改后的总金额
        double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //3.设置货物的总金额
        contractProduct.setAmount(amount);
        //4.更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //5.根据货物的合同id查询所有的合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //6.设置合同的总金额(合同的总金额 - 修改前货物的总金额 + 修改后货物的总金额)
        contract.setTotalAmount(contract.getTotalAmount() - oldCp.getAmount() + amount);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }
    /**
     * 货物删除
     *  设计3张表
     *      1.购销合同
     *      2.货物
     *      3.附件
     */
    @Override
    public void delete(String id) {
        //1.根据id获取货物（删除之前的货物金额）
        ContractProduct cP = contractProductDao.selectByPrimaryKey(id);
        //2.根据货物的购销合同id获取所有的合同
        Contract contract = contractDao.selectByPrimaryKey(cP.getContractId());
        //3.删除货物
        contractProductDao.deleteByPrimaryKey(id);
        //4.根据货物的id获取所有的附件
        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        //5.循环删除附件
        double amount = cP.getAmount();
        for (ExtCproduct extCproduct : list) {
            amount += extCproduct.getAmount();
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //6.设置购销合同的总金额
        contract.setTotalAmount(contract.getTotalAmount()-amount);
        //7.设置购销合同的货物和附件的数量
        contract.setProNum(contract.getProNum()-1);
        contract.setExtNum(contract.getExtNum()-list.size());
        //8.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }

    //批量保存
    public void save(List<ContractProduct> list) {
        for (ContractProduct contractProduct : list) {
            this.save(contractProduct);
        }
    }

    //根据船期查询所有货物数据
    public List<ContractProductVo> findByShipTime(String companyId, String inputDate) {
        return contractProductDao.findByShipTime(companyId,inputDate);
    }
}
