package com.kd.nvoap.service.impl;

import com.alibaba.fastjson.JSONArray;

import com.kd.nvoap.dao.IAPISysEnvironmentDao;
import com.kd.nvoap.model.ApiSysEnvironment;
import com.kd.nvoap.service.IAPISysEnvironmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("apiSysEnvironmentService")
public class APISysEnvironmentServiceImpl implements IAPISysEnvironmentService {

	@Resource
	private IAPISysEnvironmentDao apiSysEnvironmentDao;

	@Override
	public List<ApiSysEnvironment> loadAll() {
		return apiSysEnvironmentDao.selectAll();
	}

	@Override
	public ApiSysEnvironment getByType(int type) {
		return apiSysEnvironmentDao.getByType(type);
	}

}
