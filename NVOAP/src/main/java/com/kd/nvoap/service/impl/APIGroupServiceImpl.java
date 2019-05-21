package com.kd.nvoap.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.nvoap.dao.IAPIGroupDao;
import com.kd.nvoap.model.ApiGroup;
import com.kd.nvoap.service.IAPIGroupService;

@Service("apiGroupService")
public class APIGroupServiceImpl implements IAPIGroupService {

	@Resource
	private IAPIGroupDao apiGroupDao;

	@Override
	public List<ApiGroup> loadAll() {
		return apiGroupDao.selectAll();
	}
	
	@Override
	public JSONArray groupTree(String parentId) {
 		JSONArray array = new JSONArray();
// 		List<ApiGroup> groups = query.list();
// 		for(ApiGroup group:groups){
//			JSONObject obj = new JSONObject();
//			obj.put("groupId",group.getId());
//			obj.put("text",group.getGroupName());
//			obj.put("nodes",groupTree(group.getId()));
//			array.add(obj);
//		}
		return array;
	}
}
