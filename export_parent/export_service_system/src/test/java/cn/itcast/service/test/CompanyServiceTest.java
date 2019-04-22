package cn.itcast.service.test;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;

	@Test
	public void testFindAll() {
		List<Company> list = companyService.findAll();
		for (Company company : list) {
			System.out.println(company);
		}
	}

	@Test
	public void testFindPage() {
		PageInfo info = companyService.findPageByHelper(1, 5);
		System.out.println(info);
	}
}
