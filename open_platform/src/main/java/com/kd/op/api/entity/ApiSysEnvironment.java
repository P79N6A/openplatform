package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**
 * 环境配置信息表
 */
@Entity
@Table(name = "api_sys_environment", schema = "")
@SuppressWarnings("serial")
public class ApiSysEnvironment implements java.io.Serializable {
	/**主键*/
	private String id;
	private String ip;
	private Integer port;
	private Integer type;//0：测试环境，1：正式环境
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=32)
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

	@Column(name ="ip",nullable=true,length=255)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name ="port",nullable=true,length=10)
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name ="type",nullable=true,length=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
