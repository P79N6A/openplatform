package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiAppEntity;
import com.kd.op.api.entity.ApiSysEnvironment;
import com.kd.op.api.service.ApiSysEnvironService;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务环境配置
 */
@Controller
@RequestMapping("/apiSysEnvironController")
public class ApiSysEnvironController {

    @Resource
    private ApiSysEnvironService apiSysEnvironService;
    @Resource
    private SystemService systemService;


    @RequestMapping(params = "list")
    public ModelAndView list() {
        return new ModelAndView("com/kd/op/environment/apiSysEnvironList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    @ResponseBody
    public void datagrid(ApiSysEnvironment environment, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiSysEnvironment.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, environment, request.getParameterMap());
        cq.add();
        this.apiSysEnvironService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(String id) {
        ModelAndView model = new ModelAndView("com/kd/op/environment/apiSysEnvironEdit");
        ApiSysEnvironment environment = apiSysEnvironService.getEntity(ApiSysEnvironment.class,id);
        if(environment != null){
            model.addObject("environment",environment);
        }
        return model;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public JSONObject doUpdate(ApiSysEnvironment environment){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","环境数据修改成功");
        try {
            ApiSysEnvironment old = apiSysEnvironService.getEntity(ApiSysEnvironment.class,environment.getId());
            old.setIp(environment.getIp());
//            old.setPort(environment.getPort());
            apiSysEnvironService.updateEntitie(old);
//            systemService.addLog("修改服务环境：" + old.getType(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO,"1");
            systemService.addLog("环境数据[" + environment.getId() +"]修改成功", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","环境数据修改失败");
            systemService.addLog("环境数据[" +  environment.getId() +"]修改失败", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        return result;
    }
}
