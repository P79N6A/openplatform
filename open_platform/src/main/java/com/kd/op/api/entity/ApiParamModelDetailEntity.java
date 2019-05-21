package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 参数模型详情
 * @date 2018-10-10 16:46:50
 */
@Entity
@Table(name = "api_param_model_detail", schema = "")
@SuppressWarnings("serial")
public class ApiParamModelDetailEntity implements java.io.Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 模型id
     */
    @Excel(name = "模型id", width = 15)
    private String modelId;
    /**
     * 参数名称
     */
    @Excel(name = "参数名称", width = 15)
    private String paramName;
    /**
     * 参数描述
     */
    @Excel(name = "参数描述", width = 15)
    private String paramDesc;
    /**
     * 数据类型
     */
    @Excel(name = "数据类型", width = 15, dicCode = "parDType")
    private Integer dataType;
    /**
     * 参数默认值
     */
    @Excel(name = "参数默认值", width = 15)
    private String defaultValue;
    /**
     * 上级参数id
     */
    @Excel(name = "上级参数id", width = 15)
    private String parentId;
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
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
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
     * @param: java.lang.String  id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "MODEL_ID", nullable = true, length = 32)
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  参数名称
     */

    @Column(name = "PARAM_NAME", nullable = true, length = 50)
    public String getParamName() {
        return this.paramName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  参数名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 方法: 取得javax.xml.soap.Text
     *
     * @return: javax.xml.soap.Text  参数描述
     */

    @Column(name = "PARAM_DESC", nullable = true, length = 1000)
    public String getParamDesc() {
        return this.paramDesc;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  参数描述
     */
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  数据类型
     */

    @Column(name = "DATA_TYPE", nullable = true, length = 10)
    public Integer getDataType() {
        return this.dataType;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  数据类型
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  参数默认值
     */

    @Column(name = "DEFAULT_VALUE", nullable = true, length = 50)
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  参数默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  上级参数id
     */

    @Column(name = "PARENT_ID", nullable = true, length = 32)
    public String getParentId() {
        return this.parentId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  上级参数id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
}
