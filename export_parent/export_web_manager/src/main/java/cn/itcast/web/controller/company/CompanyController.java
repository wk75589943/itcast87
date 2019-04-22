package cn.itcast.web.controller.company;

import cn.itcast.common.entity.PageResult;
import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;

	/**
	 * company/list.do
	 *      RequestParam : 配置到方法参数
	 *              指定此参数的默认值
	 */

//	@RequestMapping(value = "/list" , name = "企业列表")
//	public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size) {
//		//分页对象
//		PageResult pr = companyService.findPage(page,size);
////		List<Company> list = companyService.findAll();
//		request.setAttribute("page",pr);
//		return "company/company-list";
//	}

	/**
	 * 通过pageHelper实现分页
	 */
	@RequestMapping(value = "/list" , name = "企业列表")
	public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size) {
		//分页对象

		//1.调用service查询
		PageInfo info = companyService.findPageByHelper(page, size);
		//2.存入request域中
		request.setAttribute("page",info);
		return "company/company-list";
	}

	//保存
	@RequestMapping(value = "/save" , name = "添加企业")
	public String save(Date date) {
		System.out.println(date);
		return "success";
	}

	/**
	 * 进入新增界面
	 *      页面跳转到  company/company-add.jsp
	 */
	@RequestMapping(value = "/toAdd" , name = "进入新增界面")
	public String toAdd() {
		return "company/company-add";
	}


	/**
	 * 保存企业
	 *      1.接受页面参数
	 *             获取企业对象company
	 *      2.调用service保存
	 *      3.重定向到企业列表
	 */
	@RequestMapping(value = "/edit" , name = "进入新增界面")
	public String edit(Company company) {
		//1.判断是否具有id参数 （没有id：保存。具有id：更新）
		if(UtilFuns.isEmpty(company.getId())) {
			//没有id
			//2.调用service保存
			companyService.save(company);
		}else {
			//有id
			companyService.update(company);
		}
		//3.重定向到企业列表
		return "redirect: /company/list.do";
	}


	/**
	 * 进入修改界面
	 *      获取参数id
	 *      根据id查询企业
	 *      页面跳转到  company/company-update.jsp
	 */
	@RequestMapping(value = "/toUpdate" , name = "进入新增界面")
	public String toUpdate(String id) {
		//2.根据id查询企业
		Company company = companyService.findById(id);
		request.setAttribute("company",company);
		//3.页面跳转
		return "company/company-update";
	}


	/**
	 * 根据id删除
	 *      获取参数id
	 *      调用service删除
	 *      重定向到企业列表
	 */
	@RequestMapping(value = "/delete" , name = "删除企业")
	public String delete(String id) {
		//2.调用service删除
		companyService.delete(id);
		//3.重定向到企业列表
		return "redirect: /company/list.do";
	}
}
