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
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 服务订单表
 * @date 2018-11-21 17:33:39
 */
@Entity
@Table(name = "api_order", schema = "")
@SuppressWarnings("serial")
public class ApiOrderEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 应用id
     */
    @Excel(name = "应用id", width = 15)
    private String appId;
    /**
     * 应用名称
     */
    @Excel(name = "应用名称", width = 15)
    private String appName;
    /**
     * 订单金额
     */
    @Excel(name = "订单金额", width = 15)
    private Double money;
    /**
     * 支付状态
     */
    @Excel(name = "支付状态", width = 15)
    private Integer payStatus ;
    /**
     * 支付时间
     */
    @Excel(name = "支付时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    /**
     * 审核状态
     */
    @Excel(name = "审核状态", width = 15)
    private Integer auditStatus;
    /**
     * 审核意见
     */
    @Excel(name = "审核意见", width = 15)
    private String orderType;
    @Excel(name = "订单类型", width = 15)
    private String auditMsg;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 审核员名称
     */
    private String auditBy;

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
     * @return: java.lang.String  应用id
     */

    @Column(name = "APP_ID", nullable = true, length = 32)
    public String getAppId() {
        return this.appId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  应用id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  应用名称
     */

    @Column(name = "APP_NAME", nullable = true, length = 32)
    public String getAppName() {
        return this.appName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 方法: 取得java.lang.Double
     *
     * @return: java.lang.Double  订单金额
     */

    @Column(name = "MONEY", nullable = true, length = 32)
    public Double getMoney() {
        return this.money;
    }

    /**
     * 方法: 设置java.lang.Double
     *
     * @param: java.lang.Double  订单金额
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  支付状态
     */

    @Column(name = "PAY_STATUS", nullable = true, length = 2)
    public Integer getPayStatus() {
        return this.payStatus;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  支付状态
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  支付时间
     */

    @Column(name = "PAY_TIME", nullable = true, length = 32)
    public Date getPayTime() {
        return this.payTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  审核状态
     */

    @Column(name = "AUDIT_STATUS", nullable = true, length = 2)
    public Integer getAuditStatus() {
        return this.auditStatus;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  审核状态
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核意见
     */

    @Column(name = "AUDIT_MSG", nullable = true, length = 200)
    public String getAuditMsg() {
        return this.auditMsg;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核意见
     */
    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */

    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人名称
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人登录名称
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  更新日期
     */

    @Column(name = "UPDATE_DATE", nullable = true, length = 20)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "ORDER_TYPE", nullable = true, length = 20)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核员名称
     */

    @Column(name = "AUDIT_BY", nullable = true, length = 50)
    public String getAuditBy() {
        return this.auditBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核员名称
     */
    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }
}