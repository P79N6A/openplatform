package com.kd.op.api.service;

import com.kd.op.api.entity.ApiMiddleWareEntity;
import org.jeecgframework.core.common.service.CommonService;

public interface ApiMiddleWareService extends CommonService {
    public void delete(ApiMiddleWareEntity entity) throws Exception;

    public void save(ApiMiddleWareEntity entity) throws Exception;

    public void saveOrUpdate(ApiMiddleWareEntity entity) throws Exception;
}
