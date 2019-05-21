package com.kd.op.api.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_app_keys", schema = "")
@SuppressWarnings("serial")
public class ApiAppKeysEntity {
    private String id;
    private String appId;
    private String appName;
    private String passKey;
    private String ivStr;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name ="ID",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="APP_ID",nullable=true,length=32)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Column(name ="APP_NAME",nullable=true,length=255)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(name ="PASS_KEY",nullable=true,length=255)
    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    @Column(name ="IV_STR",nullable=true,length=255)
    public String getIvStr() {
        return ivStr;
    }

    public void setIvStr(String ivStr) {
        this.ivStr = ivStr;
    }
}
