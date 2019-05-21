package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "api_resource_access", catalog = "")
public class ApiResourceAccessEntity {
    private int id;
    private String resourceName;
    private String resourceDesc;
    private String resourcePlace;
    private String resourceSort;
    private String resourceStatus;
    private String apiSort;
    private String createName;
    private String createBy;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Basic
    @Column(name = "resource_desc")
    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    @Basic
    @Column(name = "resource_place")
    public String getResourcePlace() {
        return resourcePlace;
    }

    public void setResourcePlace(String resourcePlace) {
        this.resourcePlace = resourcePlace;
    }

    @Basic
    @Column(name = "resource_sort")
    public String getResourceSort() {
        return resourceSort;
    }

    public void setResourceSort(String resourceSort) {
        this.resourceSort = resourceSort;
    }

    @Basic
    @Column(name = "resource_status")
    public String getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    @Basic
    @Column(name = "api_sort")
    public String getApiSort() {
        return apiSort;
    }

    public void setApiSort(String apiSort) {
        this.apiSort = apiSort;
    }

    @Basic
    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Basic
    @Column(name = "create_by")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

}
