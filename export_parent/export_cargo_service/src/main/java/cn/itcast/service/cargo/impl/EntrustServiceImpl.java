package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.EntrustDao;
import cn.itcast.domain.cargo.Entrust;
import cn.itcast.domain.cargo.EntrustExample;
import cn.itcast.service.cargo.EntrustService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class EntrustServiceImpl implements EntrustService {
    @Autowired
    private EntrustDao entrustDao;
    //根据id查询
    public Entrust findById(String id) {
        return entrustDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Entrust entrust) {

    }

    @Override
    public void update(Entrust entrust) {

    }

    @Override
    public void delete(String id) {

    }

    //发票分页查询
    public PageInfo findAll(EntrustExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Entrust> list = entrustDao.selectByExample(example);
        return new PageInfo(list);
    }
}
