package com.kd.op.api.service;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface HplushomeService {
    Long getApiCountData();
    Long getApiOrderCountData();
    Long getAppCountData();
    Double getApiProductFlowData();
    Long getApiProductCountData();
    BigDecimal getMoneyData();
    Map getFlowByTime(String startDate,String endDate);
    Map getCountData(String startDate,String endDate);
    Map getExceptionData(String startDate,String endDate);
    Map getApiCountByTime(String startDate,String endDate);
    Map getOrderCountBytime(String startDate,String endDate);
    Map getUserCountByTimeAndRoleCode(String startDate,String endDate,String roleCode);
    Map<String,Double> getApiNameRankByFlow();
    Map<String,Long> getApiNameRankByCount();
    Long getApiAppCountByStatus(String type,String stausType,Object ... status);
}
