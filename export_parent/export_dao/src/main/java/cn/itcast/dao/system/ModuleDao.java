package cn.itcast.dao.system;

import cn.itcast.domain.system.Module;

import java.util.List;

public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加
    int save(Module module);

    //更新
    int update(Module module);

    //查询全部
    List<Module> findAll();

    //根据角色id查询所有模块
    List<Module> findModuleByRoleId(String roleId);
    //根据useri获取所有的模块
    List<Module> findModuleByUserId(String userId);

    //根据从属belong字段进行查询
    List<Module> findByBelong(String belong);

}
