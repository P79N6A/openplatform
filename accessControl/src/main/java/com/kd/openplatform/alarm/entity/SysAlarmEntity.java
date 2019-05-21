package com.kd.openplatform.alarm.entity;

import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

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
@Table(name = "sys_alarm", schema = "")
@SuppressWarnings("serial")
public class SysAlarmEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**类型名称*/
    //@Excel(name="类型名称",width=15)
    private String typeName;
    /**告警信息*/
    //@Excel(name="信息描述",width=15)
    private String message;
    /**告警时间*/
    //@Excel(name="时间",width=15)
    private String time;
    /**应用描述*/
    //@Excel(name="应用ID",width=15)
    private String appId;
    /**接口描述*/
	//@Excel(name="接口ID",width=15)
	private String apiId;
    /**应用描述*/
    //@Excel(name="应用名称",width=15)
    private String appName;
    /**接口描述*/
    //@Excel(name="接口名称",width=15)
    private String apiName;
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

    @Column(name ="TYPE_NAME",nullable=true,length=100)
    public String getTypeName(){
        return this.typeName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用名称
     */
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用ID
     */

    @Column(name ="MESSAGE",nullable=true,length=32)
    public String getMessage(){
        return this.message;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用ID
     */
    public void setMessage(String message){
        this.message = message;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="TIME",nullable=true,length=200)
    public String getTime(){
        return this.time;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setTime(String time){
        this.time = time;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="APP_ID",nullable=true,length=10)
    public String getAppId(){
        return this.appId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setAppId(String appId){
        this.appId = appId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  接口角色ID
     */

	@Column(name ="API_ID",nullable=true,length=32)
	public String getApiId(){
		return this.apiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口角色ID
	 */
    public void setApiId(String apiId){
        this.apiId = apiId;
    }


    @Column(name ="APP_NAME",nullable=true,length=32)
    public String getAppName(){
        return this.appName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  接口角色ID
     */
    public void setAppName(String appName){
        this.appName = appName;
    }

    @Column(name ="API_NAME",nullable=true,length=32)
    public String getApiName(){
        return this.apiName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  接口角色ID
     */
    public void setApiName(String apiName){
        this.apiName = apiName;
    }

}
