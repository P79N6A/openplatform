package com.kd.nvoap.service.impl;

import com.kd.nvoap.dao.INewsDetailDao;
import com.kd.nvoap.model.NewsDetail;
import com.kd.nvoap.service.INewsDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//import com.kd.nvoap.dao.IAPIParamDao;
//import com.kd.nvoap.model.ApiParam;

@Service("newsDetailService")
public class NewsDetailServiceImpl implements INewsDetailService {

	@Resource
	private INewsDetailDao newsDetailDao;
	
	//@Autowired
	//private IAPIParamDao apiParamDao;

	@Override
	public NewsDetail getById(String id) {
		return newsDetailDao.getById(id);
	}

	@Override
	public List<NewsDetail> loadAll() {
		return newsDetailDao.selectAll();
	}
}
