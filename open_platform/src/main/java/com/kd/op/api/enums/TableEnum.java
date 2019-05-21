package com.kd.op.api.enums;

import com.kd.op.common.CustomConstant;

/**
 * @Auther:张健云
 * @Description：表名类名对应关系枚举类
 * @DATE：2018/12/18 14:07
 */
public enum TableEnum{

    API_INFO("",CustomConstant.API_INFO_E);

    private String tableName;

    private String entityName;

    TableEnum(String tableName, String entityName) {
        this.tableName = tableName;
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
