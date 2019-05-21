package com.kd.openplatform.alarm.service;


import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.entity.ApiInfoEntity;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.common.entity.ApiAppEntity;
import com.kd.openplatform.common.entity.ApiAppRelaEntity;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.common.utils.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("searchMethod")
public class SearchMethod extends CommonAccessService{
    @Autowired
    private ParamService paramService;
    @Autowired
    private MapCache mapCache;

    /**
     * 根据ID获得实体类
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiAppRelaEntity getApiAppRelaEntityById(String id){
        ApiAppRelaEntity entity = (ApiAppRelaEntity) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"id",id);
        return entity;
    }

    /**
     * 根据ID获得list类型的实体
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ApiAppRelaEntity> getApiAppRelaEntitiesByAppId(String id){
        List<ApiAppRelaEntity> entities = (List<ApiAppRelaEntity>) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"id",id);
        return entities;
    }

    /**
     * 根据appid获得该app应用的名称
     * @param appid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String getAppNameByAppId(String appid){
        //List<ApiAppRelaEntity> apiAppEntities = (List<ApiAppRelaEntity>) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"appId",appid);
        ApiAppEntity apiAppEntity=null;
        apiAppEntity=  (ApiAppEntity)mapCache.getGetAppNameByAppId().get(appid);
        if(apiAppEntity==null){
            apiAppEntity = (ApiAppEntity) commonAccessDao.queryUniqueByProperty(ApiAppEntity.class,"id",appid);//apiAppEntities.get(0);
            mapCache.getGetAppNameByAppId().put(appid,apiAppEntity);
        }
        //apiAppEntity = (ApiAppRelaEntity) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"appId",appid);//apiAppEntities.get(0);
        String appName = apiAppEntity.getApp_name();
        return appName;
    }

    /**
     * 根据appid获得api接口的名称
     * @param appid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String getApiNameByApiId(String apiid){
//    	System.err.println(commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"appId",appid));
//        List<ApiAppRelaEntity> apiAppEntities = (List<ApiAppRelaEntity>) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class,"appId",appid);
        ApiInfoEntity apiAppEntity =null;
        apiAppEntity=(ApiInfoEntity)mapCache.getGetAppNameByAppId().get(apiid);
        if(apiAppEntity==null){
            apiAppEntity=  (ApiInfoEntity)commonAccessDao.queryUniqueByProperty(ApiInfoEntity.class,"id",apiid) /*apiAppEntities.get(0)*/;
            mapCache.getGetAppNameByAppId().put(apiid,apiAppEntity);
        }
        String apiName = apiAppEntity.getApiName();
        return apiName;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getApiNameByParam(JSONObject param){
        String apiId = param.get("apiId").toString();
        return getApiNameByApiId(apiId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getAppNameByParam(JSONObject param){
        String appId = param.get("appId").toString();
        return getAppNameByAppId(appId);
    }

}
