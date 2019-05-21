package com.kd.nvoap.service;

import java.util.List;

import com.kd.nvoap.model.ApiInfo;
import com.kd.nvoap.model.ApiParam;

public interface IAPIInfoService {

	public ApiInfo getById(String id);
	
	public List<ApiParam> getParamsByApiId(String apiId);
	
	public List<ApiInfo> getByGroupId(String groupId);
	
	public List<ApiInfo> getInfoByParam(String param);
}
