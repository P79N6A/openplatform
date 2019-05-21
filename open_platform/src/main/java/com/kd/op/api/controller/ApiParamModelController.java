package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiGroupServiceI;
import com.kd.op.api.service.ApiParamModelServiceI;
import com.kd.op.util.StringTransUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjy
 * @version V1.0
 * @Title: Controller
 * @Description: 参数模型
 * @date 2018-10-20 10:11:17
 */
@Controller
@RequestMapping("/apiParamModelController")
public class ApiParamModelController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiParamModelController.class);

    @Autowired
    private ApiParamModelServiceI apiParamModelService;
    @Autowired
    private SystemService systemService;


    /**
     * 参数模型列表跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/parammodel/apiParamModelList");
    }

    /**
     * 加载参数模型数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiParamModelEntity apiParamModel, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //修改为模糊查询
        if(StringUtil.isNotEmpty(apiParamModel.getModelName())){
            String s = StringTransUtil.stringReplace(apiParamModel.getModelName());
            apiParamModel.setModelName("*"+s+"*");
        }
        CriteriaQuery cq = new CriteriaQuery(ApiParamModelEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiParamModel, request.getParameterMap());
        //根据当前登录人过滤 管理员查看全部预留
        //cq.eq("createBy",ResourceUtil.getSessionUserName().getUserName());
        cq.add();
        this.apiParamModelService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除参数模型
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiParamModelEntity apiParamModel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "参数模型已作废";
        try {
            apiParamModelService.delApiParamModel(apiParamModel);
            systemService.addLog("参数模型[" + apiParamModel.getId() + "]作废成功", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "参数模型作废失败";
            systemService.addLog("参数模型[" + apiParamModel.getId() + "]作废失败", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }



    /**
     * 添加参数模型
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiParamModelEntity apiParamModel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "参数模型添加成功";
        try {
            apiParamModelService.addApiParamModel(apiParamModel);
            systemService.addLog("创建参数模型[" + apiParamModel.getId() + "]", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "参数模型添加失败";
            systemService.addLog("创建参数模型[" + apiParamModel.getId() + "]", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新参数模型
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiParamModelEntity apiParamModel, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "参数模型更新成功";
        try {
            apiParamModelService.updateApiParamModel(apiParamModel);
            systemService.addLog("更新参数模型[" + apiParamModel.getId() + "]", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "参数模型更新失败";
            systemService.addLog("更新参数模型[" + apiParamModel.getId() + "]", Globals.MODULE_PARAM_MODEL, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 参数模型新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiParamModelEntity apiParamModel, HttpServletRequest req) {

        return new ModelAndView("com/kd/op/parammodel/apiParamModel-add");
    }

    /**
     * 参数模型编辑，查看页面跳转
     *@param type 1 编辑 2 查看
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiParamModelEntity apiParamModel,Integer type, HttpServletRequest req) {
        String url = "";
        ModelAndView mv = new ModelAndView();
        if(type == 1){
            url = "com/kd/op/parammodel/apiParamModel-update";
        }else if(type == 2){
            url = "com/kd/op/parammodel/apiParamModel-look";
        }
        if (StringUtil.isNotEmpty(apiParamModel.getId())) {
            try {
                apiParamModel = apiParamModelService.getApiParamModel(apiParamModel);
            } catch (Exception e) {
                logger.error("执行失败：" + e.getMessage());
            }
            req.setAttribute("apiParamModel", apiParamModel);
        }
        mv.setViewName(url);
        return mv;
    }

    @RequestMapping(params="selectMode")
    public ModelAndView selectMode(){
        ModelAndView model = new ModelAndView("com/kd/op/parammodel/selectModel");
        JSONArray array = new JSONArray();
        //获取当前登陆用户
        TSUser user = ResourceUtil.getSessionUser();
        if(user != null){
            List<ApiParamModelEntity> paramModels = systemService.findByProperty(ApiParamModelEntity.class,"createBy",user.getUserName());
            for(ApiParamModelEntity pm:paramModels){
                JSONObject json = new JSONObject();
                json.put("id",pm.getId());
                json.put("modelName",pm.getModelName());
                array.add(json);
            }
        }
        model.addObject("models",array);
        return model;
    }

    @RequestMapping(params = "getModelById")
    @ResponseBody
    public ApiParamModelEntity getModelById(ApiParamModelEntity apiParamModel){
        try {
            apiParamModel = apiParamModelService.getApiParamModel(apiParamModel);
        } catch (Exception e) {
           logger.error("获取模型数据失败：" + e.getMessage());
        }
        return apiParamModel;
    }

}
