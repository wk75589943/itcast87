package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

	/**
	 * 分页
	 */
	PageInfo findAll(String companyId, int page, int size);

	//查询所有部门
	List<User> findAll(String companyId);

	//保存
	void save(User user);

	//更新
	void update(User user);

	//删除
	void delete(String id);

	//根据id查询
	User findById(String id);
	//根据邮箱查询用户
    User findByEmail(String email);
	//分配角色
    void updateUserRoles(String userid, String[] roleIds);
}
