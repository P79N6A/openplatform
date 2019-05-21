package com.kd.marketplace.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private String id;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", signatureFile='" + signatureFile + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", createBy='" + createBy + '\'' +
                ", createName='" + createName + '\'' +
                ", updateDate=" + updateDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateName='" + updateName + '\'' +
                '}';
    }

    private String signatureFile;// 签名文件
    private String mobilePhone;// 手机
    private String officePhone;// 办公电话
    private String email;// 邮箱
    /**创建时间*/
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**创建人ID*/
    private String createBy;
    /**创建人名称*/
    private String createName;

    public String getSignatureFile() {
        return signatureFile;
    }

    public void setSignatureFile(String signatureFile) {
        this.signatureFile = signatureFile;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**修改时间*/
    private Date updateDate;
    /**修改人*/
    private String updateBy;
    /**修改人名称*/
    private String updateName;

}
