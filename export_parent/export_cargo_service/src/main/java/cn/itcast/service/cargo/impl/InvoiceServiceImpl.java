package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.EntrustDao;
import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.domain.cargo.Entrust;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.service.cargo.InvoiceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private EntrustDao entrustDao;
    @Autowired
    private PackingDao packingDao;

    @Override
    public int deleteByPrimaryKey(String invoiceId) {
        return 0;
    }

    @Override
    public int insert(Invoice record) {
        return 0;
    }

    @Override
    public int insertSelective(Invoice record) {
        return 0;
    }

    @Override
    public List<Invoice> selectByExample(InvoiceExample example) {
        return null;
    }

    @Override
    public Invoice selectByPrimaryKey(String invoiceId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Invoice record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Invoice record) {
        return 0;
    }

    //分页查询
    public PageInfo findAll(InvoiceExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Invoice> list = invoiceDao.selectByExample(example);
        return new PageInfo(list);
    }

    //更新
    public void update(Invoice invoice) {
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }

    //根据Id查询
    public Invoice findById(String id) {
        return invoiceDao.selectByPrimaryKey(id);
    }

    //保存
    public void save(Invoice invoice) {
        //获取委托单对象
        Entrust entrust = entrustDao.selectByPrimaryKey(invoice.getInvoiceId());
        //保存时设置委托单的状态
        entrust.setState(2);
        //更新委托单
        entrustDao.updateByPrimaryKeySelective(entrust);
        //获取装箱对象
        Packing packing = packingDao.selectByPrimaryKey(invoice.getInvoiceId());
        //根据装箱的合同号设置发票的scno合同号
        invoice.setScNo(packing.getExportNos());
        //设置发票基础状态
        invoice.setState(0);
        invoice.setCreateTime(new Date());
        //保存
        invoiceDao.insertSelective(invoice);

    }

    //删除
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }
}
