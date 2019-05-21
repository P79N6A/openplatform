package com.kd.op.api.service;

import com.kd.op.api.entity.ApiSceneResourceEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import org.jeecgframework.core.common.service.CommonService;

public interface ApiSceneResourceServiceI extends CommonService {
    public void save(ApiSceneResourceEntity entity) throws Exception;
    public void addApiSceneResource (ApiSceneTotalEntity SceneTotal) throws Exception;
    public void updateApiSceneResource (ApiSceneTotalEntity SceneTotal) throws Exception;
}
