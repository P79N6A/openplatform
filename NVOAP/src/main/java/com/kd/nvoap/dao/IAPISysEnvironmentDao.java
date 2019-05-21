package com.kd.nvoap.dao;

import com.kd.nvoap.model.ApiSysEnvironment;

import java.util.List;

public interface IAPISysEnvironmentDao {

	public List<ApiSysEnvironment> selectAll();

	public ApiSysEnvironment getByType(int type);

}
