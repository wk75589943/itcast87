package cn.itcast.web.controller.cargo;

import cn.itcast.common.utils.UtilFuns;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.FileUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    /**
     * 进入货物列表
     * 1.需要根据购销合同id查询所有货物
     * 2.查询所有生产厂家
     */
    @RequestMapping(value = "/list", name = "货物列表分页")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        request.setAttribute("contractId", contractId);
        //1.需要根据购销合同id查询所有货物
        ContractProductExample example1 = new ContractProductExample();
        ContractProductExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andContractIdEqualTo(contractId);
        PageInfo info = contractProductService.findAll(example1, page, size);
        request.setAttribute("page", info);
        //2.查询所有生产厂家
        FactoryExample example2 = new FactoryExample();
        FactoryExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example2);
        request.setAttribute("factoryList", factoryList);
        //业务逻辑
        return "cargo/product/product-list";
    }

    /**
     * 进入新增购销合同页面
     * 1.查询所用购销合同数据，为了构造下拉框数据
     */
    @RequestMapping(value = "/toAdd", name = "进入新增购销合同界面")
    public String toAdd() {
        return "cargo/product/product-add";
    }


    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 新增购销合同
     * 1.获取表单数据构造contract对象
     * 2.添加对应的企业属性
     * 更新购销合同
     */
    @RequestMapping(value = "/edit", name = "编辑购销合同")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);
        if (StringUtils.isEmpty(contractProduct.getId())) {
            String img = "";
            if (productPhoto != null) {
                img = "http://" + fileUploadUtil.upload(productPhoto); //返回当前请求的图片路径
            }
            contractProduct.setProductImage(img);
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }

    /**
     * 进入到修改界面
     */
    @RequestMapping(value = "/toUpdate", name = "进入到修改界面")
    public String toUpdate(String id) {
        //货物信息
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);

        //查询所有生产厂家
        FactoryExample example2 = new FactoryExample();
        FactoryExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example2);
        request.setAttribute("factoryList", factoryList);
        return "cargo/product/product-update";
    }

    /**
     * 删除购销合同
     * 获取id
     * 调用service删除
     */
    @RequestMapping(value = "/delete", name = "删除购销合同")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    /**
     * 进入上传页面
     */
    @RequestMapping(value = "/toImport", name = "进入上传页面")
    public String toImport(String contractId) {
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }

    /**
     * 文件上传
     * MultipartFile ： springmvc提供的文件对象
     */
    @RequestMapping(value = "/import", name = "进入上传页面")
    public String importExcel(MultipartFile file, String contractId) throws Exception {
        List<ContractProduct> list = new ArrayList();
        //1.根据上传的excel文件创建工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //2.获取第一个sheet
        Sheet sheet = wb.getSheetAt(0);//数组下标
        //3.循环获取每一个行对象
        //sheet.getLastRowNum 获取最后一行的数组下标
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            //4.循环获取每一个单元格
            Object objs[] = new Object[10];
            //row.getLastCellNum() 获取最大行数
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                //5.获取单元格中的每一个数据
                if (cell != null) {
                    objs[j] = getCellValue(cell);
                }
            }
            //6.构造货物对象
            ContractProduct cp = new ContractProduct(objs, companyId, companyName);
            //设置购销合同id
            cp.setContractId(contractId);
            list.add(cp);
        }

        //调用service服务完成批量保存
        contractProductService.save(list);

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    public Object getCellValue(Cell cell) {
        /**
         * 获取单元格的类型
         */
        CellType type = cell.getCellType();

        Object result = null;

        switch (type) {
            case STRING: {
                result = cell.getStringCellValue();//获取string类型数据
                break;
            }
            case NUMERIC: {
                /**
                 * 判断
                 */
                if (DateUtil.isCellDateFormatted(cell)) {  //日期格式
                    result = cell.getDateCellValue();
                } else {
                    //double类型
                    result = cell.getNumericCellValue(); //数字类型
                }
                break;
            }
            case BOOLEAN: {
                result = cell.getBooleanCellValue();//获取boolean类型数据
                break;
            }
            default: {
                break;
            }
        }

        return result;
    }
}
