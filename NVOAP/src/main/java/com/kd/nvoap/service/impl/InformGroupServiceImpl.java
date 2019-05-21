package com.kd.nvoap.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.kd.nvoap.dao.IInformGroupDao;
import com.kd.nvoap.model.InformGroup;
import com.kd.nvoap.service.IInformGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("informGroupService")
public class InformGroupServiceImpl implements IInformGroupService {

	@Resource
	private IInformGroupDao informGroupDao;

	@Override
	public List<InformGroup> loadAll() {
		return informGroupDao.selectAll();
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
