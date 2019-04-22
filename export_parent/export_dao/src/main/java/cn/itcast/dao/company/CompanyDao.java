package cn.itcast.dao.company;

import cn.itcast.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {

	//查询全部企业
	List<Company> findAll();

	//保存
	void save(Company company);

	//根据id查询
	Company findById(String id);

	//更新
	void update(Company company);

	//删除
	void delete(String id);

	//查询总记录数
	long findCount();

	//分页查询数据列表   sql语句的索引，每页查询条数

	/**
	 *  相当于向sql映射文件中传递了一个Map集合
	 *      key = param注解中配置的名称value
	 *      value = 传递的参数值
	 */
	List findPage(@Param("index") int index, @Param("size") int size);
}
