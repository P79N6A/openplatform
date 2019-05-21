package com.kd.op.api.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.SequenceGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 接口调用日志
 * @date 2018-10-03 12:24:57
 */
@Entity
@Table(name = "api_invoke_log", schema = "")
@SuppressWarnings("serial")
public class ApiInvokeLogEntity implements java.io.Serializable {
    /**
     * id
     */
    private java.lang.String id;
    /**
     * 场景id
     */
    @Excel(name = "场景id", width = 15)
    private java.lang.String sceneId;
    /**
     * 场景名称
     */
    @Excel(name = "场景名称", width = 15)
    private java.lang.String sceneName;
    /**
     * 接口id
     */
    @Excel(name = "接口id", width = 15)
    private java.lang.String apiId;
    /**
     * 接口名称
     */
    @Excel(name = "接口名称", width = 15)
    private java.lang.String apiName;
    /**
     * 应用id
     */
    @Excel(name = "接口id", width = 15)
    private java.lang.String appId;
    /**
     * 应用名称
     */
    @Excel(name = "接口名称", width = 15)
    private java.lang.String appName;
    /**
     * 调用时间
     */
    @Excel(name = "调用时间", width = 15, format = "yyyy-MM-dd")
    private java.util.Date invokeTime;
    /**
     * 调用IP
     */
    @Excel(name = "调用IP", width = 15)
    private java.lang.String invokeIp;
    /**
     * 请求方法类型
     */
    @Excel(name = "请求方法类型", width = 15)
    private java.lang.String methodType;
    /**
     * 调用地址
     */
    @Excel(name = "调用地址", width = 15)
    private java.lang.String invokeUrl;
    /**
     * 响应时长
     */
    @Excel(name = "响应时长", width = 15)
    private java.lang.Double responseTimeLength;
    /**
     * 响应流量
     */
    @Excel(name = "响应流量", width = 15)
    private java.lang.Double responseFlowSize;
    /**
     * 请求流量
     */
    @Excel(name = "请求流量", width = 15)
    private java.lang.Double requestFlowSize;
    /**
     * 请求头
     */
    @Excel(name = "请求头", width = 15)
    private java.lang.String requestHeader;
    /**
     * 请求参数
     */
    @Excel(name = "请求参数", width = 15)
    private java.lang.String requestParam;
    /**
     * 响应参数
     */
    @Excel(name = "响应参数", width = 15)
    private java.lang.String returnParam;
    /**
     * 响应头
     */
    @Excel(name = "响应头", width = 15)
    private java.lang.String returnHeader;
    /**
     * 开放平台调用结果
     */
    @Excel(name = "接口调用结果", width = 15, dicCode = "invoRes")
    private java.lang.Integer invokeOpenplatformResult;
    /**
     * 服务调用结果
     */
    @Excel(name = "接口调用结果", width = 15, dicCode = "invoRes")
    private java.lang.Integer invokeServiceproviderResult;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "ID", nullable = false, length = 32)
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "SCENE_ID", nullable = true, length = 32)
    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    @Column(name = "SCENE_NAME", nullable = true, length = 200)
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口id
     */

    @Column(name = "API_ID", nullable = true, length = 32)
    public java.lang.String getApiId() {
        return this.apiId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口id
     */
    public void setApiId(java.lang.String apiId) {
        this.apiId = apiId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口名称
     */

    @Column(name = "API_NAME", nullable = true, length = 255)
    public java.lang.String getApiName() {
        return this.apiName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口名称
     */
    public void setApiName(java.lang.String apiName) {
        this.apiName = apiName;
    }

    @Column(name = "APP_ID", nullable = true, length = 32)
    public java.lang.String getAppId() {
        return appId;
    }

    public void setAppId(java.lang.String appId) {
        this.appId = appId;
    }

    @Column(name = "APP_NAME", nullable = true, length = 255)
    public java.lang.String getAppName() {
        return appName;
    }

    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  调用时间
     */

    @Column(name = "INVOKE_TIME", nullable = true)
    public java.util.Date getInvokeTime() {
        return this.invokeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  调用时间
     */
    public void setInvokeTime(java.util.Date invokeTime) {
        this.invokeTime = invokeTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  调用IP
     */

    @Column(name = "INVOKE_IP", nullable = true, length = 255)
    public java.lang.String getInvokeIp() {
        return this.invokeIp;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  调用IP
     */
    public void setInvokeIp(java.lang.String invokeIp) {
        this.invokeIp = invokeIp;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求方法类型
     */

    @Column(name = "METHOD_TYPE", nullable = true, length = 255)
    public java.lang.String getMethodType() {
        return this.methodType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求方法类型
     */
    public void setMethodType(java.lang.String methodType) {
        this.methodType = methodType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  调用地址
     */

    @Column(name = "INVOKE_URL", nullable = true, length = 255)
    public java.lang.String getInvokeUrl() {
        return this.invokeUrl;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  调用地址
     */
    public void setInvokeUrl(java.lang.String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  响应时长
     */

    @Column(name = "RESPONSE_TIME_LENGTH", nullable = true, length = 22)
    public java.lang.Double getResponseTimeLength() {
        return this.responseTimeLength;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  响应时长
     */
    public void setResponseTimeLength(java.lang.Double responseTimeLength) {
        this.responseTimeLength = responseTimeLength;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  响应流量
     */

    @Column(name = "RESPONSE_FLOW_SIZE", nullable = true, length = 22)
    public java.lang.Double getResponseFlowSize() {
        return this.responseFlowSize;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  响应流量
     */
    public void setResponseFlowSize(java.lang.Double responseFlowSize) {
        this.responseFlowSize = responseFlowSize;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  请求流量
     */

    @Column(name = "REQUEST_FLOW_SIZE", nullable = true, length = 22)
    public java.lang.Double getRequestFlowSize() {
        return this.requestFlowSize;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  请求流量
     */
    public void setRequestFlowSize(java.lang.Double requestFlowSize) {
        this.requestFlowSize = requestFlowSize;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求头
     */

    @Column(name = "REQUEST_HEADER", nullable = true, length = 255)
    public java.lang.String getRequestHeader() {
        return this.requestHeader;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求头
     */
    public void setRequestHeader(java.lang.String requestHeader) {
        this.requestHeader = requestHeader;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求参数
     */

    @Column(name = "REQUEST_PARAM", nullable = true, length = 255)
    public java.lang.String getRequestParam() {
        return this.requestParam;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求参数
     */
    public void setRequestParam(java.lang.String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  响应参数
     */

    @Column(name = "RETURN_PARAM", nullable = true, length = 255)
    public java.lang.String getReturnParam() {
        return this.returnParam;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  响应参数
     */
    public void setReturnParam(java.lang.String returnParam) {
        this.returnParam = returnParam;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  响应头
     */

    @Column(name = "RETURN_HEADER", nullable = true, length = 255)
    public java.lang.String getReturnHeader() {
        return this.returnHeader;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  响应头
     */
    public void setReturnHeader(java.lang.String returnHeader) {
        this.returnHeader = returnHeader;
    }

    @Column(name = "INVOKE_OPENPLATFORM_RESULT", nullable = true, length = 5)
    public Integer getInvokeOpenplatformResult() {
        return invokeOpenplatformResult;
    }

    public void setInvokeOpenplatformResult(Integer invokeOpenplatformResult) {
        this.invokeOpenplatformResult = invokeOpenplatformResult;
    }

    @Column(name = "INVOKE_SERVICEPROVIDER_RESULT", nullable = true, length = 5)
    public Integer getInvokeServiceproviderResult() {
        return invokeServiceproviderResult;
    }

    public void setInvokeServiceproviderResult(Integer invokeServiceproviderResult) {
        this.invokeServiceproviderResult = invokeServiceproviderResult;
    }
}
