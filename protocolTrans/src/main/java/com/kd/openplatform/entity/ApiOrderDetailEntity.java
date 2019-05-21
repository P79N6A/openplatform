package com.kd.openplatform.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;


/**   
 * @Title: Entity
 * @Description: 服务订单详情表
 * @author onlineGenerator
 * @date 2018-11-21 17:34:39
 * @version V1.0   
 *
 */

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
	private String orderId;
	/**场景id*/
	private String sceneId;
	/**场景名称*/
	private String sceneName;
	/**服务id*/
	private String apiId;
	/**服务名称*/
	private String apiName;
	/**资源ID*/
	private String resourceId;
	/**计费模型ID*/
	private String chargeId;


	private String resourceParam;

	private String methodPath;//主动推送能力对应的URL

	private String mqTag;


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getMethodPath() {
		return methodPath;
	}

	public void setMethodPath(String methodPath) {
		this.methodPath = methodPath;
	}

	public String getResourceParam() {
		return resourceParam;
	}

	public void setResourceParam(String resourceParam) {
		this.resourceParam = resourceParam;
	}

	public String getMqTag() {
		return mqTag;
	}

	public void setMqTag(String mqTag) {
		this.mqTag = mqTag;
	}
}