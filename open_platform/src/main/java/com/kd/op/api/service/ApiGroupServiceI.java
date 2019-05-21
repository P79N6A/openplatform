package com.kd.op.api.service;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiGroupEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ApiGroupServiceI extends CommonService {

    public void delete(ApiGroupEntity entity) throws Exception;

    public void save(ApiGroupEntity entity) throws Exception;

    public void saveOrUpdate(ApiGroupEntity entity) throws Exception;

    public JSONArray loadAllGroup(ApiGroupEntity apiGroup) throws Exception;

}