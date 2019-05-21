package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api_scene_resource", schema = "")
public class ApiSceneResourceEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**能力场景ID*/
    @Excel(name="能力场景ID")
    private String sceneId;
    /**能力场景名称*/
    @Excel(name="能力场景名称")
    private String sceneName;
    /**资源控制ID*/
    @Excel(name="资源控制ID")
    private String resourceId;
    /**资源名称*/
    @Excel(name="资源名称")
    private String resourceName;
    /**创建人登录名称*/
    @Excel(name="创建人账号")
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


    @Column(name = "resource_id")
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    @Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
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
