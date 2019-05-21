package com.kd.openplatform.entity;

import java.util.Date;

public class UserToken {
    /**
     *id
     */
//    private String id;
    /**
     *用户id
     */
    private String userId;
    /**
     *用户对应的token
     */
    private String token;
    /**
     *创建时间
     */
//    private Date createDate;
    /**
     *修改时间
     */
//    private Date updateDate;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
}
