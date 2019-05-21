package com.kd.op.api.entity;

public class ApiSceneTotalEntity {

    private ApiSceneEntity apiScene;



    private String chargeModeIds;

    private String resourceAccessIds;

    private String apiInfoIds;

    public ApiSceneEntity getApiScene() {
        return apiScene;
    }

    public void setApiScene(ApiSceneEntity apiScene) {
        this.apiScene = apiScene;
    }

    public String getChargeModeIds() {
        return chargeModeIds;
    }

    public void setChargeModeIds(String chargeModeIds) {
        this.chargeModeIds = chargeModeIds;
    }

    public String getResourceAccessIds() {
        return resourceAccessIds;
    }

    public void setResourceAccessIds(String resourceAccessIds) {
        this.resourceAccessIds = resourceAccessIds;
    }

    public String getapiInfoIds() {
        return apiInfoIds;
    }

    public void setapiInfoIds(String apiInfoIds) {
        this.apiInfoIds = apiInfoIds;
    }
}
