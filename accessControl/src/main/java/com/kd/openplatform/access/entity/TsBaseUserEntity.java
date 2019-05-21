package com.kd.openplatform.access.entity;

import java.util.Date;

public class TsBaseUserEntity {
    private String id;

    private String userName;

    private Short checkStatus;

    private String hispassword;

    private Short type;

    private String userIp;

    private String beginDate;

    private String endDate;

    private Short passUpdateFlag;
    private Date passUpdateTime;
    private String approvalOpinion;
    private String realName;
    private String browser;
    private String userKey;
    private String password;
    private Short activitiSync;
    private Short status;
    private Short deleteFlag;
    private byte[] signature;
    private String departid;
    private String registerType;
    private String thirdId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Short getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Short checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getHispassword() {
        return hispassword;
    }

    public void setHispassword(String hispassword) {
        this.hispassword = hispassword;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Short getPassUpdateFlag() {
        return passUpdateFlag;
    }

    public void setPassUpdateFlag(Short passUpdateFlag) {
        this.passUpdateFlag = passUpdateFlag;
    }

    public Date getPassUpdateTime() {
        return passUpdateTime;
    }

    public void setPassUpdateTime(Date passUpdateTime) {
        this.passUpdateTime = passUpdateTime;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getActivitiSync() {
        return activitiSync;
    }

    public void setActivitiSync(Short activitiSync) {
        this.activitiSync = activitiSync;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }
}
