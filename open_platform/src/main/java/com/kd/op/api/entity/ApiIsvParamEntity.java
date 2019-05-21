package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_isv_param", schema = "")
public class ApiIsvParamEntity {
    private String id;
    private String apiId;
    private String isvId;
    private String paramName;
    private String paramValue;
    private String appId;
    private Integer isVisible;


    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    @Column(name = "IS_VISIBLE", nullable = true, length = 2)
    public Integer getIsVisible() {
        return isVisible;
    }

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "API_ID", nullable = true, length = 32)
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Column(name = "ISV_ID", nullable = true, length = 32)
    public String getIsvId() {
        return isvId;
    }

    public void setIsvId(String isvId) {
        this.isvId = isvId;
    }

    @Column(name = "PARAM_NAME", nullable = true, length = 32)
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name = "PARAM_VALUE", nullable = true, length = 256)
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Column(name = "APP_ID", nullable = true, length = 32)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
