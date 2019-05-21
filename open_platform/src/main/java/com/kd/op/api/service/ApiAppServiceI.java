package com.kd.op.api.service;
import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiAppEntity;
import com.kd.op.api.entity.ApiAppKeysEntity;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public interface ApiAppServiceI extends CommonService{
	
 	public void delete(ApiAppEntity entity) throws Exception;
 	
 	public void save(ApiAppEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ApiAppEntity entity) throws Exception;

 	//查询该API的id 是否在list里
 	public Boolean idInList(JSONArray array, String[] idList);

	//查询是否重名
	public BigInteger nameCount(String apiName);

	//查询当前用户所创建的appId
	public Object[] getCurrentUserAppIds();

	//根据id查询实体类
	ApiAppEntity getApiAppEntityById(String id);

	//查询可发布的app列表
    List<ApiAppEntity> getPublishApiAppList(ApiAppEntity apiApp,HttpServletRequest request);

    /**
     * 封装ApiAppKeysEntity
     * @param apiAppEntity
     * @return
     */
    ApiAppKeysEntity getApiAppKeys(ApiAppEntity apiAppEntity);

    /**
     * 通过appId查询ApiAppKeysEntity
     * @param appId
     * @return
     */
    ApiAppKeysEntity getApiAppKeysEntityByAppId(String appId);
}
