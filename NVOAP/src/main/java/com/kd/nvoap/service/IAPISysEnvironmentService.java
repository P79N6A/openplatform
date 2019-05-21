package com.kd.nvoap.service;

import com.alibaba.fastjson.JSONArray;
import com.kd.nvoap.model.ApiSysEnvironment;

import java.util.List;

public interface IAPISysEnvironmentService {

	public List<ApiSysEnvironment> loadAll();

	public ApiSysEnvironment getByType(int type);
	
//	public JSONArray groupTree(String parentId);
}
