package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api_app_charge", schema = "")
public class ApiAppChargeEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**服务场景ID*/
    @Excel(name="应用ID")
    private String appId;
    /**服务场景名称*/
    @Excel(name="应用名称")
    private String appName;
    /**计费模型ID*/
    @Excel(name="计费模型ID")
    private String chargeId;
    /**创建人登录名称*/
    @Excel(name="创建人登录名称")
    private String createBy;
    /**创建人名称*/
    @Excel(name="创建人名称")
    private String createName;
    /**创建时间*/
    @Excel(name="创建时间")
    private Date createTime;

    /**
     *方法: 取得String
     *@return: String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "id",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    @Column(name = "app_name")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    @Column(name = "charge_id")
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }


    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }


    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
