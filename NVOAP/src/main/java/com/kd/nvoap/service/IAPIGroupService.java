package com.kd.nvoap.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.kd.nvoap.model.ApiGroup;

public interface IAPIGroupService {

	public List<ApiGroup> loadAll();
	
	public JSONArray groupTree(String parentId);
}
