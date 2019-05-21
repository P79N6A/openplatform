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
 * @Description: 接口信息表
 * @author onlineGenerator
 * @date 2018-10-10 16:46:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_param", schema = "")
@SuppressWarnings("serial")
public class ApiParamEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**接口id*/
	@Excel(name="接口id",width=15)
	private java.lang.String apiId;
	/**参数名称*/
	@Excel(name="参数名称",width=15)
	private java.lang.String paramName;
	/**参数描述*/
	@Excel(name="参数描述",width=15)
	private java.lang.String paramDesc;
	/**参数类型*/
	@Excel(name="参数类型",width=15,dicCode="parType")
	private java.lang.Integer paramType;
	/**是否可见*/
	@Excel(name="是否可见",width=15,dicCode="parVisible")
	private java.lang.Integer paramVisible;
	/**数据类型*/
	@Excel(name="数据类型",width=15,dicCode="parDType")
	private java.lang.Integer dataType;
	/**参数默认值*/
	@Excel(name="参数默认值",width=15)
	private java.lang.String defaultValue;
	/**上级参数id*/
	@Excel(name="上级参数id",width=15)
	private java.lang.String parentId;
	/**上级参数名称*/
	@Excel(name="上级参数名称",width=15)
	private java.lang.String parentName;
	/**排序*/
	@Excel(name="排序",width=15)
	private java.lang.Integer sort;
	/**是否加密 0 不加密 1加密*/
	@Excel(name="是否加密",width=15)
	private java.lang.Integer paramEncrypt;

	@Excel(name="是否资源控制",width=15)
	private java.lang.Integer isSource;


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
	 *@return: java.lang.String  接口id
	 */
	
	@Column(name ="API_ID",nullable=true,length=32)
	public java.lang.String getApiId(){
		return this.apiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口id
	 */
	public void setApiId(java.lang.String apiId){
		this.apiId = apiId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数名称
	 */
	
	@Column(name ="PARAM_NAME",nullable=true,length=50)
	public java.lang.String getParamName(){
		return this.paramName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数名称
	 */
	public void setParamName(java.lang.String paramName){
		this.paramName = paramName;
	}
	
	/**
	 *方法: 取得javax.xml.soap.Text
	 *@return: javax.xml.soap.Text  参数描述
	 */
	
	@Column(name ="PARAM_DESC",nullable=true,length=1000)
	public java.lang.String getParamDesc(){
		return this.paramDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数描述
	 */
	public void setParamDesc(java.lang.String paramDesc){
		this.paramDesc = paramDesc;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  参数类型
	 */
	
	@Column(name ="PARAM_TYPE",nullable=true,length=10)
	public java.lang.Integer getParamType(){
		return this.paramType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  参数类型
	 */
	public void setParamType(java.lang.Integer paramType){
		this.paramType = paramType;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否可见
	 */
	
	@Column(name ="PARAM_VISIBLE",nullable=true,length=10)
	public java.lang.Integer getparamVisible(){
		return this.paramVisible;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否可见
	 */
	public void setparamVisible(java.lang.Integer paramVisible){
		this.paramVisible = paramVisible;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数据类型
	 */
	
	@Column(name ="DATA_TYPE",nullable=true,length=10)
	public java.lang.Integer getDataType(){
		return this.dataType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数据类型
	 */
	public void setDataType(java.lang.Integer dataType){
		this.dataType = dataType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数默认值
	 */
	
	@Column(name ="DEFAULT_VALUE",nullable=true,length=50)
	public java.lang.String getDefaultValue(){
		return this.defaultValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数默认值
	 */
	public void setDefaultValue(java.lang.String defaultValue){
		this.defaultValue = defaultValue;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上级参数id
	 */
	
	@Column(name ="PARENT_ID",nullable=true,length=32)
	public java.lang.String getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级参数id
	 */
	public void setParentId(java.lang.String parentId){
		this.parentId = parentId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上级参数名称
	 */
	
	@Column(name ="PARENT_NAME",nullable=true,length=50)
	public java.lang.String getParentName(){
		return this.parentName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级参数名称
	 */
	public void setParentName(java.lang.String parentName){
		this.parentName = parentName;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	
	@Column(name ="SORT",nullable=true,length=5)
	public java.lang.Integer getSort(){
		return this.sort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否加密
	 */

	@Column(name ="PARAM_ENCRYPT",nullable=true,length=10)
	public java.lang.Integer getParamEncrypt() {
		return this.paramEncrypt;
	}

	public void setParamEncrypt(java.lang.Integer paramEncrypt) {
		this.paramEncrypt = paramEncrypt;
	}
	
	@Column(name ="IS_SOURCE",nullable=true,length=10)
	public Integer getIsSource() {
		return isSource;
	}

	public void setIsSource(Integer isSource) {
		this.isSource = isSource;
	}
}
