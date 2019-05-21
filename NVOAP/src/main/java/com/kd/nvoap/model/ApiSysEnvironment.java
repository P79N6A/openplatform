package com.kd.nvoap.model;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 接口环境
 * @author onlineGenerator
 * @date 2018-09-30 16:43:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "api_sys_environment", schema = "")
@SuppressWarnings("serial")
public class ApiSysEnvironment implements java.io.Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * IP地址
	 */
	@Excel(name = "IP地址", width = 15)
	private String ip;
	/**
	 * 端口号
	 */
	@Excel(name = "端口号", width = 15)
	private Integer port;
	/**
	 * 类型
	 */
	@Excel(name = "类型", width = 15)
	private Integer type;


	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  IP地址
	 */

	@Column(name = "IP", nullable = true, length = 32)
	public String getIp() {
		return this.ip;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  端口号
	 */

	@Column(name = "PORT", nullable = true, length = 32)
	public Integer getPort() {
		return this.port;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  端口号
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String  类型
	 */

	@Column(name = "TYPE", nullable = true, length = 256)
	public Integer getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String  类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}

}