package cn.itcast.web.controller.cargo;


import cn.itcast.common.utils.DownloadUtil;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractProductVo;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/cargo/contract")
public class OutProductController extends BaseController {
    @Reference
    private ContractProductService contractProductService;

    @RequestMapping(value = "print")
    public String print() {
        return "cargo/print/contract-print";
    }

    /**
     * 不难，繁琐
     */
    @RequestMapping(value = "/printExcel")
    public void pringtExcel(String inputDate) throws Exception {
        //1.查询货物数据
        List<ContractProductVo> cpvs = contractProductService.findByShipTime(companyId, inputDate);
        //2.构造excel报表
        //a.创建工作簿
        Workbook wb = new SXSSFWorkbook();
        //b.创建sheet
        Sheet sheet = wb.createSheet();
        //c.设置基本参数（宽度）
        sheet.setColumnWidth(1, 26 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 8 * 256);
        int rowIndex = 0;
        Row row = null;
        Cell cell = null;
        //d.构造大标题
        row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(36); //行高
        cell = row.createCell(1);
        cell.setCellValue(inputDate.replaceAll("-0", "-").replaceAll("-", "年") + "月份出货表");  //2012-11   -》 2012-8  --》 2012年8
        cell.setCellStyle(bigTitle(wb)); //样式
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));//合并单元行  int firstRow, int lastRow, int firstCol, int lastCol
        //e.构造小标题
        row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(26.25f); //行高
        String titles[] = new String[]{"", "客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        for (int i = 1; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(title(wb));
        }
        //f.构造数据行
        for (ContractProductVo cpv : cpvs) {
            for (int i = 0; i < 4000; i++) {
                row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(24f); //行高
                //客户
                cell = row.createCell(1);
                cell.setCellValue(cpv.getCustomName());
               // cell.setCellStyle(text(wb));
//			订单号
                cell = row.createCell(2);
                cell.setCellValue(cpv.getContractNo());
               // cell.setCellStyle(text(wb));
//			货号
                cell = row.createCell(3);
                cell.setCellValue(cpv.getProductNo());
               // cell.setCellStyle(text(wb));
//			数量
                cell = row.createCell(4);
                cell.setCellValue(cpv.getCnumber());
               // cell.setCellStyle(text(wb));
//			工厂
                cell = row.createCell(5);
                cell.setCellValue(cpv.getFactoryName());
               // cell.setCellStyle(text(wb));
//			工厂交期
                cell = row.createCell(6);
                cell.setCellValue(cpv.getDeliveryPeriod());
               // cell.setCellStyle(text(wb));
//			船期
                cell = row.createCell(7);
                cell.setCellValue(cpv.getShipTime());
               // cell.setCellStyle(text(wb));
//			贸易条款
                cell = row.createCell(8);
                cell.setCellValue(cpv.getTradeTerms());
               // cell.setCellStyle(text(wb));
            }
        }
        //3.下载excel
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        new DownloadUtil().download(bos, response, "出货表.xlsx");
    }

    /**
     * 模板打印，完成报表操作
     * 1.根据模板创建工作簿
     * 2.读取sheet
     * 3.获取行对象
     * 4.获取单元格对象
     */
//	@RequestMapping("/printExcel")
//	public void printExcel(String inputDate) throws IOException {
//		//1.查询货物数据
//		List<ContractProductVo> cpvs = contractProductService.findByShipTime(companyId,inputDate);
//		//2.构造excel报表
//		//a.根据模板创建工作簿
//		//获取到模板路径
//		String path = session.getServletContext().getRealPath("/")+"/make/xlsprint/tOUTPRODUCT.xlsx";
//		Workbook wb = new XSSFWorkbook(path);
//
//		//b.读取sheet
//		Sheet sheet = wb.getSheetAt(0);
//		//c.设置基本属性
//		int rowIndex = 0;
//		Row row = null;
//		Cell cell = null;
//		//d.设置大标题
//		row = sheet.getRow(rowIndex++);
//		cell = row.getCell(1);
//		cell.setCellValue(inputDate.replaceAll("-0","-").replaceAll("-","年")+"月份出货表");  //2012-11   -》 2012-8  --》 2012年8);
//		//e.设置小标题
//		rowIndex++;
//
//		//提取样式
//		row = sheet.getRow(rowIndex);
//		CellStyle css [] = new CellStyle[9];
//		for(int i=1;i<css.length;i++ ){
//			cell = row.getCell(i);
//			css [i] = cell.getCellStyle();
//		}
//		//f.设置数据行
//		for(ContractProductVo cpv : cpvs) {
//			for(int i=0;i<3000;i++) {
//				row = sheet.createRow(rowIndex ++);
//				//			//客户
//				cell = row.createCell(1);
//				cell.setCellValue(cpv.getCustomName());
//				cell.setCellStyle(css[1]);
//	//			订单号
//				cell = row.createCell(2);
//				cell.setCellValue(cpv.getContractNo());
//				cell.setCellStyle(css[2]);
//	//			货号
//				cell = row.createCell(3);
//				cell.setCellValue(cpv.getProductNo());
//				cell.setCellStyle(css[3]);
//	//			数量
//				cell = row.createCell(4);
//				cell.setCellValue(cpv.getCnumber());
//				cell.setCellStyle(css[4]);
//	//			工厂
//				cell = row.createCell(5);
//				cell.setCellValue(cpv.getFactoryName());
//				cell.setCellStyle(css[5]);
//	//			工厂交期
//				cell = row.createCell(6);
//				cell.setCellValue(cpv.getDeliveryPeriod());
//				cell.setCellStyle(css[6]);
//	//			船期
//				cell = row.createCell(7);
//				cell.setCellValue(cpv.getShipTime());
//				cell.setCellStyle(css[7]);
//	//			贸易条款
//				cell = row.createCell(8);
//				cell.setCellValue(cpv.getTradeTerms());
//				cell.setCellStyle(css[8]);
//			}
//		}
//
//
//		//3.下载excel
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		wb.write(bos);
//		new DownloadUtil().download(bos,response,"出货表.xlsx");
//	}

    //大标题的样式
    public CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);                //横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线

        return style;
    }
}
