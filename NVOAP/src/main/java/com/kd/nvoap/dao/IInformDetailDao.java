package com.kd.nvoap.dao;

import com.kd.nvoap.model.InformDetail;
import com.kd.nvoap.model.InformGroup;

import java.util.List;

public interface IInformDetailDao {

	public InformDetail getById(String id);
	
	public List<InformDetail> getByGroupId(String groupId);

	public List<InformDetail> selectAll();
	public List<InformDetail> getListByParam(String param);

}
