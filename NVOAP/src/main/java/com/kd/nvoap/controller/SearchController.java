package com.kd.nvoap.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.kd.nvoap.dao.IAPISysEnvironmentDao;
import com.kd.nvoap.model.ApiSysEnvironment;
import com.kd.nvoap.service.IAPISysEnvironmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.nvoap.model.ApiGroup;
import com.kd.nvoap.model.ApiInfo;
import com.kd.nvoap.model.ApiParam;
import com.kd.nvoap.service.IAPIGroupService;
import com.kd.nvoap.service.IAPIInfoService;
import com.kd.nvoap.util.APIDataTypeEnum;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Resource
	private IAPIInfoService apiInfoService;
	
	@Resource
	private IAPIGroupService apiGroupService;

    @Resource
    private IAPISysEnvironmentService apiSysEnvironmentService;
    
    //根据条件查询数据
    @RequestMapping("/searchapi")
	public ModelAndView searchapi(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api_sousuo");
		String valz=request.getParameter("valz");
		List<ApiInfo> infos = apiInfoService.getInfoByParam(valz);
		model.addObject("infos", infos);
		return model;
	}

    

	@RequestMapping("/apiInfo")
	public ModelAndView apiInfo(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("api");
		List<ApiGroup> groups = apiGroupService.loadAll();
		model.addObject("groups", groups);//填充组名
		//获取前台传过来的接口分组
		String groupId = request.getParameter("groupId");
		ApiGroup currentGroup = null;
		if(groupId != null && !groupId.isEmpty() && groups != null) {
			for(ApiGroup g:groups) {
				if(g.getId().equals(groupId)) {
					currentGroup = g;
					break;
				}
			}
		}
		if(currentGroup == null) {
			currentGroup = groups.get(0);
		}
		model.addObject("currentGroup", currentGroup);//填充当前组
		//获取当前分组的具体接口信息
		List<ApiInfo> apiInfos = apiInfoService.getByGroupId(currentGroup.getId());
		model.addObject("apiInfos", apiInfos);
		return model;
	}


    @RequestMapping(value="/loadApiInfo/{apiId}")
    @ResponseBody
    public JSONObject getAPIInfo(@PathVariable String apiId,Map<String, Object> model){
        JSONObject result = new JSONObject();
        ApiInfo apiInfo = apiInfoService.getById(apiId);
        result.put("apiInfo", apiInfo);

		ApiSysEnvironment test = apiSysEnvironmentService.getByType(0);
        ApiSysEnvironment official = apiSysEnvironmentService.getByType(1);
        result.put("testPrefix", test);
		result.put("officialPrefix", official);

        List<ApiParam> params = apiInfoService.getParamsByApiId(apiId);
        //区分请求头参数，请求参数，返回结果
        List<ApiParam> headerParams = new ArrayList<>();
        List<ApiParam> requestParams = new ArrayList<>();
        List<ApiParam> resultParams = new ArrayList<>();
        for(ApiParam param:params) {
            param.setDataTypeName(APIDataTypeEnum.getNameByIndex(param.getDataType()));
            if(param.getParamType() == 2) {
                headerParams.add(param);
            }else if(param.getParamType() == 0) {
                requestParams.add(param);
            }else if(param.getParamType() == 1) {
                resultParams.add(param);
            }
        }
        result.put("headerParams", headerParams);
        result.put("requestParams", requestParams);
        result.put("resultParams", resultParams);

        return result;
    }


    @RequestMapping(value="/loadReturnParams/{apiId}")
	@ResponseBody
    public JSONArray loadReturnParams(@PathVariable String apiId,Map<String, Object> model){
		JSONArray result = new JSONArray();
		List<ApiParam> params = apiInfoService.getParamsByApiId(apiId);
		List<ApiParam> resultParams = new ArrayList<>();
		for(ApiParam param:params) {
			param.setDataTypeName(APIDataTypeEnum.getNameByIndex(param.getDataType()));
			if(param.getParamType() == 1) {
				JSONObject obj = new JSONObject();
				obj.put("id", param.getId());
				obj.put("paramName", param.getParamName());
				obj.put("dataType", param.getDataTypeName());
				obj.put("paramDesc", param.getParamDesc());
				obj.put("defaultValue", param.getDefaultValue());
				obj.put("pId", param.getParentId());
				result.add(obj);
			}
		}
        return result;
    }  
}
