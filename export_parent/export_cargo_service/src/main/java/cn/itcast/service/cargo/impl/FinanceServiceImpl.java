package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.FinanceDao;
import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.service.cargo.FinanceService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceDao financeDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Finance finance) {
        Invoice invoice = invoiceDao.selectByPrimaryKey(finance.getFinanceId());
        invoice.setState(2);
        //更新invoice
        invoiceDao.updateByPrimaryKeySelective(invoice);
        //设置finance的初始状态
        finance.setState(0);
        finance.setCreateTime(new Date());
        //保存finance
        financeDao.insertSelective(finance);
    }

    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void delete(String id) {
        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(FinanceExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Finance> list = financeDao.selectByExample(example);
        return new PageInfo(list);
    }
}
