package com.kd.op.api.entity;

import java.util.List;
import java.util.Map;

/**
 * @Auther:张健云
 * @Description：Excel实体类用于导出excel对象
 * @DATE：2018/12/18 9:27
 */
public class ExcelEntity {

    //文件名
    private String fileName;
    //sheet列表
    private List<SheetEntity> sheets;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SheetEntity> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetEntity> sheets) {
        this.sheets = sheets;
    }


    // sheet表单对象
    public class SheetEntity{

        public SheetEntity(String sheetName) {
            this.sheetName = sheetName;
        }

        private String sheetName;
        //表头
        private String[] titles;
        //内容
        private String [][] contents;

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public String[] getTitles() {
            return titles;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public String[][] getContents() {
            return contents;
        }

        public void setContents(String[][] contents) {
            this.contents = contents;
        }
    }
}

