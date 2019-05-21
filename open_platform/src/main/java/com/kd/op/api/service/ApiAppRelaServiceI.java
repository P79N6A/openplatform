package com.kd.op.api.service;
import com.kd.op.api.entity.*;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public interface ApiAppRelaServiceI extends CommonService{
	
 	public void delete(ApiAppRelaEntity entity) throws Exception;
 	
 	public void save(ApiAppRelaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ApiAppRelaEntity entity) throws Exception;
 	
 	public void updateApiAppRela(String appId,String apiAppRelas);

 	public void updateChargeRela(ApiOrderEntity apiOrder, List<ApiOrderDetailEntity> apiOrderDetail) throws Exception;

	public BigInteger idCount(String apiAppRelaId);
}
