package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api_scene", schema = "")
public class ApiSceneEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**服务场景名称*/
    @Excel(name="服务场景名称")
    private String sceneName;
    /**服务场景描述*/
    @Excel(name="服务场景描述")
    private String sceneDes;
    /**场景状态*/
    @Excel(name="场景状态")
    private Integer sceneStatus;
    /**审核状态*/
    @Excel(name="审核状态")
    private Integer sceneAuditStatus;
    /**审核信息*/
    @Excel(name="审核信息")
    private String sceneAuditMsg;
    /**创建人登录名称*/
    @Excel(name="创建人登录名称")
    private String createBy;
    /**创建人名称*/
    @Excel(name="创建人名称")
    private String createName;
    /**创建日期*/
    @Excel(name="创建日期")
    private Date createTime;
    /**更新人名称*/
    @Excel(name="更新人名称")
    private String updateName;
    /**更新人日期*/
    @Excel(name="更新人日期")
    private Date updateDate;
    /**更新人登录名称*/
    @Excel(name="更新人登录名称")
    private String updateBy;
    /**资源控制*/
    @Excel(name="资源控制")
    private Integer resourceCtrl;

    /**
     *方法: 取得String
     *@return: String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "id",nullable=false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *方法: 取得String
     *@return: String  服务场景名称
     */
    @Column(name = "scene_name")
    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     *方法: 取得String
     *@return: String  服务场景描述
     */
    @Column(name = "scene_des")
    public String getSceneDes() {
        return sceneDes;
    }

    public void setSceneDes(String sceneDes) {
        this.sceneDes = sceneDes;
    }

    /**
     *方法: 取得String
     *@return: String  场景状态
     */
    @Column(name = "scene_status")
    public Integer getSceneStatus() {
        return sceneStatus;
    }

    public void setSceneStatus(Integer sceneStatus) {
        this.sceneStatus = sceneStatus;
    }

    /**
     *方法: 取得String
     *@return: String  审核状态
     */
    @Column(name = "scene_audit_status")
    public Integer getSceneAuditStatus() {
        return sceneAuditStatus;
    }

    public void setSceneAuditStatus(Integer sceneAuditStatus) {
        this.sceneAuditStatus = sceneAuditStatus;
    }

    /**
     *方法: 取得String
     *@return: String  审核信息
     */
    @Column(name = "scene_audit_msg")
    public String getSceneAuditMsg() {
        return sceneAuditMsg;
    }

    public void setSceneAuditMsg(String sceneAuditMsg) {
        this.sceneAuditMsg = sceneAuditMsg;
    }

    /**
     *方法: 取得String
     *@return: String  创建人登录名称
     */
    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     *方法: 取得String
     *@return: String  创建人名称
     */
    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     *方法: 取得String
     *@return: String  创建日期
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *方法: 取得String
     *@return: String  更新人登录名称
     */
    @Column(name = "update_by")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     *方法: 取得String
     *@return: String  更新人名称
     */
    @Column(name = "update_name")
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     *方法: 取得String
     *@return: String  更新日期
     */
    @Column(name = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    @Column(name = "resource_ctrl")
    public Integer getResourceCtrl() {
        return resourceCtrl;
    }

    public void setResourceCtrl(Integer resourceCtrl) {
        this.resourceCtrl = resourceCtrl;
    }

}
