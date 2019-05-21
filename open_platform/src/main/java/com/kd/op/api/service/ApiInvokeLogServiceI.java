package com.kd.op.api.service;
import com.kd.op.api.entity.ApiInvokeLogEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface ApiInvokeLogServiceI extends CommonService{
	
 	public void delete(ApiInvokeLogEntity entity) throws Exception;
 	
 	public void save(ApiInvokeLogEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ApiInvokeLogEntity entity) throws Exception;
 	
}
