package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收费方式
 */
@Entity
@Table(name="api_charge_mode")
public class ApiChargeMode implements Serializable{

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    private String id;

    @Column(name ="type",nullable=true,length=6)
    private Integer type;

    @Column(name ="num",nullable=true,length=15)
    private Integer num;

    @Column(name ="start_num",nullable=true,length=15)
    private Integer startNum;

    @Column(name ="end_num",nullable=true,length=15)
    private Integer endNum;

    @Column(name ="unit",nullable=true,length=6)
    private Integer unit;

    @Column(name ="price",nullable=true,length=10,scale = 2)
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
