package com.kd.nvoap.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kd.nvoap.dao.IAPIInfoDao;
import com.kd.nvoap.dao.IAPIParamDao;
import com.kd.nvoap.dao.IUserDao;
import com.kd.nvoap.model.ApiInfo;
import com.kd.nvoap.model.ApiParam;
import com.kd.nvoap.service.IAPIInfoService;

@Service("apiInfoService")
public class APIInfoServiceImpl implements IAPIInfoService {

	@Resource
	private IAPIInfoDao apiInfoDao;
	
	@Autowired
	private IAPIParamDao apiParamDao;

	@Override
	public ApiInfo getById(String id) {
		return apiInfoDao.getById(id);
	}

	@Override
	public List<ApiParam> getParamsByApiId(String apiId) {
		return apiParamDao.getParamsByApiId(apiId);
	}

	@Override
	public List<ApiInfo> getByGroupId(String groupId) {
		return apiInfoDao.getByGroupId(groupId);
	}

	@Override
	public List<ApiInfo> getInfoByParam(String param) {
		
		return apiInfoDao.getInfoByParam(param);
	}
}
