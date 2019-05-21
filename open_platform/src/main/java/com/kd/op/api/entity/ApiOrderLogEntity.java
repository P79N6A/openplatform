package com.kd.op.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "api_order_log", catalog = "")
public class ApiOrderLogEntity {
    private long id;
    private String appId;
    private String appName;
    private String apiId;
    private String apiName;
    private Integer orderType;
    private Timestamp orderTime;
    private Long orderNumber;
    private Timestamp orderEndTime;
    private Integer orderResult;
    private String orderIp;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "app_name")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Basic
    @Column(name = "api_id")
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Basic
    @Column(name = "api_name")
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    @Basic
    @Column(name = "order_type")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "order_time")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Basic
    @Column(name = "order_number")
    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "order_end_time")
    public Timestamp getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Timestamp orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    @Basic
    @Column(name = "order_result")
    public Integer getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(Integer orderResult) {
        this.orderResult = orderResult;
    }

    @Basic
    @Column(name = "order_ip")
    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiOrderLogEntity that = (ApiOrderLogEntity) o;
        return id == that.id &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(appName, that.appName) &&
                Objects.equals(apiId, that.apiId) &&
                Objects.equals(apiName, that.apiName) &&
                Objects.equals(orderType, that.orderType) &&
                Objects.equals(orderTime, that.orderTime) &&
                Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(orderEndTime, that.orderEndTime) &&
                Objects.equals(orderResult, that.orderResult) &&
                Objects.equals(orderIp, that.orderIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appId, appName, apiId, apiName, orderType, orderTime, orderNumber, orderEndTime, orderResult, orderIp);
    }
}
