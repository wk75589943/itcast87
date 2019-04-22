package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImple implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    //分页
    public PageInfo findAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }

    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    public int delete(String id) {
        return moduleDao.delete(id);
    }

    public int save(Module module) {
        //指定id属性
        module.setId(UUID.randomUUID().toString());
        return moduleDao.save(module);
    }

    public int update(Module module) {
        return moduleDao.update(module);
    }


    //根据角色id查询所有模块
    public List<Module> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    @Autowired
    private UserDao userDao;

    /**
     * 根据登录的用户id查询用户所具有的所有权限（模块，菜单）
     * 1.根据用户id查询用户
     * 2.根据用户degree级别判断
     * 3.如果degree==0 （内部的sass管理）
     * 根据模块中的belong字段进行查询，belong = "0";
     * 4.如果degree==1 （租用企业的管理员）
     * 根据模块中的belong字段进行查询，belong = "1";
     * 5.其他的用户类型
     * 借助RBAC的数据库模型，多表联合查询出结果
     */
    public List<Module> findModuleByUserId(String userId) {
        //1。根据用户id查询用户
        User user = userDao.findById(userId);
        //2.根据用户degree级别判断
        //如果degree==0 （内部的sass管理）
        if (user.getDegree() == 0) {
            //根据模块中的belong字段进行查询，belong = "0";
            return moduleDao.findByBelong("0");
        }
        //如果degree==1 （租用企业的管理员）
        else if (user.getDegree() == 1) {
            //根据模块中的belong字段进行查询，belong = "1";
            return moduleDao.findByBelong("1");
        } else {
            //5.其他的用户类型
            //   借助RBAC的数据库模型，多表联合查询出结果
            return moduleDao.findModuleByUserId(userId);
        }
    }
}
