package com.kd.nvoap.service;

import com.kd.nvoap.model.InformDetail;
//import com.kd.nvoap.model.ApiParam;
import com.kd.nvoap.model.InformGroup;

import java.util.List;

public interface IInformDetailService {

	public InformDetail getById(String id);
	
	//public List<ApiParam> getParamsByApiId(String apiId);
	
	public List<InformDetail> getByGroupId(String groupId);

	public List<InformDetail>  loadAll();
	public List<InformDetail> getListByParam(String param);

}
