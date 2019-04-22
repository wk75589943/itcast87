package cn.itcast.web.controller.cargo;

import cn.itcast.common.utils.BeanMapUtils;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import cn.itcast.domain.system.User;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@Controller
@RequestMapping("/cargo/export")
public class PdfController extends BaseController {
    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    /**
     * 图形报表
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(String id) throws Exception {

        //构造模板数据
        //a.根据id查询报运单，将对象转化为map集合
        Export export = exportService.findById(id);
        Map<String, Object> map = BeanMapUtils.beanToMap(export);
        //b.根据报运单id，查询此报运单下的所有货物
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(example);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/export.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造数据列表的数据源对象
        //4.构造jasperPrint
        JasperPrint jp = JasperFillManager.fillReport(fis,map,dataSource);
        //5.输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }


    /**
     * 图形报表
     */
    @RequestMapping("/exportPdf6")
    public void exportPdf6() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test06.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造数据列表的数据源对象
        List list = new ArrayList();
        for(int i=0;i<6;i++) {
            Map map = new HashMap();
            map.put("title","标题"+i);
            map.put("value",new Random().nextInt(100));
            list.add(map);
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //4.构造jasperPrint
        JasperPrint jp = JasperFillManager.fillReport(fis,new HashMap<>(),dataSource);
        //5.输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }

    /**
     * 传递数据源数据-connection
     */
    @RequestMapping("/exportPdf5")
    public void exportPdf5() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test05.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造JasperPrint对象，

        /**
         * 构造数据列表
         */
        List<User> list = new ArrayList<>();
        for(int i=0;i<10;i++) {
            for(int j=0;j<5;j++) {
                User user = new User();
                user.setUserName("name"+j);
                user.setEmail(j+"@qq.com");
                user.setCompanyName("企业"+i);
                user.setDeptName("部门"+j);
                list.add(user);
            }
        }

        //将list构造为jrdatasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //fis : 模板文件输入流，第二个参数：传递到模板文件中的key-value类型的参数 ,第三个参数：列表参数
        JasperPrint jp = JasperFillManager.fillReport(fis,new HashMap(),dataSource);
        //4.以pdf形式输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }

    /**
     * 传递数据源数据-connection
     */
    @RequestMapping("/exportPdf4")
    public void exportPdf4() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test04.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造JasperPrint对象，

        /**
         * 构造数据列表
         */
        List<User> list = new ArrayList<>();
        for(int i=0;i<10;i++) {
            User user = new User();
            user.setUserName("name"+i);
            user.setEmail(i+"@qq.com");
            user.setCompanyName("企业"+i);
            user.setDeptName("部门"+i);
            list.add(user);
        }

        //将list构造为jrdatasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //fis : 模板文件输入流，第二个参数：传递到模板文件中的key-value类型的参数 ,第三个参数：列表参数
        JasperPrint jp = JasperFillManager.fillReport(fis,new HashMap(),dataSource);
        //4.以pdf形式输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }


    /**
     * 传递数据源数据-connection
     * @throws Exception
     */
    @RequestMapping("/exportPdf3")
    public void exportPdf3() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test03.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造JasperPrint对象，

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///saas-export", "root", "111111");

        //fis : 模板文件输入流，第二个参数：传递到模板文件中的key-value类型的参数 ,第三个参数：列表参数
        JasperPrint jp = JasperFillManager.fillReport(fis,new HashMap(),conn);
        //4.以pdf形式输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }

    /**
     * 传递map类型的参数
     */
    @RequestMapping("/exportPdf2")
    public void exportPdf2() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test02.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造JasperPrint对象，
        Map params = new HashMap();
        params.put("username","张三");
        params.put("email","12345@qq.com");
        params.put("companyName","传智播客");
        params.put("deptName","讲师");
        //fis : 模板文件输入流，第二个参数：传递到模板文件中的key-value类型的参数 ,第三个参数：列表参数
        JasperPrint jp = JasperFillManager.fillReport(fis,params,new JREmptyDataSource());
        //4.以pdf形式输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }


    /*入门案例*/
    @RequestMapping("/exportPdf1")
    public void exportPdf1() throws Exception {
        //1.加载jasper文件
        String path = session.getServletContext().getRealPath("/")+"/jasper/test01.jasper";
        //2.构造文件输入流
        FileInputStream fis = new FileInputStream(path);
        //3.构造JasperPrint对象，
        //fis : 模板文件输入流，第二个参数：传递到模板文件中的key-value类型的参数 ,第三个参数：列表参数
        JasperPrint jp = JasperFillManager.fillReport(fis,new HashMap(),new JREmptyDataSource());
        //4.以pdf形式输出
        JasperExportManager.exportReportToPdfStream(jp,response.getOutputStream());
    }
}
