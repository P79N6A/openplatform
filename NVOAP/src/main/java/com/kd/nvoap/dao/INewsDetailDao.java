package com.kd.nvoap.dao;

import com.kd.nvoap.model.NewsDetail;

import java.util.List;

public interface INewsDetailDao {

	public NewsDetail getById(String id);

	public List<NewsDetail> selectAll();
}
