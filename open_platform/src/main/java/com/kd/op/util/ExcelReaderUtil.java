package com.kd.op.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Auther:张健云
 * @Description：
 * @DATE：2018/12/19 15:48
 */
public class ExcelReaderUtil {
    private static final Logger logger = Logger.getLogger(ExcelReaderUtil.class);

    /**
     * 根据fileType不同读取excel文件
     * 将文件转换为excel对象
     *
     * @param
     * @param
     * @throws IOException
     */
    public static Workbook transFileToExcel(MultipartFile file) {
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        //读取excel文件
        InputStream is = null;
        //获取工作薄
        Workbook wb = null;
        try {
            is = file.getInputStream();
            if (fileType.equals("xls") || fileType.equals("xlsx")) {
                wb = new HSSFWorkbook(is);
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("执行失败：" + e.getMessage());
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                logger.error("error:",e);
            }
        }
        return wb;
    }

    /**
     * 读取sheet对象内容
     *
     * @param
     * @return
     */
    public static List<List<String>> readSheet(Sheet sheet) {
        List<List<String>> list = new ArrayList<>();
        //第一行为标题
        for (Row row : sheet) {
            List<String> listRow = new ArrayList<>();
            for (Cell cell : row) {
                //根据不同类型转化成字符串
                cell.setCellType(Cell.CELL_TYPE_STRING);
                listRow.add(cell.getStringCellValue());
            }
            list.add(listRow);
        }
        return list;
    }

/**
 * 创建Excel.xls
 *
 * @param lists 需要写入xls的数据
 * @param titles 列标题
 * @param name  文件名
 * @return
 * @throws IOException
 */
public static Workbook creatExcel(List<List<String>>lists,String[]titles,String name)throws IOException{
        //创建新的工作薄
        Workbook wb=new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet=wb.createSheet(name);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<titles.length;i++){
        sheet.setColumnWidth((short)i,(short)(35.7*150));
        }

        // 创建第一行
        Row row=sheet.createRow((short)0);

        // 创建两种单元格格式
        CellStyle cs=wb.createCellStyle();
        CellStyle cs2=wb.createCellStyle();

        // 创建两种字体
        Font f=wb.createFont();
        Font f2=wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short)10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short)10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<titles.length;i++){
        Cell cell=row.createCell(i);
        cell.setCellValue(titles[i]);
        cell.setCellStyle(cs);
        }
        if(lists==null||lists.size()==0){
        return wb;
        }
        //设置每行每列的值
        for(short i=1;i<=lists.size();i++){
        // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
        // 创建一行，在页sheet上
        Row row1=sheet.createRow((short)i);
        for(short j=0;j<titles.length;j++){
        // 在row行上创建一个方格
        Cell cell=row1.createCell(j);
        cell.setCellValue(lists.get(i-1).get(j));
        cell.setCellStyle(cs2);
        }
        }
        return wb;
        }

}
