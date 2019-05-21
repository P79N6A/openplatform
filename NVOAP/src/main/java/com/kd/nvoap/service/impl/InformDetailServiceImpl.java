package com.kd.nvoap.service.impl;

import com.kd.nvoap.dao.IInformDetailDao;
//import com.kd.nvoap.dao.IAPIParamDao;
import com.kd.nvoap.model.InformDetail;
import com.kd.nvoap.model.InformGroup;
//import com.kd.nvoap.model.ApiParam;
import com.kd.nvoap.service.IInformDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("informDetailService")
public class InformDetailServiceImpl implements IInformDetailService {

	@Resource
	private IInformDetailDao informDetailDao;
	
	//@Autowired
	//private IAPIParamDao apiParamDao;

	@Override
	public InformDetail getById(String id) {
		return informDetailDao.getById(id);
	}

	/*@Override
	public List<ApiParam> getParamsByApiId(String apiId) {
		return apiParamDao.getParamsByApiId(apiId);
	}*/

	@Override
	public List<InformDetail> getByGroupId(String groupId) {
		return informDetailDao.getByGroupId(groupId);
	}

	@Override
	public List<InformDetail> loadAll() {
		return informDetailDao.selectAll();
	}

	@Override
	public List<InformDetail> getListByParam(String param) {
		// TODO Auto-generated method stub
		return informDetailDao.getListByParam(param);
	}
}
