package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有角色并且分页
     */
    PageInfo findAll(String companyId, int page, int size);
    //保存角色
    int save(Role role);
    //根据id查询角色
    Role findById(String id);
        //更新角色
    int update(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int delete(String id);
    //对角色分配权限
    void updateRoleModule(String roleid, String moduleIds);
    //根据企业id所有的角色
    List<Role> findAll(String companyId);
    // 根据用户id查询用户所有的角色集合
    List<Role> findByUserId(String userId);
}
