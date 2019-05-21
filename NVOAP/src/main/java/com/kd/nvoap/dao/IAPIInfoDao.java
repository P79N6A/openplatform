package com.kd.nvoap.dao;

import java.util.List;

import com.kd.nvoap.model.ApiInfo;

public interface IAPIInfoDao {

	public ApiInfo getById(String id);
	
	public List<ApiInfo> getByGroupId(String groupId);
	
	public List<ApiInfo> getInfoByParam(String param);
	
	
}
