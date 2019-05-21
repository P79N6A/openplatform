package com.kd.op.api.page;

import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.ApiParamEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;

/**   
 * @Title: Entity
 * @Description: 接口信息表
 * @author onlineGenerator
 * @date 2018-10-10 16:46:50
 * @version V1.0   
 *
 */
public class ApiInfoPage implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**接口名称*/
    @Excel(name="接口名称")
	private java.lang.String apiName;
	/**接口描述*/
    @Excel(name="接口描述")
	private java.lang.String apiDesc;
	/**接口状态*/
    @Excel(name="接口状态")
	private java.lang.Integer apiStatus;
    /**接口发布状态*/
	@Excel(name="接口发布状态")
	private java.lang.Integer apiPublishStatus;
	/**接口可见状态*/
	@Excel(name="接口可见状态")
	private java.lang.Integer apiVisibleStatus;
	/**接口审核状态*/
	@Excel(name="接口审核状态")
	private java.lang.Integer apiAuditStatus;
	/**接口审核意见*/
	@Excel(name="接口审核意见",width=15)
	private java.lang.String apiAuditMsg;
	/**发布方式*/
	@Excel(name="发布方式",width=15,dicCode="pubMode")
	private java.lang.Integer pubMode;
	/**接口类全名*/
	@Excel(name="接口审核意见",width=15)
	private java.lang.String apiClassName;
	/**接口类方法名*/
	@Excel(name="接口审核意见",width=15)
	private java.lang.String apiMethodName;
	/**http请求地址*/
    @Excel(name="http请求地址")
	private java.lang.String reqAddrHttp;
	/**hsf请求地址*/
    @Excel(name="hsf请求地址")
	private java.lang.String reqAddrHsf;
	/**接口分组Id*/
    @Excel(name="接口分组Id")
	private java.lang.String groupId;
	/**接口分组名称*/
    @Excel(name="接口分组名称")
	private java.lang.String groupName;
	/**实例数据*/
    @Excel(name="实例数据")
	private java.lang.String examData;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口名称
	 */
	public java.lang.String getApiName(){
		return this.apiName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口名称
	 */
	public void setApiName(java.lang.String apiName){
		this.apiName = apiName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口描述
	 */
	public java.lang.String getApiDesc(){
		return this.apiDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口描述
	 */
	public void setApiDesc(java.lang.String apiDesc){
		this.apiDesc = apiDesc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  接口状态
	 */
	public java.lang.Integer getApiStatus(){
		return this.apiStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  接口状态
	 */
	public void setApiStatus(java.lang.Integer apiStatus){
		this.apiStatus = apiStatus;
	}
	
	public java.lang.Integer getApiPublishStatus() {
		return apiPublishStatus;
	}

	public void setApiPublishStatus(java.lang.Integer apiPublishStatus) {
		this.apiPublishStatus = apiPublishStatus;
	}

	public java.lang.Integer getApiVisibleStatus() {
		return apiVisibleStatus;
	}

	public void setApiVisibleStatus(java.lang.Integer apiVisibleStatus) {
		this.apiVisibleStatus = apiVisibleStatus;
	}
	
	public java.lang.Integer getApiAuditStatus() {
		return apiAuditStatus;
	}

	public void setApiAuditStatus(java.lang.Integer apiAuditStatus) {
		this.apiAuditStatus = apiAuditStatus;
	}
	
	
	public java.lang.String getApiAuditMsg() {
		return apiAuditMsg;
	}

	public void setApiAuditMsg(java.lang.String apiAuditMsg) {
		this.apiAuditMsg = apiAuditMsg;
	}

	public java.lang.Integer getPubMode() {
		return pubMode;
	}

	public void setPubMode(java.lang.Integer pubMode) {
		this.pubMode = pubMode;
	}

	public java.lang.String getApiClassName() {
		return apiClassName;
	}

	public void setApiClassName(java.lang.String apiClassName) {
		this.apiClassName = apiClassName;
	}

	public java.lang.String getApiMethodName() {
		return apiMethodName;
	}

	public void setApiMethodName(java.lang.String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  http请求地址
	 */
	public java.lang.String getReqAddrHttp(){
		return this.reqAddrHttp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  http请求地址
	 */
	public void setReqAddrHttp(java.lang.String reqAddrHttp){
		this.reqAddrHttp = reqAddrHttp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  hsf请求地址
	 */
	public java.lang.String getReqAddrHsf(){
		return this.reqAddrHsf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  hsf请求地址
	 */
	public void setReqAddrHsf(java.lang.String reqAddrHsf){
		this.reqAddrHsf = reqAddrHsf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口分组Id
	 */
	public java.lang.String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口分组Id
	 */
	public void setGroupId(java.lang.String groupId){
		this.groupId = groupId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口分组名称
	 */
	public java.lang.String getGroupName(){
		return this.groupName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口分组名称
	 */
	public void setGroupName(java.lang.String groupName){
		this.groupName = groupName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  实例数据
	 */
	public java.lang.String getExamData(){
		return this.examData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  实例数据
	 */
	public void setExamData(java.lang.String examData){
		this.examData = examData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	
	/**保存-接口参数*/
    @ExcelCollection(name="接口参数")
	private List<ApiParamEntity> apiParamList = new ArrayList<ApiParamEntity>();
	public List<ApiParamEntity> getApiParamList() {
		return apiParamList;
	}
	public void setApiParamList(List<ApiParamEntity> apiParamList) {
		this.apiParamList = apiParamList;
	}
	
}
