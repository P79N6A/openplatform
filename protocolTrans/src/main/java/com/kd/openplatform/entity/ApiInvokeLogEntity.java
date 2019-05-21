package com.kd.openplatform.entity;



import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.util.Date;


public class ApiInvokeLogEntity implements java.io.Serializable {
    /**
     * id
     */
    private String id;
 /**
     * 场景id
     */
    private String sceneId;
    /**
     * 场景名称
     */
    private String sceneName;
    /**
     * 接口id
     */
    private String apiId;
    /**
     * 接口名称
     */
    private String apiName;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 调用时间
     */
    private Date invokeTime;
    /**
     * 调用IP
     */
    private String invokeIp;
    /**
     * 请求方法类型
     */
    private String methodType;
    /**
     * 调用地址
     */
    private String invokeUrl;
    /**
     * 响应时长
     */
    private Double responseTimeLength;
    /**
     * 响应流量
     */
    private Double responseFlowSize;
    /**
     * 请求流量
     */
    private Double requestFlowSize;
    /**
     * 请求头
     */
    private String requestHeader;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 响应参数
     */
    private String returnParam;
    /**
     * 响应头
     */
    private String returnHeader;
    /**
     * 接口调用结果
     */
    private Integer invokeOpenplatFormResult;
    /**
     * 接口调用结果
     */
    private Integer invokeServiceProviderResult;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

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

    public String getApiId() {
        return this.apiId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口id
     */
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口名称
     */

    public String getApiName() {
        return this.apiName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口名称
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  调用时间
     */

    public Date getInvokeTime() {
        return this.invokeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  调用时间
     */
    public void setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  调用IP
     */

    public String getInvokeIp() {
        return this.invokeIp;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  调用IP
     */
    public void setInvokeIp(String invokeIp) {
        this.invokeIp = invokeIp;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求方法类型
     */

    public String getMethodType() {
        return this.methodType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求方法类型
     */
    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  调用地址
     */

    public String getInvokeUrl() {
        return this.invokeUrl;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  调用地址
     */
    public void setInvokeUrl(String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  响应时长
     */

    public Double getResponseTimeLength() {
        return this.responseTimeLength;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  响应时长
     */
    public void setResponseTimeLength(Double responseTimeLength) {
        this.responseTimeLength = responseTimeLength;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  响应流量
     */

    public Double getResponseFlowSize() {
        return this.responseFlowSize;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  响应流量
     */
    public void setResponseFlowSize(Double responseFlowSize) {
        this.responseFlowSize = responseFlowSize;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  请求流量
     */
    public Double getRequestFlowSize() {
        return this.requestFlowSize;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  请求流量
     */
    public void setRequestFlowSize(Double requestFlowSize) {
        this.requestFlowSize = requestFlowSize;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求头
     */

    public String getRequestHeader() {
        return this.requestHeader;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求头
     */
    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  请求参数
     */

    public String getRequestParam() {
        return this.requestParam;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  请求参数
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  响应参数
     */

    public String getReturnParam() {
        return this.returnParam;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  响应参数
     */
    public void setReturnParam(String returnParam) {
        this.returnParam = returnParam;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  响应头
     */

    public String getReturnHeader() {
        return this.returnHeader;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  响应头
     */
    public void setReturnHeader(String returnHeader) {
        this.returnHeader = returnHeader;
    }

    public Integer getInvokeOpenplatFormResult() {
        return invokeOpenplatFormResult;
    }

    public void setInvokeOpenplatFormResult(Integer invokeOpenplatFormResult) {
        this.invokeOpenplatFormResult = invokeOpenplatFormResult;
    }

    public Integer getInvokeServiceProviderResult() {
        return invokeServiceProviderResult;
    }

    public void setInvokeServiceProviderResult(Integer invokeServiceProviderResult) {
        this.invokeServiceProviderResult = invokeServiceProviderResult;
    }
}
