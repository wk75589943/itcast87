package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptDao {

	//分页查询列表
	/**
	 * pageHelper分页
	 *      1.startPage
	 *      2.查询全部  ： 根据企业id查询全部
	 *      3.自动分页
	 */
	/**
	 * companyId: 企业id
	 */
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
