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
 * @Description: 服务订单详情表
 * @author onlineGenerator
 * @date 2018-11-21 17:34:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_order_detail", schema = "")
@SuppressWarnings("serial")
public class ApiOrderDetailEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**订单id*/
	@Excel(name="订单id",width=15)
	private String orderId;
	/**场景id*/
	@Excel(name="场景id",width=15)
	private String sceneId;
	/**场景名称*/
	@Excel(name="场景名称",width=15)
	private String sceneName;
	/**服务id*/
	@Excel(name="服务id",width=15)
	private String apiId;
	/**服务名称*/
	@Excel(name="服务名称",width=15)
	private String apiName;
	/**资源ID*/
	@Excel(name="资源ID",width=15)
	private String resourceId;
	/**计费模型ID*/
	@Excel(name="计费模型ID",width=15)
	private String chargeId;


	private String resourceParam;

	private String methodPath;//主动推送能力对应的URL

	private String mqTag;


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
	 *@return: java.lang.String  订单id
	 */

	@Column(name ="ORDER_ID",nullable=true,length=32)
	public String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单id
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  场景id
	 */

	@Column(name ="SCENE_ID",nullable=true,length=32)
	public String getSceneId(){
		return this.sceneId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  场景id
	 */
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  场景名称
	 */

	@Column(name ="SCENE_NAME",nullable=true,length=50)
	public String getSceneName(){
		return this.sceneName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  场景名称
	 */
	public void setSceneName(String sceneName){
		this.sceneName = sceneName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务id
	 */

	@Column(name ="API_ID",nullable=true,length=32)
	public String getApiId(){
		return this.apiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务id
	 */
	public void setApiId(String apiId){
		this.apiId = apiId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务名称
	 */

	@Column(name ="API_NAME",nullable=true,length=50)
	public String getApiName(){
		return this.apiName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务名称
	 */
	public void setApiName(String apiName){
		this.apiName = apiName;
	}

	@Column(name ="RESOURCE_ID",nullable=true,length=32)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name ="CHARGE_ID",nullable=true,length=32)
	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	@Column(name ="METHOD_PATH",nullable=true,length=100)
	public String getMethodPath() {
		return methodPath;
	}

	public void setMethodPath(String methodPath) {
		this.methodPath = methodPath;
	}

	@Column(name ="RESOURCE_PARAM",nullable=true,length=100)
	public String getResourceParam() {
		return resourceParam;
	}

	public void setResourceParam(String resourceParam) {
		this.resourceParam = resourceParam;
	}

	@Column(name ="MQ_TAG",nullable=true,length=100)
	public String getMqTag() {
		return mqTag;
	}

	public void setMqTag(String mqTag) {
		this.mqTag = mqTag;
	}
}