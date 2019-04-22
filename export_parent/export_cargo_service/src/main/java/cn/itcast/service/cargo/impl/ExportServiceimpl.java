package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.*;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductResult;
import cn.itcast.vo.ExportResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ExportServiceimpl implements ExportService {
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExportProductDao exportProductDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ExtEproductDao extEproductDao;
    //    ExtCproduct购销合同附件
    //    ExtEproduct报运单附件

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    /**
     * 新增报运单
     *
     * @param export
     */
    public void save(Export export) {
        //1.设置基本参数 （id，创建时间，状态为草稿）
        export.setId(UUID.randomUUID().toString());
        export.setInputDate(new Date());//制单时间
        export.setCreateTime(new Date());//创建时间
        export.setState(0);
        //2.修改所有购销合同的状态
        //拼接：用for循环拼接时用StringBuilder中的append耗时少
        StringBuilder stringBuilder = new StringBuilder();
        String[] ids = export.getContractIds().split(",");
        for (String id : ids) {
            Contract contract = contractDao.selectByPrimaryKey(id);
            contract.setState(2);//已报云
            stringBuilder.append(contract.getContractNo()).append(" ");
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //3.保存报运单下的所有货物
        //i  根据购销合同的id集合查询所有购销合同下的货物
        ContractProductExample cpExample = new ContractProductExample();
        ContractProductExample.Criteria cpCriteria = cpExample.createCriteria();
        cpCriteria.andContractIdIn(Arrays.asList(ids));//将数组转化 为 list集合
        List<ContractProduct> cps = contractProductDao.selectByExample(cpExample);
        //ii 循环购销合同货物，并构造所有的报运单货物
        //iii 保存报运单商品

        HashMap<String, String> idsMap = new HashMap<>();
        for (ContractProduct cp : cps) {
            ExportProduct ep = new ExportProduct();
            //基本属性的数据来源：货物（实体类属性名称一模一样）
            /*import org.springframework.beans.BeanUtils
            这个包下的BeanUtils.copyProperties(对象A,对象B),是将A的值复制到B*/
            BeanUtils.copyProperties(cp, ep);//数据源对象，目的对象
            ep.setId(UUID.randomUUID().toString());
            //货物外键
            ep.setExportId(export.getId());
            exportProductDao.insertSelective(ep);
            //合同货物id 和 报运商品id之间的关系
            // 购销合同的货物id 1  (xxxxx)==  报运商品的id  3 (xxxx)
            idsMap.put(cp.getId(), ep.getId());
        }
        //4.保存报运单下的所有附件
        //i 查询所有购销合同下的附件
        ExtCproductExample ecExample = new ExtCproductExample();
        ExtCproductExample.Criteria ecCriteria = ecExample.createCriteria();
        ecCriteria.andContractIdIn(Arrays.asList(ids));
        List<ExtCproduct> ecps = extCproductDao.selectByExample(ecExample);
        //ii 循环所有附件，并且构造报运附件对象
        //iii 保存报运附件
        for (ExtCproduct ecp : ecps) {
            //处理的是购销合同下货物id为1的附件
            ExtEproduct eep = new ExtEproduct();
            BeanUtils.copyProperties(ecp, eep);
            eep.setId(UUID.randomUUID().toString());
            // 购销合同的货物id 1  (xxxxx)==  报运商品的id  3 (xxxx)
            eep.setExportProductId(idsMap.get(ecp.getContractProductId()));
            extEproductDao.insertSelective(eep);
        }
        //5.保存报运单
        export.setProNum(cps.size());//货物数量
        export.setExtNum(ecps.size());//附件数量
        //购销合同名字保存到报运单中
        export.setCustomerContract(stringBuilder.toString());
        exportDao.insertSelective(export);

    }

    /**
     * export
     * 第一部分：报运单的主体信息
     * 第二部分：修改的报运单商品信息
     * <p>
     * 更新：
     */
    public void update(Export export) {
        //1.更新报运单主体
        exportDao.updateByPrimaryKeySelective(export);
        //2.循环更新多个商品
        if (export.getExportProducts() != null) {
            for (ExportProduct exportProduct : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    /**
     * 删除表
     *
     * @param id
     */
    public void delete(String id) {
        //出口报运单
        exportDao.deleteByPrimaryKey(id);
        //报运商品表
        exportProductDao.deleteByPrimaryKey(id);
        //报运附件表
        extEproductDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    /**
     * 处理报运结果
     */
    public void updateE(ExportResult result) {
        //1.查询报运单
        Export export = exportDao.selectByPrimaryKey(result.getExportId());
        //2.设置报运单属性（状态，和说明）
        export.setState(result.getState());
        export.setRemark(result.getRemark());
        exportDao.updateByPrimaryKeySelective(export);
        //3.循环处理报运商品
        for (ExportProductResult epr : result.getProducts()) {
            ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(epr.getExportProductId());
            //4.对报运商品的税收修改
            exportProduct.setTax(epr.getTax());
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }

    }

}
