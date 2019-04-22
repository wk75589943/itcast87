package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.FactoryService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Action;
import java.util.List;
import java.util.UUID;

@Service
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;
    //保存
    public void save(Factory factory) {
        factory.setId(UUID.randomUUID().toString());
        factoryDao.insertSelective(factory);
    }

    //更新
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    //删除
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    //根据id查询
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    //条件查询
    public List<Factory> findAll(FactoryExample example) {
        return factoryDao.selectByExample(example);
    }
}
