package com.kd.op.api.entity;

import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

@Entity
@Table(name = "api_middle_ware", schema = "", catalog = "")
@SuppressWarnings("serial")
public class ApiMiddleWareEntity implements java.io.Serializable {
    /**主键*/
    private String id;
    /**中间件名称*/
    @Excel(name="应用名称",width=15)
    private String middleName;
    /**接入功能描述*/
    @Excel(name="接入功能描述",width=15)
    private String middleDesc;
    /**开放形式*/
    @Excel(name="开放形式",width=15)
    private String openForm;
    /**状态*/
    @Excel(name="状态",width=15)
    private Integer status;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "middle_desc")
    public String getMiddleDesc() {
        return middleDesc;
    }

    public void setMiddleDesc(String middleDesc) {
        this.middleDesc = middleDesc;
    }

    @Basic
    @Column(name = "open_form")
    public String getOpenForm() {
        return openForm;
    }

    public void setOpenForm(String openForm) {
        this.openForm = openForm;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiMiddleWareEntity that = (ApiMiddleWareEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (middleDesc != null ? !middleDesc.equals(that.middleDesc) : that.middleDesc != null) return false;
        if (openForm != null ? !openForm.equals(that.openForm) : that.openForm != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (middleDesc != null ? middleDesc.hashCode() : 0);
        result = 31 * result + (openForm != null ? openForm.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
