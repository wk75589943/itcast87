package cn.itcast.service.company.impl;

import cn.itcast.common.entity.PageResult;
import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	public List<Company> findAll() {
		return companyDao.findAll();
	}

	//保存企业
	public void save(Company company) {
		//设置企业id
		company.setId(UUID.randomUUID().toString());
		companyDao.save(company);
	}

	//根据id查询
	public Company findById(String id) {
		return companyDao.findById(id);
	}

	//更新
	public void update(Company company) {
		companyDao.update(company);
	}

	//根据id删除
	public void delete(String id) {
		companyDao.delete(id);
	}

	//传统分页查询
	/**
	 *  page：页码   1   2         3
	 *              0    5        10
	 *
	 *  size：每页查询条数  5
	 *                                     索引 条数
	 *  SQL:SELECT * FROM ss_company LIMIT 0,5
	 */
	public PageResult findPage(int page, int size) {
		//1.调用dao查询总记录数
		long total = companyDao.findCount();
		//2.调用dao分页查询数据列表  索引   条数
		List list = companyDao.findPage((page-1)*size,size);
//			 * 调用构造方法
//				* @param total     总记录数
//				* @param rows      数据列表
//				* @param page      当前页码
//				* @param size      每页查询条数
		PageResult pr = new PageResult(total,list,page,size);
		return pr;
	}

	/**
	 * 使用pageHelper插件分页
	 *      1.pageHelper.startPage()
	 *      2.查询全部
	 *      3.构造分页数据pageInfo
	 */
	public PageInfo findPageByHelper(int page, int size) {
		PageHelper.startPage(page,size);
		//查询全部
		List<Company> list = companyDao.findAll();
		return new PageInfo(list);
	}
}
