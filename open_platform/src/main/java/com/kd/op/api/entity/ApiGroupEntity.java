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
 * @Title: Entity
 * @Description: 接口分组表
 * @author onlineGenerator
 * @date 2018-10-20 10:11:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_group", schema = "")
@SuppressWarnings("serial")
public class ApiGroupEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**分组编号*/
	private java.lang.String groupCode;
	/**分组名称*/
	@Excel(name="分组名称",width=15)
	private java.lang.String groupName;
	/**分组描述*/
	@Excel(name="分组描述",width=15)
	private java.lang.String groupDesc;
	/**上级分组ID*/
	@Excel(name="上级分组ID",width=15)
	private java.lang.String parentId;
	/**上级分组名称*/
	private java.lang.String parentName;
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
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	@Column(name ="GROUP_DESC",nullable=true)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上级分组ID
	 */
	@Column(name ="PARENT_ID",nullable=true,length=32)
	public java.lang.String getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级分组ID
	 */
	public void setParentId(java.lang.String parentId){
		this.parentId = parentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上级分组名称
	 */
	@Column(name ="PARENT_NAME",nullable=true,length=32)
	public java.lang.String getParentName(){
		return this.parentName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级分组名称
	 */
	public void setParentName(java.lang.String parentName){
		this.parentName = parentName;
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
