package com.kd.nvoap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 接口分组
 * @author onlineGenerator
 * @date 2018-09-30 16:43:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_group", schema = "")
@SuppressWarnings("serial")
public class ApiGroup implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**分组编号*/
	@Excel(name="分组编号",width=15)
	private java.lang.String groupCode;
	/**分组名称*/
	@Excel(name="分组名称",width=15)
	private java.lang.String groupName;
	/**分组描述*/
	@Excel(name="分组描述",width=15)
	private java.lang.String groupDesc;
	/**上级分组id*/
	@Excel(name="上级分组ID",width=15)
	private java.lang.String pGroupId;
	/**上级分组名称*/
	@Excel(name="上级分组名称",width=15)
	private java.lang.String pGroupName;
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
	 *@return: java.lang.String  分组编号
	 */

	@Column(name ="GROUP_CODE",nullable=true,length=32)
	public java.lang.String getGroupCode(){
		return this.groupCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分组编号
	 */
	public void setGroupCode(java.lang.String groupCode){
		this.groupCode = groupCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分组名称
	 */

	@Column(name ="GROUP_NAME",nullable=true,length=32)
	public java.lang.String getGroupName(){
		return this.groupName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分组名称
	 */
	public void setGroupName(java.lang.String groupName){
		this.groupName = groupName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分组描述
	 */

	@Column(name ="GROUP_DESC",nullable=true,length=256)
	public java.lang.String getGroupDesc(){
		return this.groupDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分组描述
	 */
	public void setGroupDesc(java.lang.String groupDesc){
		this.groupDesc = groupDesc;
	}
	
	@Column(name ="P_GROUP_ID",nullable=true,length=32)
	public java.lang.String getpGroupId() {
		return pGroupId;
	}

	public void setpGroupId(java.lang.String pGroupId) {
		this.pGroupId = pGroupId;
	}
	
	@Column(name ="P_GROUP_NAME",nullable=true,length=32)
	public java.lang.String getpGroupName() {
		return pGroupName;
	}

	public void setpGroupName(java.lang.String pGroupName) {
		this.pGroupName = pGroupName;
	}

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
	
}
