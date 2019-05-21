package com.kd.op.api.entity;

import java.util.List;

/**
 * @Auther:张健云
 * @Description：统计分析对象 用于echarts图形显示
 * @DATE：2019/1/14 9:50
 */
public class MonitorEntity {
    //横坐标
    private List<String> xAxisData;
    //纵坐标
    private List<String> yAxisData;
    //对应的值
    private List<Integer> seriesData;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
    //场景id
    private String sceneId;
    //能力id
    private String apiId;
    //能力中心id
    private String apiGroupId;
    //图表类型 统计count 排名 rank
    private String chartType;
    //统计类型 订购order 调用 invoke
    private String countType;
    //数据类型 能力数据 api 场景数据 scene 应用数据 app
    private String dataType;

    public List<String> getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(List<String> xAxisData) {
        this.xAxisData = xAxisData;
    }

    public List<Integer> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<Integer> seriesData) {
        this.seriesData = seriesData;
    }

    public List<String> getyAxisData() {
        return yAxisData;
    }

    public void setyAxisData(List<String> yAxisData) {
        this.yAxisData = yAxisData;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(String apiGroupId) {
        this.apiGroupId = apiGroupId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
