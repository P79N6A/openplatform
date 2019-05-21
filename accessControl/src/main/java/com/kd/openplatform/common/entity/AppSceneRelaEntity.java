package com.kd.openplatform.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "api_app_scene_rela", catalog = "")
public class AppSceneRelaEntity {
    private String id;
    private String sceneId;
    private String sceneName;
    private String appId;
    private String appName;
    private String chargeModeId;
    private String chargeAccountId;
    private String userId;
    //private Integer auditStatus;

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
    @Column(name = "scene_id")
    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    @Basic
    @Column(name = "scene_name")
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    @Basic
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "app_name")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Basic
    @Column(name = "charge_mode_id")
    public String getChargeModeId() {
        return chargeModeId;
    }

    public void setChargeModeId(String chargeModeId) {
        this.chargeModeId = chargeModeId;
    }

    @Basic
    @Column(name = "charge_account_id")
    public String getChargeAccountId() {
        return chargeAccountId;
    }

    public void setChargeAccountId(String chargeAccountId) {
        this.chargeAccountId = chargeAccountId;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /*@Basic
    @Column(name = "audit_status")
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }*/

}
