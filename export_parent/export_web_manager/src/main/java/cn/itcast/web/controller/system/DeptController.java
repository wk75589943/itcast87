package cn.itcast.web.controller.system;

import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/system/dept")
public class DeptController extends BaseController{

	@Autowired
	private DeptService deptService;

	//部门列表分页

	/**
	 * companyId:
	 *      当前操作用户所属的企业id
	 */
	@RequestMapping(value="/list",name = "部门列表分页")
	public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size) {
		//1.调用service查询部门列表
		PageInfo info = deptService.findAll(companyId, page, size);
		//2.将部门列表保存到request域中
		request.setAttribute("page",info);
		//3.跳转到对象的页面
		return "system/dept/dept-list";
	}

	/**
	 * 进入新增部门页面
	 *      1.查询所用部门数据，为了构造下拉框数据
	 */
	@RequestMapping(value = "/toAdd" , name = "进入新增部门界面")
	public String toAdd() {
		//1.查询所用部门数据，为了构造下拉框数据
		List<Dept> deptList = deptService.findAll(companyId);
		//2.将部门列表保存到request域中
		request.setAttribute("deptList",deptList);
		return "system/dept/dept-add";
	}

	/**
	 * 新增部门
	 *      1.获取表单数据构造dept对象
	 *      2.添加对应的企业属性
	 * 更新部门
	 */
	@RequestMapping(value = "/edit" , name = "编辑部门")
	public String edit(Dept dept) {
		dept.setCompanyId(companyId);
		dept.setCompanyName(companyName);
		//1.判断是否具有id属性
		if(UtilFuns.isEmpty(dept.getId())) {
			//2.没有id，保存
			deptService.save(dept);
		}else{
			//3.具有id，更新
			deptService.update(dept);
		}
		return "redirect:/system/dept/list.do";
	}

	/**
	 * 进入到修改界面
	 *      1.获取到id
	 *      2.根据id进行查询
	 *      3.查询所有的部门
	 *      4.保存到request域中
	 *      5.跳转到修改界面
	 */
	@RequestMapping(value = "/toUpdate" , name = "进入到修改界面")
	public String toUpdate(String id) {
		//根据id进行查询
		Dept dept = deptService.findById(id);
		request.setAttribute("dept",dept);
		//查询所有的部门
		List<Dept> deptList = deptService.findAll(companyId);
		request.setAttribute("deptList",deptList);
		//跳转到修改界面
		return "system/dept/dept-update";
	}

	/**
	 * 删除部门
	 *      获取id
	 *      调用service删除
	 */
	@RequestMapping(value = "/delete" , name = "删除部门")
	public String delete(String id) {
		deptService.delete(id);
		//跳转到修改界面
		return "redirect:/system/dept/list.do";
	}

}
