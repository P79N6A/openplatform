package com.kd.openplatform.bean;

import java.util.ArrayList;
import java.util.List;

public class ApiParam {
    String id;
    String apiId;
    String paramName;
    String paramType;
    String parentId;
    String dataType;
    String paramEncrypt;
    String defaultValue;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    List<ApiParam> childern = new ArrayList<ApiParam>();

    public List<ApiParam> getChildern() {
        return childern;
    }

    public void setChildern(List<ApiParam> childern) {
        this.childern = childern;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParamEncrypt() {
        return paramEncrypt;
    }

    public void setParamEncrypt(String paramEncrypt) {
        this.paramEncrypt = paramEncrypt;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
