package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "api_charge_account", schema = "")
public class ApiChargeAccountEntity implements java.io.Serializable {
    /**主键*/
    private java.lang.String id;
    /**剩余状态*/
    @Excel(name="剩余状态")
    private java.lang.String restState;
    /**类型名称*/
    @Excel(name="类型名称")
    private java.lang.String typename;

    private java.lang.String apiAppRelaId;

    private java.lang.String chargeModelId;

    /**
     *方法: 设置、取得java.lang.String
     *@return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id",nullable=false,length=36)
    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     *方法: 设置、取得java.lang.String
     *@return: java.lang.String  剩余状态
     */
    @Column(name = "rest_state",nullable=true,length=20)
    public java.lang.String getRestState() {
        return restState;
    }

    public void setRestState(java.lang.String restState) {
        this.restState = restState;
    }

    /**
     *方法: 设置、取得java.lang.String
     *@return: java.lang.String  类型名称
     */
    @Column(name = "typename")
    public java.lang.String getTypename() {
        return typename;
    }

    public void setTypename(java.lang.String typename) {
        this.typename = typename;
    }

    @Column(name = "api_app_rela_id")
    public String getApiAppRelaId() {
        return apiAppRelaId;
    }

    public void setApiAppRelaId(String apiAppRelaId) {
        this.apiAppRelaId = apiAppRelaId;
    }

    @Column(name = "charge_model_id")
    public String getChargeModelId() {
        return chargeModelId;
    }

    public void setChargeModelId(String chargeModelId) {
        this.chargeModelId = chargeModelId;
    }
}
