package com.kd.openplatform.access.entity;

import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

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
	private String id;
	/**应用名称*/
//	@Excel(name="应用名称",width=15)
	private String appName;
	/**应用ID*/
//	@Excel(name="应用ID",width=15)
	private String appId;
	/**应用描述*/
//	@Excel(name="应用描述",width=15)
	private String appDesc;
	/**应用描述*/
//	@Excel(name="应用描述",width=15)
	private String appVersion;
	/**接口角色ID*/
//	@Excel(name="接口角色ID",width=15)
//	private java.lang.String appRoleId;
//	/**接口角色名称*/
//	@Excel(name="接口角色名称",width=15)
//	private java.lang.String appRoleName;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;

	private Integer auditStatus;//审核状态

	private String auditMsg;//审核意见


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用名称
	 */

	@Column(name ="APP_NAME",nullable=true,length=100)
	public String getAppName(){
		return this.appName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用名称
	 */
	public void setAppName(String appName){
		this.appName = appName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用ID
	 */

	@Column(name ="APP_ID",nullable=true,length=32)
	public String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用ID
	 */
	public void setAppId(String appId){
		this.appId = appId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用描述
	 */

	@Column(name ="APP_DESC",nullable=true,length=200)
	public String getAppDesc(){
		return this.appDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用描述
	 */
	public void setAppDesc(String appDesc){
		this.appDesc = appDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用描述
	 */

	@Column(name ="APP_VERSION",nullable=true,length=10)
	public String getAppVersion(){
		return this.appVersion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用描述
	 */
	public void setAppVersion(String appVersion){
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
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
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

}
