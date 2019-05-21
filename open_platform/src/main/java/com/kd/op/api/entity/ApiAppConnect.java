package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "api_app_connect", schema = "")
@SuppressWarnings("serial")
public class ApiAppConnect implements java.io.Serializable {
	/**主键*/
	private String id;
	/**应用ID*/
	private String appId;
	/**应用名称*/
	private String appName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createTime;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateTime;

	private Integer auditStatus;//审核状态

	private String auditMsg;//审核意见

	private String ip;//应用服务器的ip地址，用英文逗号,分割

	private Integer type;//0：测试环境，1：正式环境

	private Integer deleteFlag;//删除标志位，0：未删除，1：已删除

	private String connectAddr;//接入地址
	/**
	 * 审核员名称
	 */
	private String auditBy;


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

	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
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

	@Column(name ="UPDATE_TIME",nullable=true)
	public Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	@Column(name ="AUDIT_STATUS",nullable=true,length=6)
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(name ="AUDIT_MSG",nullable=true,length=2000)
	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	@Column(name ="IP",nullable=true,length=255)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name ="TYPE",nullable=true,length=6)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name ="DELETE_FLAG",nullable=true,length=6)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Transient
	public String getConnectAddr() {
		return connectAddr;
	}

	public void setConnectAddr(String connectAddr) {
		this.connectAddr = connectAddr;
	}
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  审核员名称
	 */

	@Column(name = "AUDIT_BY", nullable = true, length = 50)
	public String getAuditBy() {
		return this.auditBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  审核员名称
	 */
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
}
