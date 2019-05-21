package com.kd.openplatform.charge.entity;

import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api_scene_rela", schema = "")
public class ApiSceneRelaEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**服务场景ID*/
    //@Excel(name="服务场景ID")
    private String sceneId;
    /**服务场景名称*/
    //@Excel(name="服务场景名称")
    private String sceneName;
    /**服务ID*/
    //@Excel(name="服务ID")
    private String apiId;
    /**服务名称*/
    //@Excel(name="服务名称")
    private String apiName;
    /**创建人登录名称*/
    //@Excel(name="创建人登录名称")
    private String createBy;
    /**创建人名称*/
    //@Excel(name="创建人名称")
    private String createName;
    /**创建时间*/
    //@Excel(name="创建时间")
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


    @Column(name = "api_id")
    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }


    @Column(name = "api_name")
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
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
