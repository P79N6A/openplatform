package com.kd.nvoap.service;

import com.kd.nvoap.model.NewsDetail;

import java.util.List;

//import com.kd.nvoap.model.ApiParam;

public interface INewsDetailService {

	public NewsDetail getById(String id);

	public List<NewsDetail>  loadAll();
}
