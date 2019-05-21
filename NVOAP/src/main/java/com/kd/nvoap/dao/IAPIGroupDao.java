package com.kd.nvoap.dao;

import java.util.List;

import com.kd.nvoap.model.ApiGroup;

public interface IAPIGroupDao {

	public List<ApiGroup> selectAll();
	
	public List<ApiGroup> getByParentId(String parentId);
}
