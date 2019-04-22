package cn.itcast.dao.test;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class FactoryDaoTest {
    @Autowired
    private FactoryDao factoryDao;

    @Test
    public void testUpdate() {
        Factory factory = new Factory();
        factory.setId("4028817a3ae2ac42013ae3550357000d");
        factory.setFactoryName("临潼华清蜡厂123");
        //factoryDao.updateByPrimaryKey(factory);
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 条件查询
     * 使用example查询操作步骤
     * //1.初始化example对象
     * //2.构造criteria对象
     * //3.以java代码的形式构造查询条件
     */
    @Test
    public void findByExample() throws Exception {
        //1.初始化example对象
        FactoryExample example = new FactoryExample();
        //2.构造criteria对象
        FactoryExample.Criteria criteria = example.createCriteria();
        //3.以java代码的形式构造查询条件
        criteria.andFactoryNameLike("%顺驰%");
        criteria.andContactsEqualTo("王明");
        List<Factory> list = factoryDao.selectByExample(example);
        for (Factory factory : list) {
            System.out.println(factory);
        }
    }
}
