package com.kd.openplatform.access.entity;

import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

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
public class ApiInfoEntity implements java.io.Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 接口名称
     */
    //@Excel(name = "接口名称", width = 15)
    private String apiName;
    /**
     * 接口描述
     */
    //@Excel(name = "接口描述", width = 15)
    private String apiDesc;
    /**
     * 接口状态
     */
    //@Excel(name = "接口状态", width = 15, dicCode = "apiSts")
    private Integer apiStatus;
    /**
     * 接口发布状态
     */
    //@Excel(name = "接口发布状态", width = 15, dicCode = "pubSts")
    private Integer apiPublishStatus;
    /**
     * 接口可见状态
     */
    //@Excel(name = "接口可见状态", width = 15, dicCode = "visiSts")
    private Integer apiVisibleStatus;
    /**
     * 接口运行状态
     */
    //@Excel(name = "接口运行状态", width = 15, dicCode = "runSts")
    private Integer apiRunStatus;
    /**
     * 接口审核状态
     */
    //@Excel(name = "接口审核状态", width = 15, dicCode = "auditSts")
    private Integer apiAuditStatus;
    /**
     * 接口审核意见
     */
    //@Excel(name = "接口审核意见", width = 15)
    private String apiAuditMsg;
    /**
     * 发布方式
     */
    //@Excel(name = "发布方式", width = 15, dicCode = "pubMode")
    private String pubMode;
    /**
     * 接口类全名
     */
    //@Excel(name = "类名", width = 15)
    private String apiClassName;
    /**
     * 接口类方法名
     */
    //@Excel(name = "方法名", width = 15)
    private String apiMethodName;
    /**
     * http请求地址
     */
    //@Excel(name = "http请求地址", width = 15)
    private String reqAddrHttp;
    /**
     * hsf请求地址
     */
    //@Excel(name = "hsf请求地址", width = 15)
    private String reqAddrHsf;
    /**
     * 接口分组Id
     */
    //@Excel(name = "接口分组Id", width = 15)
    private String groupId;
    /**
     * 接口分组名称
     */
    //@Excel(name = "接口分组名称", width = 15)
    private String groupName;
    /**
     * 实例数据
     */
    //@Excel(name = "实例数据", width = 15)
    private String examData;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * hsf分组
     */
    private String hsfGroup;
    /**
     * 版本号
     */
    private String version;
    /**
     * 组织机构
     */
    //private String orgId;
    /**
     * 创建者名称
     */
    private String user_id;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口名称
     */

    @Column(name = "API_NAME", nullable = true, length = 100)
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口描述
     */

    @Column(name = "API_DESC", nullable = true, length = 200)
    public String getApiDesc() {
        return this.apiDesc;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口描述
     */
    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  接口状态
     */

    @Column(name = "API_STATUS", nullable = true, length = 10)
    public Integer getApiStatus() {
        return this.apiStatus;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  接口状态
     */
    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }


    @Column(name = "API_PUBLISH_STATUS", nullable = true, length = 10)
    public Integer getApiPublishStatus() {
        return apiPublishStatus;
    }

    public void setApiPublishStatus(Integer apiPublishStatus) {
        this.apiPublishStatus = apiPublishStatus;
    }

    @Column(name = "API_VISIBLE_STATUS", nullable = true, length = 10)
    public Integer getApiVisibleStatus() {
        return apiVisibleStatus;
    }

    public void setApiVisibleStatus(Integer apiVisibleStatus) {
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
    public Integer getApiAuditStatus() {
        return apiAuditStatus;
    }

    public void setApiAuditStatus(Integer apiAuditStatus) {
        this.apiAuditStatus = apiAuditStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  http请求地址
     */

    @Column(name = "REQ_ADDR_HTTP", nullable = true, length = 256)
    public String getReqAddrHttp() {
        return this.reqAddrHttp;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  http请求地址
     */
    public void setReqAddrHttp(String reqAddrHttp) {
        this.reqAddrHttp = reqAddrHttp;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  hsf请求地址
     */

    @Column(name = "REQ_ADDR_HSF", nullable = true, length = 256)
    public String getReqAddrHsf() {
        return this.reqAddrHsf;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  hsf请求地址
     */
    public void setReqAddrHsf(String reqAddrHsf) {
        this.reqAddrHsf = reqAddrHsf;
    }


    @Column(name = "API_AUDIT_MSG", nullable = true, length = 256)
    public String getApiAuditMsg() {
        return apiAuditMsg;
    }

    public void setApiAuditMsg(String apiAuditMsg) {
        this.apiAuditMsg = apiAuditMsg;
    }

    @Column(name = "PUB_MODE", nullable = true, length = 30)
    public String getPubMode() {
        return pubMode;
    }

    public void setPubMode(String pubMode) {
        this.pubMode = pubMode;
    }

    @Column(name = "API_CLASS_NAME", nullable = true, length = 256)
    public String getApiClassName() {
        return apiClassName;
    }


    public void setApiClassName(String apiClassName) {
        this.apiClassName = apiClassName;
    }

    @Column(name = "API_METHOD_NAME", nullable = true, length = 256)
    public String getApiMethodName() {
        return apiMethodName;
    }

    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口分组Id
     */

    @Column(name = "GROUP_ID", nullable = true, length = 30)
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口分组Id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  接口分组名称
     */

    @Column(name = "GROUP_NAME", nullable = true, length = 50)
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  接口分组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  实例数据
     */

    @Column(name = "EXAM_DATA", nullable = true, length = 200)
    public String getExamData() {
        return this.examData;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  实例数据
     */
    public void setExamData(String examData) {
        this.examData = examData;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */

    @Column(name = "CREATE_DATE", nullable = true)
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人名称
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人登录名称
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  更新日期
     */

    @Column(name = "UPDATE_DATE", nullable = true)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate) {
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

   /* @Column(name = "org_id", nullable = true, length = 50)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }*/


    @Column(name = "user_id", nullable = true, length = 50)
    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}
