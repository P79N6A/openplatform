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
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

/**
 * @Title: Entity
 * @Description: 接口信息表
 * @author onlineGenerator
 * @date 2018-10-10 16:46:50
 * @version V1.0
 *
 */

@Entity
@Table(name = "api_info", schema = "")
@SuppressWarnings("serial")
@ExcelTarget(value = "ApiInfoEntity")
public class ApiInfoEntity implements java.io.Serializable {
    /**
     * id
     */
    @Excel(name = "接口id", width = 15)
    private java.lang.String id;
    /**
     * 接口名称
     */
    @Excel(name = "接口名称", width = 15)
    private java.lang.String apiName;
    /**
     * 接口描述
     */
    @Excel(name = "接口描述", width = 15)
    private java.lang.String apiDesc;
    /**
     * 接口可用状态
     */
    @Excel(name = "接口状态", width = 15, dicCode = "apiSts")
    private java.lang.Integer apiStatus;//0：不可用，1：可用
    /**
     * 接口发布状态
     */
    @Excel(name = "接口发布状态", width = 15, dicCode = "pubSts")
    private java.lang.Integer apiPublishStatus;//0：未发布，1：已发布,-1:作废
    /**
     * 接口可见状态
     */
    @Excel(name = "接口可见状态", width = 15, dicCode = "visiSts")
    private java.lang.Integer apiVisibleStatus;//0：不可见，1：可见
    /**
     * 接口运行状态
     */
    @Excel(name = "接口运行状态", width = 15, dicCode = "runSts")
    private java.lang.Integer apiRunStatus;
    /**
     * 接口审核状态
     */
    @Excel(name = "接口审核状态", width = 15, dicCode = "auditSts")
    private java.lang.Integer apiAuditStatus;//0：暂存，1:待审核。2：审核通过，3：审核不通过
    /**
     * 接口审核意见
     */
    @Excel(name = "接口审核意见", width = 15)
    private java.lang.String apiAuditMsg;
    /**
     * 发布方式
     */
    @Excel(name = "发布方式", width = 15, dicCode = "pubMode")
    private java.lang.String pubMode;
    /**
     * 接口类全名
     */
    @Excel(name = "类名", width = 15)
    private java.lang.String apiClassName;
    /**
     * 接口类方法名
     */
    @Excel(name = "方法名", width = 15)
    private java.lang.String apiMethodName;
    /**
     * http请求地址
     */
    @Excel(name = "http请求地址", width = 15)
    private java.lang.String reqAddrHttp;
    /**
     * hsf请求地址
     */
    @Excel(name = "hsf请求地址", width = 15)
    private java.lang.String reqAddrHsf;
    /**
     * 接口分组Id
     */
    @Excel(name = "接口分组Id", width = 15)
    private java.lang.String groupId;
    /**
     * 接口分组名称
     */
    @Excel(name = "接口分组名称", width = 15)
    private java.lang.String groupName;
    /**
     * 实例数据
     */
    @Excel(name = "实例数据", width = 15)
    private java.lang.String examData;
    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称", width = 15)
    private java.lang.String createName;
    /**
     * 创建人登录名称
     */
    @Excel(name = "创建人登录名称", width = 15)
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 15)
    private java.util.Date createDate;
    /**
     * 更新人名称
     */
    @Excel(name = "更新人名称", width = 15)
    private java.lang.String updateName;
    /**
     * 更新人登录名称
     */
    @Excel(name = "更新人登录名称", width = 15)
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @Excel(name = "更新日期", width = 15)
    private java.util.Date updateDate;
    /**
     * hsf分组
     */
    @Excel(name = "hsf分组", width = 15)
    private java.lang.String hsfGroup;
    /**
     * 版本号
     */
    @Excel(name = "版本号", width = 15)
    private java.lang.String version;

    @Excel(name = "是否进行订单控制", width = 15)
    private Integer resourceCtrl;//是否进行订单控制

    @Excel(name = "能力调用类型", width = 15)
    private Integer apiInvokeType;//能力调用类型，1：ISP被动调用，2：ISP主动推送

    /**
     * 创建者用户id
     */
    @Excel(name = "创建者用户id", width = 15)
    private java.lang.String userId;

    /**
     * 审核员名称
     */
    @Excel(name = "审核员名称", width = 15)
    private java.lang.String auditBy;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口名称
     */

    @Column(name = "API_NAME", nullable = true, length = 100)
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口描述
     */

    @Column(name = "API_DESC", nullable = true, length = 200)
    public java.lang.String getApiDesc() {
        return this.apiDesc;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口描述
     */
    public void setApiDesc(java.lang.String apiDesc) {
        this.apiDesc = apiDesc;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  接口状态
     */

    @Column(name = "API_STATUS", nullable = true, length = 10)
    public java.lang.Integer getApiStatus() {
        return this.apiStatus;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  接口状态
     */
    public void setApiStatus(java.lang.Integer apiStatus) {
        this.apiStatus = apiStatus;
    }


    @Column(name = "API_PUBLISH_STATUS", nullable = true, length = 10)
    public java.lang.Integer getApiPublishStatus() {
        return apiPublishStatus;
    }

    public void setApiPublishStatus(java.lang.Integer apiPublishStatus) {
        this.apiPublishStatus = apiPublishStatus;
    }

    @Column(name = "API_VISIBLE_STATUS", nullable = true, length = 10)
    public java.lang.Integer getApiVisibleStatus() {
        return apiVisibleStatus;
    }

    public void setApiVisibleStatus(java.lang.Integer apiVisibleStatus) {
        this.apiVisibleStatus = apiVisibleStatus;
    }

    @Column(name = "API_RUN_STATUS", nullable = true, length = 10)
    public Integer getApiRunStatus() {
        return apiRunStatus;
    }

    public void setApiRunStatus(Integer apiRunStatus) {
        this.apiRunStatus = apiRunStatus;
    }

    @Column(name = "API_AUDIT_STATUS", nullable = true, length = 10)
    public java.lang.Integer getApiAuditStatus() {
        return apiAuditStatus;
    }

    public void setApiAuditStatus(java.lang.Integer apiAuditStatus) {
        this.apiAuditStatus = apiAuditStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  http请求地址
     */

    @Column(name = "REQ_ADDR_HTTP", nullable = true, length = 256)
    public java.lang.String getReqAddrHttp() {
        return this.reqAddrHttp;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  http请求地址
     */
    public void setReqAddrHttp(java.lang.String reqAddrHttp) {
        this.reqAddrHttp = reqAddrHttp;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  hsf请求地址
     */

    @Column(name = "REQ_ADDR_HSF", nullable = true, length = 256)
    public java.lang.String getReqAddrHsf() {
        return this.reqAddrHsf;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  hsf请求地址
     */
    public void setReqAddrHsf(java.lang.String reqAddrHsf) {
        this.reqAddrHsf = reqAddrHsf;
    }


    @Column(name = "API_AUDIT_MSG", nullable = true, length = 256)
    public java.lang.String getApiAuditMsg() {
        return apiAuditMsg;
    }

    public void setApiAuditMsg(java.lang.String apiAuditMsg) {
        this.apiAuditMsg = apiAuditMsg;
    }

    @Column(name = "PUB_MODE", nullable = true, length = 30)
    public java.lang.String getPubMode() {
        return pubMode;
    }

    public void setPubMode(java.lang.String pubMode) {
        this.pubMode = pubMode;
    }

    @Column(name = "API_CLASS_NAME", nullable = true, length = 256)
    public java.lang.String getApiClassName() {
        return apiClassName;
    }


    public void setApiClassName(java.lang.String apiClassName) {
        this.apiClassName = apiClassName;
    }

    @Column(name = "API_METHOD_NAME", nullable = true, length = 256)
    public java.lang.String getApiMethodName() {
        return apiMethodName;
    }

    public void setApiMethodName(java.lang.String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口分组Id
     */

    @Column(name = "GROUP_ID", nullable = true, length = 30)
    public java.lang.String getGroupId() {
        return this.groupId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口分组Id
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口分组名称
     */

    @Column(name = "GROUP_NAME", nullable = true, length = 50)
    public java.lang.String getGroupName() {
        return this.groupName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口分组名称
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  实例数据
     */

    @Column(name = "EXAM_DATA", nullable = true, length = 200)
    public java.lang.String getExamData() {
        return this.examData;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  实例数据
     */
    public void setExamData(java.lang.String examData) {
        this.examData = examData;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */

    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人名称
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人名称
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人登录名称
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  更新日期
     */

    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  更新日期
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "hsf_group", nullable = true, length = 50)
    public String getHsfGroup() {
        return hsfGroup;
    }

    public void setHsfGroup(String hsfGroup) {
        this.hsfGroup = hsfGroup;
    }

    @Column(name = "version", nullable = true, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "resource_ctrl", nullable = true, length = 2)
    public Integer getResourceCtrl() {
        return resourceCtrl;
    }

    public void setResourceCtrl(Integer resourceCtrl) {
        this.resourceCtrl = resourceCtrl;
    }

    @Column(name = "api_invoke_type", nullable = true, length = 6)
    public Integer getApiInvokeType() {
        return apiInvokeType;
    }

    public void setApiInvokeType(Integer apiInvokeType) {
        this.apiInvokeType = apiInvokeType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建者用户Id
     */

    @Column(name = "USER_ID", nullable = true, length = 30)
    public java.lang.String getUserId() {
        return this.userId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建者用户Id
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核员名称
     */

    @Column(name = "audit_by", nullable = true, length = 50)
    public java.lang.String getAuditBy() {
        return this.auditBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核员名称
     */
    public void setAuditBy(java.lang.String auditBy) {
        this.auditBy = auditBy;
    }
}
