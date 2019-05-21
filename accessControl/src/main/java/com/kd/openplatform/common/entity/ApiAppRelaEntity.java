package com.kd.openplatform.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 接口角色关联表
 * @author onlineGenerator
 * @date 2018-10-02 09:18:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_app_rela", catalog = "")
@SuppressWarnings("serial")
public class ApiAppRelaEntity implements Serializable {
	/**主键*/
	private String id;
	/**接口id*/
	private String apiId;
	/**接口名称*/
	private String apiName;
	/**角色id*/
	private String appId;
	/**接口名称*/
	private String appName;

	private String userId;

	private String sceneId;
	private String resourceId;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name = "id",nullable= false,length= 36)
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
	 *@return: java.lang.String  接口id
	 */

	@Basic
	@Column(name = "api_id",nullable= true,length= 32)
	public String getApiId(){
		return this.apiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口id
	 */
	public void setApiId(String apiId){
		this.apiId = apiId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接口名称
	 */

	@Basic
	@Column(name = "api_name",nullable= true,length= 50)
	public String getApiName(){
		return this.apiName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口名称
	 */
	public void setApiName(String apiName){
		this.apiName = apiName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用id
	 */

	@Basic
	@Column(name = "app_id",nullable= true,length= 32)
	public String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用id
	 */
	public void setAppId(String appId){
		this.appId = appId;
	}

	@Basic
	@Column(name = "app_name",nullable= true,length= 255)
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Basic
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	@Basic
	@Column(name = "scene_id")
	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@Basic
	@Column(name = "resource_id")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}

