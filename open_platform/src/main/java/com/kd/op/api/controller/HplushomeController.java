package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.CountData;
import com.kd.op.api.service.HplushomeService;
import com.kd.op.common.CustomConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.pkcs11.wrapper.Constants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/hplushome")
public class HplushomeController {

    @Autowired
    private HplushomeService hplushomeService;

    /**
     * @return com.kd.op.api.entity.CountData
     * @Author zyz
     * @Description 查询首页展示统计数据
     * @Date 2019/3/1
     * @Param []
     */
    @RequestMapping(params = "getCountData")
    @ResponseBody
    public CountData getCountData() {
        Long apiCount = hplushomeService.getApiCountData();
        Long apiOrderCount = hplushomeService.getApiOrderCountData();
        Long apiProductCount = hplushomeService.getApiProductCountData();
        Double apiProductFlow = hplushomeService.getApiProductFlowData();
        Long appCount = hplushomeService.getAppCountData();
        BigDecimal money = hplushomeService.getMoneyData();
        CountData countData = new CountData();
        countData.setApiCount(apiCount);
        countData.setApiProductCount(apiProductCount);
        countData.setApiProductFlow(apiProductFlow);
        DecimalFormat df1 = new DecimalFormat("0");
        String moneyStr = df1.format(money);
        countData.setMoney(moneyStr);
        countData.setApiOrderCount(apiOrderCount);
        countData.setAppCount(appCount);
        return countData;
    }
    /*
     * @Author zyz
     * @Description 首页调用流量、调用次数、异常个数折线图数据查询
     * @Date  2019/3/5
     * @Param []
     * @return  com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(params = "getRunMonitorData")
    @ResponseBody
    public JSONObject getRunMonitorData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String startDate = sdf.format(date) + "-01-01";
        String endDate = sdf.format(date) + "-12-31";
        Map flowSizeMonitor = hplushomeService.getFlowByTime(startDate, endDate);
        Map countMonitor = hplushomeService.getCountData(startDate, endDate);
        Map exceptionMonitor = hplushomeService.getExceptionData(startDate, endDate);
        JSONObject result = new JSONObject();
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Double> flowData = new ArrayList<>();//一年内api调用流量总数统计
        List<Long> countData = new ArrayList<>();//一年内api调用数量总数统计
        List<Long> exceptionData = new ArrayList<>();//一年内api调用异常总数统计
        for (int i = 1; i < 13; i++) {
            String nowDate = sdf.format(date);
            if (i < 10) {
                nowDate += "-0" + i;
                xIndex.add(nowDate);
                flowData.add((flowSizeMonitor.get(nowDate) == null ? 0 :(double)  Math.round((Double) flowSizeMonitor.get(nowDate))));
                countData.add(countMonitor.get(nowDate) == null ? 0 : (Long) countMonitor.get(nowDate));
                exceptionData.add(exceptionMonitor.get(nowDate) == null ? 0 : (Long) exceptionMonitor.get(nowDate));
            } else {
                nowDate += "-" + i;
                xIndex.add(nowDate);
                flowData.add(flowSizeMonitor.get(nowDate) == null ? 0 : (Double) flowSizeMonitor.get(nowDate));
                countData.add(countMonitor.get(nowDate) == null ? 0 : (Long) countMonitor.get(nowDate));
                exceptionData.add(exceptionMonitor.get(nowDate) == null ? 0 : (Long) exceptionMonitor.get(nowDate));
            }
        }
        result.put("xIndex", xIndex);
        result.put("flowData", flowData);
        result.put("countData", countData);
        result.put("exceptionData", exceptionData);
        return result;
    }
    /*
     * @Author zyz
     * @Description 首页服务数量、订阅数量折线图数据查询
     * @Date  2019/3/5
     * @Param []
     * @return  com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(params = "getOrderMonitorData")
    @ResponseBody
    public JSONObject getOrderMonitorData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String startDate = sdf.format(date) + "-01-01";
        String endDate = sdf.format(date) + "-12-31";
        Map apiCountMap = hplushomeService.getApiCountByTime(startDate, endDate);
        Map orderCountMap = hplushomeService.getOrderCountBytime(startDate, endDate);
        JSONObject result = new JSONObject();
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Long> apiCount = new ArrayList<>();//一年内app接入总数统计
        List<Long> orderCount = new ArrayList<>();//一年内订单数量
        for (int i = 1; i < 13; i++) {
            String nowDate = sdf.format(date);
            if (i < 10) {
                nowDate += "-0" + i;
                xIndex.add(nowDate);
                apiCount.add(apiCountMap.get(nowDate) == null ? 0 :(Long) apiCountMap.get(nowDate));
                orderCount.add(orderCountMap.get(nowDate) == null ? 0 : (Long) orderCountMap.get(nowDate));
            } else {
                nowDate += "-" + i;
                xIndex.add(nowDate);
                apiCount.add(apiCountMap.get(nowDate) == null ? 0 : (Long) apiCountMap.get(nowDate));
                orderCount.add(orderCountMap.get(nowDate) == null ? 0 : (Long) orderCountMap.get(nowDate));
            }
        }
        result.put("xIndex", xIndex);
        result.put("orderCount", orderCount);
        result.put("apiCount", apiCount);
        return result;
    }

    @RequestMapping(params = "getUserMonitorData")
    @ResponseBody
    public JSONObject getUserMonitorData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String startDate = sdf.format(date) + "-01-01";
        String endDate = sdf.format(date) + "-12-31";
        Map isvCountMap = hplushomeService.getUserCountByTimeAndRoleCode(startDate, endDate,"isv");
        Map ispCountMap = hplushomeService.getUserCountByTimeAndRoleCode(startDate, endDate,"isp");
        JSONObject result = new JSONObject();
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Long> isvCount = new ArrayList<>();//一年内isv接入数量统计
        List<Long> ispCount = new ArrayList<>();//一年内isp接入数量统计
        for (int i = 1; i < 13; i++) {
            String nowDate = sdf.format(date);
            if (i < 10) {
                nowDate += "-0" + i;
                xIndex.add(nowDate);
                isvCount.add(isvCountMap.get(nowDate) == null ? 0 :(Long) isvCountMap.get(nowDate));
                ispCount.add(ispCountMap.get(nowDate) == null ? 0 : (Long) ispCountMap.get(nowDate));
            } else {
                nowDate += "-" + i;
                xIndex.add(nowDate);
                isvCount.add(isvCountMap.get(nowDate) == null ? 0 : (Long) isvCountMap.get(nowDate));
                ispCount.add(ispCountMap.get(nowDate) == null ? 0 : (Long) ispCountMap.get(nowDate));
            }
        }
        result.put("xIndex", xIndex);
        result.put("isvCount", isvCount);
        result.put("ispCount", ispCount);
        return result;
    }
    /**
     * @Author zyz
     * @Description 查询访问流量前五名数据
     * @Date  2019/3/6
     * @Param []
     * @return  com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(params = "getFlowRankData")
    @ResponseBody
    public JSONObject getFlowRankData() {
        JSONObject result = new JSONObject();
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Double> flowRankData = new ArrayList<>();//排名前五的流量
        Map<String,Double> flowRankMap= hplushomeService.getApiNameRankByFlow();
        Set<Map.Entry<String, Double>> flowRankSet = flowRankMap.entrySet();
        for (Map.Entry<String, Double> flowRankEntry : flowRankSet) {
            xIndex.add(flowRankEntry.getKey());
            flowRankData.add(flowRankEntry.getValue());
        }
        result.put("flowRankData",flowRankData);
        result.put("xIndex",xIndex);
        return result;
    }
    /**
     * @Author zyz
     * @Description 查询访问次数前五名数据
     * @Date  2019/3/6
     * @Param []
     * @return  com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(params = "getCountRankData")
    @ResponseBody
    public JSONObject getCountRankData() {
        JSONObject result = new JSONObject();
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Long> countRankData = new ArrayList<>();//排名前五的调用次数
        Map<String,Long> countRankMap= hplushomeService.getApiNameRankByCount();
        Set<Map.Entry<String, Long>> countRankSet = countRankMap.entrySet();
        for (Map.Entry<String, Long> countRankEntry : countRankSet) {
            xIndex.add(countRankEntry.getKey());
            countRankData.add(countRankEntry.getValue());
        }
        result.put("countRankData",countRankData);
        result.put("xIndex",xIndex);
        return result;
    }
    /**
     * @Author zyz
     * @Description 查询正常、停止、作废三种能力数量
     * @Date  2019/3/6
     * @Param []
     * @return  com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(params = "getApiAppStatusData")
    @ResponseBody
    public JSONObject getApiAppStatusData(String type) {
        JSONObject result = new JSONObject();
        if (StringUtils.isNotEmpty(type) && type.equals("api")) {
            Long apiNormalCount = hplushomeService.getApiAppCountByStatus(type, "normal", 1, 1, 1);
            Long apiStopCount = hplushomeService.getApiAppCountByStatus(type, "stop", -1, 0, 0, 0);
            Long apiCancelCount = hplushomeService.getApiAppCountByStatus(type, "cancel", -1);
            result.put("normalCount", apiNormalCount);
            result.put("stopCount", apiStopCount);
            result.put("cancelCount", apiCancelCount);
            return result;
        } else {
            Long appNormalCount = hplushomeService.getApiAppCountByStatus(type, "normal", 1);
            Long appStopCount = hplushomeService.getApiAppCountByStatus(type, "stop",  -0);
            Long appCancelCount = hplushomeService.getApiAppCountByStatus(type, "cancel", -1);
            result.put("normalCount", appNormalCount);
            result.put("stopCount", appStopCount);
            result.put("cancelCount", appCancelCount);
            return result;
        }
    }
}
