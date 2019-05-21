package com.kd.nvoap.dao;

import java.util.List;

import com.kd.nvoap.model.ApiParam;

public interface IAPIParamDao {

	public List<ApiParam> getParamsByApiId(String apiId);
}
