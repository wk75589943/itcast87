package cn.itcast.service.system.impl;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDao deptDao;

	/**
	 * 分页：
	 *      1.startPage
	 *      2.查询全部列表
	 *      3.构造pagebean
	 */
	public PageInfo findAll(String companyId, int page, int size) {
		//1.调用startPage方法
		PageHelper.startPage(page,size);
		//2.查询全部列表
		List<Dept> list = deptDao.findAll(companyId);
		//构造pagebean
		return new PageInfo(list);
	}

	//查询所有部门
	public List<Dept> findAll(String companyId) {
		return deptDao.findAll(companyId);
	}

	//保存

	/**
	 * 1.设置基础参数（id）
	 * 2.保存
	 * @param dept
	 */
	public void save(Dept dept) {
		dept.setId(UUID.randomUUID().toString());
		deptDao.save(dept);
	}

	//更新
	public void update(Dept dept) {
		deptDao.update(dept);
	}

	//删除
	public void delete(String id) {
		deptDao.delete(id);
	}

	//根据id查询
	public Dept findById(String id) {
		return deptDao.findById(id);
	}
}
