package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 接口应用表
 * @author onlineGenerator
 * @date 2018-10-05 10:10:48
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_app", schema = "")
@SuppressWarnings("serial")
public class ApiAppEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**应用名称*/
	@Excel(name="应用名称",width=15)
	private java.lang.String appName;
	/**应用ID*/
	@Excel(name="应用ID",width=15)
	private java.lang.String appId;
	/**应用描述*/
	@Excel(name="应用描述",width=15)
	private java.lang.String appDesc;
	/**应用描述*/
	@Excel(name="应用描述",width=15)
	private java.lang.String appVersion;
	/**接口角色ID*/
//	@Excel(name="接口角色ID",width=15)
//	private java.lang.String appRoleId;
//	/**接口角色名称*/
//	@Excel(name="接口角色名称",width=15)
//	private java.lang.String appRoleName;
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

	private Integer auditStatus;//审核状态

	private String auditMsg;//审核意见

	private Integer publishStatus;//发布状态



	private String innerMerchantno;//审核意见



	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用名称
	 */

	private Integer appType;//应用类型
	private String operator_id;//运营商标识


	@Column(name ="APP_NAME",nullable=true,length=100)
	public java.lang.String getAppName(){
		return this.appName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用名称
	 */
	public void setAppName(java.lang.String appName){
		this.appName = appName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用ID
	 */

	@Column(name ="APP_ID",nullable=true,length=32)
	public java.lang.String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用ID
	 */
	public void setAppId(java.lang.String appId){
		this.appId = appId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用描述
	 */

	@Column(name ="APP_DESC",nullable=true,length=200)
	public java.lang.String getAppDesc(){
		return this.appDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用描述
	 */
	public void setAppDesc(java.lang.String appDesc){
		this.appDesc = appDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用描述
	 */

	@Column(name ="APP_VERSION",nullable=true,length=10)
	public java.lang.String getAppVersion(){
		return this.appVersion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用描述
	 */
	public void setAppVersion(java.lang.String appVersion){
		this.appVersion = appVersion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口角色ID
	 */

//	@Column(name ="APP_ROLE_ID",nullable=true,length=32)
//	public java.lang.String getAppRoleId(){
//		return this.appRoleId;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  接口角色ID
//	 */
//	public void setAppRoleId(java.lang.String appRoleId){
//		this.appRoleId = appRoleId;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口角色名称
	 */

//	@Column(name ="APP_ROLE_NAME",nullable=true,length=50)
//	public java.lang.String getAppRoleName(){
//		return this.appRoleName;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  接口角色名称
//	 */
//	public void setAppRoleName(java.lang.String appRoleName){
//		this.appRoleName = appRoleName;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
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

	@Column(name ="CREATE_BY",nullable=true,length=50)
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

	@Column(name ="CREATE_DATE",nullable=true,length=20)
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

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
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

	@Column(name ="UPDATE_BY",nullable=true,length=50)
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

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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

	@Column(name ="AUDIT_STATUS",nullable=true,length=6)
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(name ="AUDIT_MSG",nullable=true,length=1000)
	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	@Column(name ="PUBLISH_STATUS",nullable=true,length=6)
	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	@Column(name ="APP_TYPE",nullable=false)
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	@Column(name ="OPERATOR_ID",nullable=true)
	public java.lang.String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(java.lang.String operator_id) {
		this.operator_id = operator_id;
	}

	@Column(name ="INNER_MERCHANTNO",nullable=true)
	public String getInnerMerchantno() {
		return innerMerchantno;
	}
	public void setInnerMerchantno(String innerMerchantno) {
		this.innerMerchantno = innerMerchantno;
	}
}
