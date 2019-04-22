package cn.itcast.dao.system;

import cn.itcast.domain.system.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	int delete(String userId);

	//保存
	int save(User user);

	//更新
	int update(User user);
	//根据邮箱查询用户
    User findByEmail(String email);
	//根据用户id删除中间表数据
	void deleteUserRole(String userid);

	//向中间表中保存数据
	void saveUserRole(@Param("userId") String userId, @Param("roleId") String roleId);
}