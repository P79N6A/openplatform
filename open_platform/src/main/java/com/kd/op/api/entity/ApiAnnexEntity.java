package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zjy
 * @description 附件表 实体
 * @date 2019/01/10
 */
@Entity
@Table(name = "api_annex", schema = "")
@SuppressWarnings("serial")
public class ApiAnnexEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 唯一名称
     */
    private String uniqueName;
    /**
     * 附件原名称
     */
    private String annexName;
    /**
     * 附件描述
     */
    private String annexDesc;
    /**
     * 版本号
     */
    private String version;
    /**
     * 审核状态
     */
    private Integer auditStatus;
    /**
     * 审核意见
     */
    private String auditMsg;
    /**
     * 附件类型
     */
    private String annexType;
    /**
     * 附件大小
     */
    private Long annexSize;
    /**
     * 附件状态
     */
    private Integer annexStatus;
    /**
     * 附件格式
     */
    private String annexSuffix;
    /**
     * 附件存储路径
     */
    private String annexPath;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  主键
     */
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "UNIQUE_NAME")
    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }
    @Column(name = "ANNEX_NAME")
    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }
    @Column(name = "ANNEX_DESC")
    public String getAnnexDesc() {
        return annexDesc;
    }

    public void setAnnexDesc(String annexDesc) {
        this.annexDesc = annexDesc;
    }
    @Column(name = "VERSION")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    @Column(name = "ANNEX_TYPE")
    public String getAnnexType() {
        return annexType;
    }

    public void setAnnexType(String annexType) {
        this.annexType = annexType;
    }
    @Column(name = "ANNEX_SIZE")
    public Long getAnnexSize() {
        return annexSize;
    }

    public void setAnnexSize(Long annexSize) {
        this.annexSize = annexSize;
    }
    @Column(name = "ANNEX_STATUS")
    public Integer getAnnexStatus() {
        return annexStatus;
    }

    public void setAnnexStatus(Integer annexStatus) {
        this.annexStatus = annexStatus;
    }
    @Column(name = "ANNEX_SUFFIX")
    public String getAnnexSuffix() {
        return annexSuffix;
    }

    public void setAnnexSuffix(String annexSuffix) {
        this.annexSuffix = annexSuffix;
    }
    @Column(name = "ANNEX_PATH")
    public String getAnnexPath() {
        return annexPath;
    }

    public void setAnnexPath(String annexPath) {
        this.annexPath = annexPath;
    }

    @Column(name = "AUDIT_STATUS", nullable = true, length = 6)
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "AUDIT_MSG", nullable = true, length = 1000)
    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */

    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人名称
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人登录名称
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  更新日期
     */

    @Column(name = "UPDATE_DATE", nullable = true, length = 20)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
