package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_app_charge", schema = "")
public class AppChargeEntity {

    /**主键*/
    private String id;

    private String app_id;

    private String app_name;

    private String charge_id;

    private String create_by;

    private String create_name;

    private String create_time;


    /**
     *方法: 取得String
     *@return: String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public String getCharge_id() {
        return charge_id;
    }

    public String getCreate_by() {
        return create_by;
    }

    public String getCreate_name() {
        return create_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public void setCharge_id(String charge_id) {
        this.charge_id = charge_id;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


}
