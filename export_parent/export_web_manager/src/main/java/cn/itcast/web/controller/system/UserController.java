package cn.itcast.web.controller.system;

import cn.itcast.common.utils.MailUtil;
import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.jms.EmailProducer;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/system/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptService;
	//用户列表分页

	/**
	 * companyId:
	 *      当前操作用户所属的企业id
	 */
	@RequestMapping(value="/list",name = "用户列表分页")
	public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size) {
		//1.调用service查询用户列表
		PageInfo info = userService.findAll(companyId, page, size);
		//2.将用户列表保存到request域中
		request.setAttribute("page",info);
		//3.跳转到对象的页面
		return "system/user/user-list";
	}

	/**
	 * 进入新增用户页面
	 *      1.查询所用用户数据，为了构造下拉框数据
	 */
	@RequestMapping(value = "/toAdd" , name = "进入新增用户界面")
	public String toAdd() {
		//1.查询所用用户数据，为了构造下拉框数据
		List<Dept> deptList = deptService.findAll(companyId);
		//2.将用户列表保存到request域中
		request.setAttribute("deptList",deptList);
		return "system/user/user-add";
	}
	@Autowired
	private EmailProducer emailProducer;
	/**
	 * 新增用户
	 *      1.获取表单数据构造user对象
	 *      2.添加对应的企业属性
	 * 更新用户
	 */
	@RequestMapping(value = "/edit" , name = "编辑用户")
	public String edit(User user) {
		user.setCompanyId(companyId);
		user.setCompanyName(companyName);
		//1.判断是否具有id属性
		if(UtilFuns.isEmpty(user.getId())) {
			String password = user.getPassword();
			//2.没有id，保存
			userService.save(user);
			//发送邮件
			/*try {
				MailUtil.sendMsg(user.getEmail(),"申请成功","尊敬的云平台用户:欢迎您加入云平台大家庭，您的账号为:"+user.getUserName()+"，密码为:"+password);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			emailProducer.send(user.getEmail(),"申请成功","尊敬的云平台用户:欢迎您加入云平台大家庭，您的账号为:"+user.getUserName()+"，密码为:"+password);
		}else{
			//3.具有id，更新
			userService.update(user);
		}
		return "redirect:/system/user/list.do";
	}

	/**
	 * 进入到修改界面
	 *      1.获取到id
	 *      2.根据id进行查询
	 *      3.查询所有的用户
	 *      4.保存到request域中
	 *      5.跳转到修改界面
	 */
	@RequestMapping(value = "/toUpdate" , name = "进入到修改界面")
	public String toUpdate(String id) {
		//1.查询所用用户数据，为了构造下拉框数据
		List<Dept> deptList = deptService.findAll(companyId);
		//2.将用户列表保存到request域中
		request.setAttribute("deptList",deptList);
		//根据id进行查询
		User user = userService.findById(id);
		request.setAttribute("user",user);
		//跳转到修改界面
		return "system/user/user-update";
	}

	/**
	 * 删除用户
	 *      获取id
	 *      调用service删除
	 */
	@RequestMapping(value = "/delete" , name = "删除用户")
	public String delete(String id) {
		userService.delete(id);
		//跳转到修改界面
		return "redirect:/system/user/list.do";
	}
	@Autowired
	private RoleService roleService;
	/**
	 * 进入角色生成界面
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/roleList" , name = "用户角色")
	public String roleList(String id) {
			//根据id查询对应的用户
		User user = userService.findById(id);
		request.setAttribute("user",user);
		//根据企业id查询所有的角色
		List<Role> roleList = roleService.findAll(companyId);
		request.setAttribute("roleList",roleList);
		//3.根据用户id查询用户所有的角色集合
		List<Role> userRoles = roleService.findByUserId(id);
		//拼接字符串  id，id，id
		String userRoleStr = "";
		for (Role userRole : userRoles) {
			userRoleStr += userRole.getId()+",";
		}
		request.setAttribute("userRoleStr",userRoleStr);
		return "system/user/user-role";
	}
	@RequestMapping(value = "/changeRole" , name = "实现分配角色")
	public String changeRole(String userid,String [] roleIds) {
		//1.调用service分配角色
//		System.out.println(userid);
//		for (String roleId : roleIds) {
//			System.out.println("roleId="+roleId);
//		}

		userService.updateUserRoles(userid,roleIds);
		//2.页面跳转
		return "redirect:/system/user/list.do";
	}
}
