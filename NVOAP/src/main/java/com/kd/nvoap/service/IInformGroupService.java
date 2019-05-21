package com.kd.nvoap.service;

import com.alibaba.fastjson.JSONArray;
import com.kd.nvoap.model.InformGroup;

import java.util.List;

public interface IInformGroupService {

	public List<InformGroup> loadAll();
	
	public JSONArray groupTree(String parentId);
	
}
