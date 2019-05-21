package com.kd.openplatform.charge.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_charge_test_account", catalog = "")
public class ChargeTestAccountEntity implements java.io.Serializable{
    private String id;
    private String restState;
    //private String typename;
    private String invokeMethod;
    private String userId;
    private String apiId;
    
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rest_state")
    public String getRestState() {
        return restState;
    }

    public void setRestState(String restState) {
        this.restState = restState;
    }

//    @Basic
//    @Column(name = "typename")
//    public String getTypename() {
//        return typename;
//    }
//
//    public void setTypename(String typename) {
//        this.typename = typename;
//    }

    @Basic
    @Column(name = "invoke_method")
    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "api_id")
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
}
