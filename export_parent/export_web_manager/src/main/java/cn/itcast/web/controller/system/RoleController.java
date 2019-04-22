package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.RoleService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    //查询所有
    //pageHelper分页
    @RequestMapping(value = "list", name = "角色列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        PageInfo info = roleService.findAll(companyId, page, size);
        request.setAttribute("page", info);
        return "/system/role/role-list";
    }

    /**
     * 进入新建页面
     */
    @RequestMapping(value = "toAdd", name = "新建角色列表")
    public String toAdd() {

        return "/system/role/role-add";
    }

    /**
     * 保存用户
     * 跳转到role-list页面
     */
    @RequestMapping(value = "edit", name = "新建/更新角色列表")
    public String edit(Role role) {
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtils.isEmpty(role.getId())) {
            //没有Id
            roleService.save(role);
        } else {
            //存在id
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    /**
     * 编辑角色
     */
    @RequestMapping(value = "toUpdate", name = "更新角色列表")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "/system/role/role-update";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", name = "删除角色")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    /**
     * 角色权限
     * @param
     * @return
     */
    @RequestMapping(value = "roleModule", name = "角色权限")
    public String roleModule(String roleid) {
        //根据id查询角色
           Role role = roleService.findById(roleid);
           request.setAttribute("role",role);
        return "/system/role/role-module";
    }
    @Autowired
    private ModuleService moduleService;
    /**
     * 使用ajax方式访问：构造ztrss树是json数据
     *     [
     *       { id:111, pId:11, name:"随意勾选 1-1-1"},
     *       { id:111, pId:11, name:"随意勾选 1-1-1"}
     *     ]
     *
     *     { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
     *
     *  返回的数据结构 : List<Map>
     *  模块列表
     *
     *  @ResponseBody
     *      ：配置到方法返回值上，将对象转化为json字符串
     *
     *
     *  默认勾选：
     *      json数据：{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
     *      1.根据角色id查询角色所具有是权限信息
     *      2.比较角色的权限信息和所有权限信息
     *
     */
    @RequestMapping(value="/initModuleData",name = "构造模块数据")
    public @ResponseBody List  initModuleData(String id) {
        //1.查询所有的模块
        List<Module> moduleList = moduleService.findAll();

        //根据角色id查询角色所具有是权限信息
        List<Module> roleModules = moduleService.findModuleByRoleId(id);

        //2.构造map集合
        List list = new ArrayList();
        //构造map
        for (Module module : moduleList) {  //循环所有的模块
            if("1".equals(module.getBelong())){
            //初始化map
            Map map = new HashMap<>();
            //添加map中的数据
            map.put("id",module.getId());   //模块id
            map.put("pId",module.getParentId());  //父模块id
            map.put("name",module.getName()); //模板名称

            if(roleModules.contains(module)) {
                map.put("checked",true); //默认勾选
            }
            //存入list集合
            list.add(map);
            }
        }
        //3.返回
        return list;
    }

    /**
     * 实现分配权限
     *      参数：
     *          1.角色id
     *          2.分配权限的模块的id字符串（以，分隔）
     *      步骤：
     *          1.调用service完成权限分配
     *          2.跳转到角色列表
     *
     */
    @RequestMapping(value="/updateRoleModule",name = "实现分配权限")
    public String updateRoleModule(String roleid,String moduleIds) {
        //1.调用service完成权限分配
        roleService.updateRoleModule(roleid,moduleIds);
        //2.跳转到角色列表
        return "redirect:/system/role/list.do";
    }
}
