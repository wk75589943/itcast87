package cn.itcast.web.controller.system;

import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    /**
     * companyId:
     * 当前操作模块所属的企业id
     */
    @RequestMapping(value = "/list", name = "模块列表")
    @RequiresPermissions("模块管理")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        PageInfo info = moduleService.findAll(page, size);
        request.setAttribute("page", info);
        return "system/module/module-list";

    }

    /**
     * 进入新增页面
     * @return
     */
    @RequestMapping(value = "/toAdd", name = "新增/修改列表")
    public String toAdd() {
        //查询所有模块
       List<Module> moduleList = moduleService.findAll();
       //项数据保存在requeset域中
       request.setAttribute("menus",moduleList);
        return "system/module/module-add";

    }
    /**
     * 新增模块
     *      1.获取表单数据构造module对象
     *      2.添加对应的企业属性
     * 更新模块
     */
    @RequestMapping(value = "/edit" , name = "编辑模块")
    public String edit(Module module) {
        //1.判断是否具有id属性
        if(UtilFuns.isEmpty(module.getId())) {
            //2.没有id，保存
            moduleService.save(module);
        }else{
            //3.具有id，更新
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }
    /**
     * 进入到修改界面
     *      1.获取到id
     *      2.根据id进行查询
     *      3.查询所有的模块
     *      4.保存到request域中
     *      5.跳转到修改界面
     */
    @RequestMapping(value = "/toUpdate" , name = "进入到修改界面")
    public String toUpdate(String id) {
        //根据id进行查询
        Module module = moduleService.findById(id);
        request.setAttribute("module",module);
        //查询所用模块数据，为了构造下拉框数据
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus",list);
        //跳转到修改界面
        return "system/module/module-update";
    }
    /**
     * 删除模块
     *      获取id
     *      调用service删除
     */
    @RequestMapping(value = "/delete" , name = "删除模块")
    public String delete(String id) {
        moduleService.delete(id);
        //跳转到修改界面
        return "redirect:/system/module/list.do";
    }
}
