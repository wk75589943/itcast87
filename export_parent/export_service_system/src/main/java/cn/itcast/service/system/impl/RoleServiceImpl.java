package cn.itcast.service.system.impl;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findById(String id) {
        return roleDao.findById(id);
    }

    //分页
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page,size);
        List<Role> list = roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    public int delete(String id) {
        return roleDao.delete(id);
    }

    public int save(Role role) {
        //指定id属性
        role.setId(UUID.randomUUID().toString());
        return roleDao.save(role);
    }

    public int update(Role role) {
        return roleDao.update(role);
    }

    /**
     * 对角色分配权限
     * @param roleid        ：角色id
     * @param moduleIds     ：模块字符串，以,分隔
     */
    public void updateRoleModule(String roleid, String moduleIds) {
        //1.删除此角色所具有的所有模块数据
        roleDao.deleteModule(roleid);
        //2.根据字符串获取模块id的数据   1，2，3，4
        String[] moduleArray = moduleIds.split(",");
        //3.循环并保存权限信息
        for (String moduleId : moduleArray) {
            roleDao.saveRoleModule(roleid,moduleId);
        }
    }
    //根据企业id所有的角色
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }
    // 根据用户id查询用户所有的角色集合
    public List<Role> findByUserId(String userId) {
        return roleDao.findByUserId(userId);
    }
}
