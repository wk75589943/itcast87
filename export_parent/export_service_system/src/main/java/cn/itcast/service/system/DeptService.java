package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {

	/**
	 * 分页
	 */
	PageInfo findAll(String companyId,int page,int size);

	//查询所有部门
	List<Dept> findAll(String companyId);

	//保存
	void save(Dept dept);

	//更新
	void update(Dept dept);

	//删除
	void delete(String id);

	//根据id查询
	Dept findById(String id);
}
