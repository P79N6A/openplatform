package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收费方式
 */
@Entity
@Table(name="api_charge_mode_rela")
public class ApiChargeModeRela implements Serializable{

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    private String id;

    @Column(name ="api_id",nullable=true,length=32)
    private String apiId;

    @Column(name ="charge_mode_id",nullable=true,length=32)
    private String chargeModeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getChargeModeId() {
        return chargeModeId;
    }

    public void setChargeModeId(String chargeModeId) {
        this.chargeModeId = chargeModeId;
    }
}
