package com.kd.nvoap.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.nvoap.model.InformGroup;
import com.kd.nvoap.model.InformDetail;
import com.kd.nvoap.service.IInformGroupService;
import com.kd.nvoap.service.IInformDetailService;
import com.kd.nvoap.util.APIDataTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/inform")
public class informController {
	@Resource
	private IInformGroupService informGroupService;

	@Resource
	private IInformDetailService informDetailService;

	/*public ModelAndView basic() {
		ModelAndView model = new ModelAndView("inform");
		return model;
	}*/
	@RequestMapping("/informDetail")
	public ModelAndView informDetail(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("inform");//加载新模型inform
		List<InformGroup> informGroups = informGroupService.loadAll();//从数据库中获取通知组名
		model.addObject("informGroups", informGroups);//填充左边栏通知组名
		String nd=request.getParameter("nd");
		model.addObject("nd", nd);
		

		//获取前台传过来的接口分组
		//String groupId = request.getParameter("groupId");

		//获取当前分组的具体通知信息
		//List<InformDetail> informDetails = informDetailService.getByGroupId(groupId);
		//model.addObject("informDetails", informDetails);

        return model;
	}

    @RequestMapping("/quickView")
    public ModelAndView quickView(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("inform");//加载新模型inform
        List<InformGroup> informGroups = informGroupService.loadAll();//从数据库中获取通知组名
        model.addObject("informGroups", informGroups);//填充左边栏通知组名

        String id = request.getParameter("informId");//从前台获取id
        InformDetail informDetail = informDetailService.getById(id);//请求后台数据
        model.addObject("informDetail",informDetail);//填充
        return model;
	}


    @RequestMapping(value="/loadInformTitle/{groupId}")
    @ResponseBody
    public JSONObject loadInformTitle(@PathVariable String groupId, Map<String, Object> model){
        JSONObject result = new JSONObject();
        List<InformDetail> informDetails = informDetailService.getByGroupId(groupId);
        result.put("informDetails", informDetails);
        return result;
    }

    @RequestMapping(value="/loadInformContent/{informId}")
    @ResponseBody
    public JSONObject loadInformContent(@PathVariable String informId, Map<String, Object> model){
        JSONObject result = new JSONObject();
        InformDetail informDetail = informDetailService.getById(informId);
        result.put("informDetail", informDetail);
        return result;
    }

    @RequestMapping(value="/loadAll")
    @ResponseBody
    public JSONObject loadAll(){
        JSONObject result = new JSONObject();
        List<InformDetail> informDetails = informDetailService.loadAll();
        result.put("informDetails", informDetails);
        return result;
    }
}  