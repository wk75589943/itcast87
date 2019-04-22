package cn.itcast.service.system;

import cn.itcast.domain.system.Module;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    //根据id查询
    Module findById(String id);

    //查询全部
    PageInfo findAll(int page, int size);

    List<Module> findAll();

    //根据id删除
    int delete(String id);

    //添加
    int save(Module module);

    //更新
    int update(Module module);

    //根据角色id查询所有的模块
    List<Module> findModuleByRoleId(String roleId);
    //获取当前登录用户的所有权限（菜单
    List<Module> findModuleByUserId(String userId);
}
