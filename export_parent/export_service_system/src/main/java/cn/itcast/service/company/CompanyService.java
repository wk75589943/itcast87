package cn.itcast.service.company;

import cn.itcast.common.entity.PageResult;
import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CompanyService {
	//查询所有企业
	List<Company> findAll();

	//保存企业
	void save(Company company);

	//根据id查询
	Company findById(String id);

	//更新
	void update(Company company);

	void delete(String id);

	//传统分页查询
	PageResult findPage(int page, int size);

	PageInfo findPageByHelper(int page, int size);
}
