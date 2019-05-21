package com.kd.openplatform.measure.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_charge_after", schema = "")
public class ChargeAfterEntity implements java.io.Serializable{
    private String id;
    private String count;
    private String typename;
    private String apiAppRelaId;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name="count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Basic
    @Column(name="typename")
    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @Basic
    @Column(name="api_app_rela_id")
    public String getApiAppRelaId() {
        return apiAppRelaId;
    }

    public void setApiAppRelaId(String apiAppRelaId) {
        this.apiAppRelaId = apiAppRelaId;
    }
}
