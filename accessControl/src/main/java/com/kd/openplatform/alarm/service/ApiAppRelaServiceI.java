package com.kd.openplatform.alarm.service;

import com.kd.openplatform.common.entity.ApiAppRelaEntity;

public interface ApiAppRelaServiceI {
	
 	public void delete(ApiAppRelaEntity entity) throws Exception;
 	
 	public void save(ApiAppRelaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(ApiAppRelaEntity entity) throws Exception;
 	
}
