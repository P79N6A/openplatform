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
    private java.lang.String id;
    /**类型名称*/
    @Excel(name="类型名称",width=15)
    private java.lang.String typeName;
    /**告警信息*/
    @Excel(name="信息描述",width=15)
    private java.lang.String message;
    /**告警时间*/
    @Excel(name="时间",width=15)
    private java.lang.String time;
    /**应用描述*/
    @Excel(name="应用ID",width=15)
    private java.lang.String appId;
    /**接口描述*/
	@Excel(name="接口ID",width=15)
	private java.lang.String apiId;
    /**应用描述*/
    @Excel(name="应用名称",width=15)
    private java.lang.String appName;
    /**接口描述*/
    @Excel(name="接口名称",width=15)
    private java.lang.String apiName;
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

    @Column(name ="TYPE_NAME",nullable=true,length=100)
    public java.lang.String getTypeName(){
        return this.typeName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用名称
     */
    public void setTypeName(java.lang.String typeName){
        this.typeName = typeName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用ID
     */

    @Column(name ="MESSAGE",nullable=true,length=32)
    public java.lang.String getMessage(){
        return this.message;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用ID
     */
    public void setMessage(java.lang.String message){
        this.message = message;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="TIME",nullable=true,length=200)
    public java.lang.String getTime(){
        return this.time;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setTime(java.lang.String time){
        this.time = time;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="APP_ID",nullable=true,length=10)
    public java.lang.String getAppId(){
        return this.appId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setAppId(java.lang.String appId){
        this.appId = appId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  接口角色ID
     */

	@Column(name ="API_ID",nullable=true,length=32)
	public java.lang.String getApiId(){
		return this.apiId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接口角色ID
	 */
    public void setApiId(java.lang.String apiId){
        this.apiId = apiId;
    }


    @Column(name ="APP_NAME",nullable=true,length=32)
    public java.lang.String getAppName(){
        return this.appName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  接口角色ID
     */
    public void setAppName(java.lang.String appName){
        this.appName = appName;
    }

    @Column(name ="API_NAME",nullable=true,length=32)
    public java.lang.String getApiName(){
        return this.apiName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  接口角色ID
     */
    public void setApiName(java.lang.String apiName){
        this.apiName = apiName;
    }

}
