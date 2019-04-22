package cn.itcast.dao.test;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.dao.stat.StatDao;
import cn.itcast.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class StatDaoTest {

	@Autowired
	private StatDao statDao;

	@Test
	public void testFindAll() {
		List<Map> factoryData = statDao.getFactoryData("1");
		for (Map map : factoryData) {

		}
	}
}
