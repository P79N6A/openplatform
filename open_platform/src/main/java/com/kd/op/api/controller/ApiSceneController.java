package com.kd.op.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.*;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.StringTransUtil;
import io.swagger.annotations.Api;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Api(value="ApiScene",description="接口应用场景表",tags="apiSceneController")
@Controller
@RequestMapping("/apiSceneController")
public class ApiSceneController extends BaseController {

    private static final Logger logger = Logger.getLogger(ApiInfoController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiSceneServiceI apiSceneService;
    @Autowired
    private ApiInfoServiceI apiInfoService;
    @Autowired
    private ApiSceneChargeServiceI apiSceneChargeService;
    @Autowired
    private ApiSceneResourceServiceI apiSceneResourceService;
    @Autowired
    private ApiSceneRelaServiceI apiSceneRelaService;
    @Autowired
    private OrderServiceI orderService;

    /**
     * 接口应用场景表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(String listType,HttpServletRequest request) {
        String url = "";
        ModelAndView mv = new ModelAndView();
        if(CustomConstant.LIST_LIST.equals(listType)){
            url = "com/kd/op/scene/apiSceneList";
        }else if(CustomConstant.LIST_RECORD.equals(listType)){
            url = "";
        }else if(CustomConstant.LIST_AUDIT.equals(listType)){
            url = "com/kd/op/scene/apiSceneAuditList";
        }
        mv.setViewName(url);
        return mv;
    }

    /**
     * easyui AJAX请求业务场景数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiSceneEntity apiScene, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiSceneEntity.class, dataGrid);
        //修改为模糊查询
        if(StringUtil.isNotEmpty(apiScene.getSceneName())) {
            String s = StringTransUtil.stringReplace(apiScene.getSceneName());
            apiScene.setSceneName("*"+s+"*");
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiScene, request.getParameterMap());
        //根据当前登录人过滤 管理员查看全部预留
        //cq.eq("createBy",ResourceUtil.getSessionUserName().getUserName());
        cq.add();
        this.apiSceneService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridSceneApiList")
    public void datagridSceneApiList(ApiInfoEntity apiInfo, String sceneId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        try {
            //获取当前场景的服务列表
            String[] idList = orderService.getSceneApiIds(sceneId);
            cq.in("id", idList);
        } catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 加载符合条件的服务
     *
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridApiInfo")
    public void datagrid(ApiInfoEntity apiInfo,HttpServletResponse response, DataGrid dataGrid) {
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiInfo.getApiName())) {
            String s = StringTransUtil.stringReplace(apiInfo.getApiName());
            apiInfo.setApiName("*" + s + "*");
        }
        //场景只加载ISV被动调用能力
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        cq.add(Restrictions.or(Restrictions.isNull("apiInvokeType"),
                Restrictions.eq("apiInvokeType",1)));
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }



    @RequestMapping(params="configChargingList")
    public ModelAndView configChargingList(String type,String ss,String apiId){
        ModelAndView model = new ModelAndView("com/kd/op/scene/configSceneChargingList");
        model.addObject("type",type);
        model.addObject("ss",ss);
        model.addObject("apiId",apiId);
        return model;
    }

    /**
     * 删除场景
     *
     * @param apiScene
     * @param request
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiSceneEntity apiScene, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        apiScene = systemService.getEntity(ApiSceneEntity.class, apiScene.getId());
        List<ApiSceneChargeEntity> apiSceneCharges = systemService.findByProperty(ApiSceneChargeEntity.class, "sceneId", apiScene.getId());
        List<ApiSceneResourceEntity> apiSceneResources = systemService.findByProperty(ApiSceneResourceEntity.class, "sceneId", apiScene.getId());
        List<ApiSceneRelaEntity> apiSceneRelas = systemService.findByProperty(ApiSceneRelaEntity.class, "sceneId", apiScene.getId());
        message = "场景删除成功";
        try{
            systemService.deleteAllEntitie(apiSceneCharges);
            systemService.deleteAllEntitie(apiSceneResources);
            systemService.deleteAllEntitie(apiSceneRelas);
            apiSceneService.delete(apiScene);
            systemService.addLog("删除场景["+apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch(Exception e){
            logger.error("error:",e);
            message = "场景删除失败";
            systemService.addLog("删除场景["+apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加场景
     *
     * @param sceneTotal
     * @param request
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public JSONObject doAdd(ApiSceneTotalEntity sceneTotal,  HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
        String message = "添加成功";
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","服务场景添加成功！");

        ApiSceneEntity apiScene = sceneTotal.getApiScene();

        //场景添加查重
        BigInteger count = apiSceneService.nameCount(apiScene.getSceneName());
        if (count.compareTo(new BigInteger("0")) == 1) {
            result.put("success",false);
            result.put("msg","添加失败：场景名称重复");
//            return result;
        }
            try {
                apiScene.setSceneAuditStatus(CustomConstant.AUDIT_TEMP);
                apiSceneService.save(apiScene);
                apiSceneChargeService.addApiSceneCharge(sceneTotal);
                apiSceneResourceService.addApiSceneResource(sceneTotal);
                apiSceneRelaService.addApiSceneRela(sceneTotal);
                systemService.addLog("添加场景["+apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO, Globals.SUCCESS);
            } catch (Exception e) {
                result.put("success", false);
                result.put("msg","服务场景信息添加失败");
                systemService.addLog("添加场景["+apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR,Globals.FAILD);
            }

        return result;
    }

    /**
     * 更新接口信息表
     *
     * @param sceneTotal
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public JSONObject doUpdate(ApiSceneTotalEntity sceneTotal,HttpServletRequest request) {

        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","场景更新成功！");

        ApiSceneEntity apiScene = sceneTotal.getApiScene();

        try {
            apiScene.setSceneStatus(0);
            apiSceneService.update(apiScene);
            apiSceneChargeService.updateApiSceneCharge(sceneTotal);
            apiSceneResourceService.updateApiSceneResource(sceneTotal);
            apiSceneRelaService.updateApiSceneRela(sceneTotal);
            systemService.addLog("更新场景[" + apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:",e);
            result.put("success",false);
            result.put("msg","场景更新失败！");
            systemService.addLog("更新场景[" + apiScene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        return result;
    }


    /**
     * 接口应用场景表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiSceneEntity apiScene, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiScene.getId())) {
            apiScene = apiSceneService.getEntity(ApiSceneEntity.class, apiScene.getId());
            req.setAttribute("apiScene", apiScene);
        }
        return new ModelAndView("com/kd/op/scene/apiScene-add");
    }
    /**
     * 接口应用表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiSceneEntity apiScene, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiScene.getId())) {
            apiScene = apiSceneService.getEntity(ApiSceneEntity.class, apiScene.getId());
            req.setAttribute("apiScene", apiScene);

            String sceneChargeIds = "";

            List<ApiSceneChargeEntity> charges = systemService.findByProperty(ApiSceneChargeEntity.class, "sceneId", apiScene.getId());
            StringBuffer sb = new StringBuffer();
            for(ApiSceneChargeEntity charge:charges){
                sb.append(charge.getChargeId() + ",");
            }
            sceneChargeIds =sb.toString();
            if(!sceneChargeIds.isEmpty()){
                sceneChargeIds = sceneChargeIds.substring(0,sceneChargeIds.length() - 1);
            }
            req.setAttribute("sceneChargeIds",sceneChargeIds);
            String apiInfoIds = "";
            sb = new StringBuffer();
            List<ApiSceneRelaEntity> infos = systemService.findByProperty(ApiSceneRelaEntity.class, "sceneId", apiScene.getId());
            for(ApiSceneRelaEntity info:infos){
                sb.append(info.getApiId() + ",");
            }
            apiInfoIds = sb.toString();
            if(!apiInfoIds.isEmpty()){
                apiInfoIds = apiInfoIds.substring(0,apiInfoIds.length() - 1);
            }
            req.setAttribute("apiInfoIds",apiInfoIds);

        }
        return new ModelAndView("com/kd/op/scene/apiScene-update");
    }


    /**
     * 景表查看页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goLook")
    public ModelAndView goLook(ApiSceneEntity apiScene, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiScene.getId())) {
            apiScene = apiSceneService.getEntity(ApiSceneEntity.class, apiScene.getId());
            req.setAttribute("apiScene", apiScene);
        }
        return new ModelAndView("com/kd/op/scene/apiScene-Look");
    }


    /**
     * 开启审核页面
     *
     * @param apiScene
     * @param req
     * @return
     */
    @RequestMapping(params = "goAuditScene")
    public ModelAndView goAuditScene(ApiSceneEntity apiScene, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/scene/apiSceneAudit");
        ApiSceneEntity scene = systemService.getEntity(ApiSceneEntity.class, apiScene.getId());
        model.addObject("scene",scene);
        return model;
    }

    /**
     * 审核场景
     *
     * @param scene
     * @return
     */
    @RequestMapping(params="auditScene")
    @ResponseBody
    public AjaxJson auditScene(ApiSceneEntity scene){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "场景审核成功";
        try {
            ApiSceneEntity old = systemService.getEntity(ApiSceneEntity.class,scene.getId());
            old.setSceneAuditStatus(scene.getSceneAuditStatus());
            old.setSceneAuditMsg(scene.getSceneAuditMsg());
            apiSceneService.saveOrUpdate(old);
            systemService.addLog("审核场景[" + scene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        }catch (Exception e){
            message = "场景审核失败";
            systemService.addLog("审核场景[" + scene.getId()+"]", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR ,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 提交场景审核
     *
     * @param apiScene
     * @param request
     * @return
     */
    @RequestMapping(params = "submitSceneAudit")
    @ResponseBody
    public AjaxJson submitSceneAudit(ApiSceneEntity apiScene, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try{
            ApiSceneEntity apiSceneOld = apiSceneService.get(ApiSceneEntity.class, apiScene.getId());
            apiSceneOld.setSceneAuditStatus(1);
            apiSceneService.saveOrUpdate(apiSceneOld);
            systemService.addLog("场景[" + apiScene.getId() + " ]提交审核", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch(Exception e){
            logger.error("error:",e);
            message = "提交审核失败";
            systemService.addLog("场景[" + apiScene.getId() + " ]提交审核", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新可用状态
     *
     * @return
     */
    @RequestMapping(params = "changeStatus")
    @ResponseBody
    public AjaxJson changeStatus(ApiSceneEntity apiScene, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "可用状态修改成功";
        try {
            ApiSceneEntity apiSceneOld = apiSceneService.get(ApiSceneEntity.class, apiScene.getId());
            apiSceneOld.setSceneStatus(apiScene.getSceneStatus());
            apiSceneService.saveOrUpdate(apiSceneOld);
            systemService.addLog("场景["+apiScene.getSceneName() + "}修改可用状态", Globals.MODULE_SCENE, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "可用状态修改失败";
            systemService.addLog("场景["+apiScene.getSceneName() + "}修改可用状态", Globals.MODULE_SCENE, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

}
