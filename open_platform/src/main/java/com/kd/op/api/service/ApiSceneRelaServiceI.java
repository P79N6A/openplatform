package com.kd.op.api.service;

import com.kd.op.api.entity.ApiSceneRelaEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import org.jeecgframework.core.common.service.CommonService;

public interface ApiSceneRelaServiceI extends CommonService {
    public void save(ApiSceneRelaEntity entity) throws Exception;
    public void addApiSceneRela(ApiSceneTotalEntity SceneTotal) throws Exception;
    public void updateApiSceneRela (ApiSceneTotalEntity SceneTotal) throws Exception;
}
