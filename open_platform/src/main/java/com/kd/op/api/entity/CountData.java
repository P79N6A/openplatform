package com.kd.op.api.entity;

import java.math.BigDecimal;

public class CountData {

    //能力总数
    private Long apiCount;
    //订阅总数
    private Long apiOrderCount;
    //应用总数
    private Long appCount;
    //服务提供总流量
    private Double apiProductFlow;
    //服务提供总次数
    private Long apiProductCount;
    //交易总金额
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Long getApiCount() {
        return apiCount;
    }

    public void setApiCount(Long apiCount) {
        this.apiCount = apiCount;
    }

    public Long getApiOrderCount() {
        return apiOrderCount;
    }

    public void setApiOrderCount(Long apiOrderCount) {
        this.apiOrderCount = apiOrderCount;
    }

    public Long getAppCount() {
        return appCount;
    }

    public void setAppCount(Long appCount) {
        this.appCount = appCount;
    }

    public Double getApiProductFlow() {
        return apiProductFlow;
    }

    public void setApiProductFlow(Double apiProductFlow) {
        this.apiProductFlow = apiProductFlow;
    }

    public Long getApiProductCount() {
        return apiProductCount;
    }

    public void setApiProductCount(Long apiProductCount) {
        this.apiProductCount = apiProductCount;
    }
}
