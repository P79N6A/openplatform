package com.kd.op.api.entity;

public class ApiInfoTotalEntity {

    private ApiInfoEntity apiInfo;

    private String chargeModeIds;

    private String flowModeIds;

    private String resourceAccessIds;

    public ApiInfoEntity getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfoEntity apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getChargeModeIds() {
        return chargeModeIds;
    }

    public void setChargeModeIds(String chargeModeIds) {
        this.chargeModeIds = chargeModeIds;
    }

    public String getFlowModeIds() {
        return flowModeIds;
    }

    public void setFlowModeIds(String flowModeIds) {
        this.flowModeIds = flowModeIds;
    }

    public String getResourceAccessIds() {
        return resourceAccessIds;
    }

    public void setResourceAccessIds(String resourceAccessIds) {
        this.resourceAccessIds = resourceAccessIds;
    }
}
