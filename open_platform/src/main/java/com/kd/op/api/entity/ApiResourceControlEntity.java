package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "api_resource_control", schema = "")

public class ApiResourceControlEntity implements Serializable {

    //主键
    private String id;
    //
    private String resourceParamName;
    //资源控制值
    private String resourceParamValue;
    //
    private String appId;
    //
    private String apiId;
    //
    private String createBy;
    //
    private Date createTime;
    //
    private String updateBy;
    //
    private Date updateTime;


    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "ID",nullable=false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "RESOURCE_PARAM_NAME",nullable=true)
    public String getResourceParamName() {
        return resourceParamName;
    }

    public void setResourceParamName(String resourceParamName) {
        this.resourceParamName = resourceParamName;
    }

    @Column(name = "RESOURCE_PARAM_VALUE",nullable=true)
    public String getResourceParamValue() {
        return resourceParamValue;
    }

    public void setResourceParamValue(String resourceParamValue) {
        this.resourceParamValue = resourceParamValue;
    }

    @Column(name = "APP_ID",nullable=true)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Column(name = "API_ID",nullable=true)
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Column(name = "CREATE_BY",nullable=true)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_TIME",nullable=true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_BY",nullable=true)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_TIME",nullable=true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
