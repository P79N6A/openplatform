package com.kd.nvoap.controller;

import com.alibaba.fastjson.JSONObject;

import com.kd.nvoap.model.NewsDetail;
import com.kd.nvoap.service.INewsDetailService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/news")
public class newsController {

	@Resource
	private INewsDetailService newsDetailService;

//    @RequestMapping("/news")
//	public ModelAndView basic() {
//		ModelAndView model = new ModelAndView("news");
//		return model;
//	}

	@RequestMapping(value="/loadAll")
	@ResponseBody
	public JSONObject loadAll(){
		JSONObject result = new JSONObject();
		List<NewsDetail> newsDetails = newsDetailService.loadAll();
		result.put("newsDetails", newsDetails);
		return result;
	}

    @RequestMapping("/news")
    public ModelAndView newsList(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("news");
        List<NewsDetail> newsDetails = newsDetailService.loadAll();
        model.addObject("newsDetails", newsDetails);//向模型中填充信息
        return model;
    }

    @RequestMapping(value="/loadNewsContent")
    public ModelAndView newsContent(HttpServletRequest request) {
	    String id = request.getParameter("newsId");
	    NewsDetail newsDetail= newsDetailService.getById(id);
        ModelAndView model = new ModelAndView("newsContent");
        model.addObject("newsDetail", newsDetail);//填充组名
        return model;
    }
}  