package cn.itcast.dao.system;

import cn.itcast.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
    //根据id查询
    Role findById(String id);

    //查询全部
    List<Role> findAll(String companyId);

    //根据id删除
    int delete(String id);

    //添加
    int save(Role role);

    //更新
    int update(Role role);

    //根据角色id删除所有的模块信息，删除中间表数据
    void deleteModule(String roleid);

    //添加角色和id的中间表数据
    void saveRoleModule(@Param("roleId") String roleid,@Param("moduleId")String moduleId);
    // 根据用户id查询用户所有的角色集合
    List<Role> findByUserId(String userId);
}

