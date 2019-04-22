package cn.itcast.service.impl;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;

    @Override
    public List getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    @Override
    public List getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }

    @Override
    public List getOnlineData(String companyId) {
        return statDao.getOnlineData(companyId);
    }
}
