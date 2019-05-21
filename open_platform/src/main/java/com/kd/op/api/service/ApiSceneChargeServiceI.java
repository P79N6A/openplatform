package com.kd.op.api.service;

import com.kd.op.api.entity.ApiSceneChargeEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import org.jeecgframework.core.common.service.CommonService;

public interface ApiSceneChargeServiceI extends CommonService {
    public void save(ApiSceneChargeEntity entity) throws Exception;
    public void addApiSceneCharge (ApiSceneTotalEntity SceneTotal) throws Exception;
    public void updateApiSceneCharge (ApiSceneTotalEntity SceneTotal) throws Exception;
}
