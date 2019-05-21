package com.kd.op.api.service;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import com.kd.op.api.entity.MonitorEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.math.BigInteger;
import java.util.List;

public interface ApiSceneServiceI extends CommonService {

    public void delete(ApiSceneEntity entity) throws Exception;

    public void save(ApiSceneEntity entity) throws Exception;

    public void update(ApiSceneEntity entity) throws Exception;

    public void saveOrUpdate(ApiSceneEntity entity) throws Exception;

    //查询该API的id 是否在list里
    public Boolean idInList(JSONArray array, String[] idList);
    /**
     * 修改一对多
     *
     */
    //public void addMain(ApiSceneEntity apiScene) ;
    //public void updateMain(ApiSceneEntity apiScene,String headers,String requests,String returns);
    //public void delMain (ApiSceneEntity apiScene);

    //保存api信息
    public void addApi (ApiSceneTotalEntity sceneTotal, ApiSceneEntity apiScene) throws Exception;

    //更新api信息
    //public void updateApi (ApiSceneTotalEntity sceneTotal,ApiSceneEntity apiScene,String headers, String requests, String returns) throws Exception;

    //查询是否重名（用于add）
    public BigInteger nameCount(String sceneName);

    //查询是否重名（用于edit）
    public List<ApiSceneEntity> findRepeat(String sceneName);

}
