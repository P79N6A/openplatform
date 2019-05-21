package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "api_scene_charge", schema = "")
public class ApiSceneChargeEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**服务场景ID*/
    @Excel(name="服务场景ID")
    private String sceneId;
    /**服务场景名称*/
    @Excel(name="服务场景名称")
    private String sceneName;
    /**计费模型ID*/
    @Excel(name="计费模型ID")
    private String chargeId;
    /**创建人登录名称*/
    @Excel(name="创建人登录名称")
    private String createBy;
    /**创建人名称*/
    @Excel(name="创建人名称")
    private String createName;
    /**创建时间*/
    @Excel(name="创建时间")
    private Date createTime;

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

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "scene_id")
    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }


    @Column(name = "scene_name")
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }


    @Column(name = "charge_id")
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }


    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }


    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
