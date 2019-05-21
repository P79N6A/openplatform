package com.kd.op.util;

import com.kd.op.api.entity.ExcelEntity;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @Auther:张健云
 * @Description：导出excel工具类
 * @DATE：2018/12/18 9:15
 */
public class ExcelUtils {
    private static final Logger logger = Logger.getLogger(ExcelUtils.class);

    /**
     * @param excel 对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(ExcelEntity excel) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        //获去所有的sheet对象
        for (ExcelEntity.SheetEntity sheetEntity : excel.getSheets()) {
            // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetEntity.getSheetName());
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);

            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

            //声明列对象
            HSSFCell cell = null;

            //创建标题
            for (int i = 0; i < sheetEntity.getTitles().length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(sheetEntity.getTitles()[i]);
                cell.setCellStyle(style);
            }

            //创建内容
            if(sheetEntity.getContents()!=null && sheetEntity.getContents().length>0){
                for (int i = 0; i < sheetEntity.getContents().length; i++) {
                    row = sheet.createRow(i + 1);
                    for (int j = 0; j < sheetEntity.getContents()[i].length; j++) {
                        //将内容按顺序赋给对应的列对象
                        row.createCell(j).setCellValue(sheetEntity.getContents()[i][j]);
                    }
                }
            }
        }
        return wb;
    }

    /**
     * 导出表格
     * @param excel
     * @param response
     * @throws Exception
     */
    public static void exportExcel(ExcelEntity excel, HttpServletResponse response) throws Exception {
        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(excel);
        setResponseHeader(response,excel.getFileName());
        OutputStream op = response.getOutputStream();
        wb.write(op);
        op.flush();
        op.close();
    }
    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                logger.error("error:",e);
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            logger.error("error:",ex);
        }
    }
}
