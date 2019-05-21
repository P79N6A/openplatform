package com.kd.openplatform.access.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name = "t_s_user_token", schema = "")
//@SuppressWarnings("serial")
public class UserTokenEntity implements java.io.Serializable {
//    /**主键*/
//    private java.lang.String id;

    private String userId;

    private String token;

//    private Date createDate;
//
//    private Date updateDate;

//    @Id
//    @GeneratedValue(generator = "paymentableGenerator")
//    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
//
//    @Column(name ="id",nullable=false,length=36)
//    public java.lang.String getId(){
//        return this.id;
//    }
//
//    public void setId(java.lang.String id){
//        this.id = id;
//    }

//    @Column(name ="user_id",nullable=true,length=255)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    @Column(name ="token",nullable=true,length=255)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    @Column(name ="create_date",nullable=true,length=255)
//    public Date getCreateDate() {
//        return createDate;
//    }

//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    @Column(name ="update_date",nullable=true,length=255)
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
}

