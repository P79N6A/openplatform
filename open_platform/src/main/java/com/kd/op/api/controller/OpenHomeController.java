package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.ApiParamEntity;
import com.kd.op.api.service.ApiInfoServiceI;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/openHome")
@Controller
public class OpenHomeController {

    @Resource
    private ApiInfoServiceI apiInfoService;
    @Resource
    private SystemService systemService;

    @RequestMapping(params = "home")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("website/home");
        return model;
    }


    @RequestMapping(params = "apiInfo")
    public ModelAndView tIndex() {
        ModelAndView model = new ModelAndView("website/apiInfo");
        return model;
    }

    @RequestMapping(params = "loadApi")
    @ResponseBody
    public JSONObject loadApi(String apiId){
        JSONObject result = new JSONObject();
        ApiInfoEntity apiInfo = apiInfoService.getEntity(ApiInfoEntity.class,apiId);

        result.put("apiInfo", apiInfo);
        List<ApiParamEntity> params = systemService.findByProperty(ApiParamEntity.class,"apiId",apiId);
        List<TSType> typeList = ResourceUtil.allTypes.get("parDType".toLowerCase());
        Collections.sort(typeList);
        Map<String,String> map = new HashMap<>();
        if(typeList != null && !typeList.isEmpty()){
            for (TSType type : typeList) {
                map.put(type.getTypecode(),type.getTypename());
            }
        }
        result.put("dataTypes", map);
        //区分请求头参数，请求参数，返回结果
        JSONArray resultParams = new JSONArray();
        JSONArray headerParams = new JSONArray();
        JSONArray reauestParams = new JSONArray();
        for(ApiParamEntity param:params) {
            JSONObject obj = new JSONObject();
            obj.put("id", param.getId());
            obj.put("paramName", param.getParamName());
            obj.put("dataType", param.getDataType());
            obj.put("paramDesc", param.getParamDesc());
            obj.put("defaultValue", param.getDefaultValue());
            obj.put("pId", param.getParentId());
            if(param.getParamType() == 1) {
                resultParams.add(obj);
            }else if(param.getParamType() == 0){
                reauestParams.add(obj);
            }else if(param.getParamType() == 2){
                headerParams.add(obj);
            }
        }
        result.put("headerParams", headerParams);
        result.put("requestParams", reauestParams);
        result.put("returnParams", resultParams);
        return result;
    }

    @RequestMapping(params="loadParams")
    @ResponseBody
    public JSONArray loadReturnParams(String apiId,Integer dataType){
        List<ApiParamEntity> params = systemService.findByProperty(ApiParamEntity.class,"apiId",apiId);
        JSONArray paramArray = new JSONArray();
        for(ApiParamEntity param:params) {
            JSONObject obj = new JSONObject();
            obj.put("id", param.getId());
            obj.put("paramName", param.getParamName());
            obj.put("dataType", param.getDataType());
            obj.put("paramDesc", param.getParamDesc());
            obj.put("defaultValue", param.getDefaultValue());
            obj.put("pId", param.getParentId());
            if(param.getParamType().equals(dataType)) {
                paramArray.add(obj);
            }
        }
        return paramArray;
    }

    @RequestMapping(params="loadApiByGroup")
    @ResponseBody
    public JSONArray loadApiByGroup(String groupId){
        JSONArray result = new JSONArray();
        List<ApiInfoEntity> apiInfos = systemService.findByProperty(ApiInfoEntity.class,"groupId",groupId);
        for(ApiInfoEntity api:apiInfos){
            if(api.getApiPublishStatus() == 1){
                JSONObject json = new JSONObject();
                json.put("id",api.getId());
                json.put("name",api.getApiName());
                result.add(json);
            }
        }
        return result;
    }

    @RequestMapping(params = "basicDocs")
    public ModelAndView basicDocs(){
        ModelAndView model = new ModelAndView("website/basicDoc");
        return model;
    }
}
