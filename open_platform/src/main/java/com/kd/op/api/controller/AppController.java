package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.util.PublishStatus;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用发布
 */
@Controller
@RequestMapping("/appController")
public class AppController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Resource
    private SystemService systemService;
    @Autowired
    private ApiAppServiceI apiAppService;

    //跳转应用发布页面
    @RequestMapping(params = "appPublishList")
    public ModelAndView appPublishList(){
        return new ModelAndView("com/kd/op/app/appPublishList");
    }


    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    //查看已发布应用的审核列表
    @RequestMapping(params = "aduitDatagrid")
    @ResponseBody
    public void aduitDatagrid(ApiAppEntity apiApp, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiAppEntity.class, dataGrid);
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiApp.getAppName())) {
            String s = StringTransUtil.stringReplace(apiApp.getAppName());
            apiApp.setAppName("*" + s + "*");
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiApp, request.getParameterMap());
        try {
            //自定义追加查询条件

                TSUser user = ResourceUtil.getSessionUser();
                cq.eq("createBy", user.getUserName());
                cq.eq("auditStatus", Status.UN_AUDIT.getValue());
                cq.eq("publishStatus",PublishStatus.SUCCESS_PUBLISH.getValue());

        } catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiAppService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    //查看应用发布列表
    @RequestMapping(params = "datagrid")
    @ResponseBody
    public void datagrid(ApiAppEntity apiApp, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiAppEntity.class, dataGrid);
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiApp.getAppName())) {
            String s = StringTransUtil.stringReplace(apiApp.getAppName());
            apiApp.setAppName("*" + s + "*");
        }
        TSUser user = ResourceUtil.getSessionUser();
        try{
        //自定义追加查询条件
        //只显示当前用户下已经发布的应用
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiApp, request.getParameterMap());
        if(StringUtil.isNotEmpty(user.getUserName())){
            List<String> appIds = new ArrayList<>();
            String hql0 = "from ApiAppConnect where 1 = 1 AND auditStatus = ? order by createTime asc";
            List<ApiAppConnect> apiAppConnectList = systemService.findHql(hql0, Status.SUCCESS_AUDIT.getValue());
            for (ApiAppConnect apiAppConnect:apiAppConnectList){
               appIds.add(apiAppConnect.getAppId());
            }

            cq.in("id", appIds.toArray());
        }
        cq.eq("createBy", user.getUserName());
        cq.notEq("publishStatus", PublishStatus.FAILURE_PUBLISH.getValue());
        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiAppService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 更新发布应用状态
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiAppEntity apiApp, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        apiApp = systemService.getEntity(ApiAppEntity.class, apiApp.getId());
        String message = "应用作废成功";
        try {
            ApiAppEntity apiAppOld = apiAppService.get(ApiAppEntity.class, apiApp.getId());
            apiAppOld.setPublishStatus(PublishStatus.FAILURE_PUBLISH.getValue());
            apiAppService.saveOrUpdate(apiAppOld);
            systemService.addLog("应用[" + apiApp.getId() +"]作废成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:",e);
            systemService.addLog("应用[" + apiApp.getId() +"]作废失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
            message = "应用作废失败";
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 只更新发布应用状态
     *
     * @return
     */
    @RequestMapping(params = "publishApp")
    @ResponseBody
    public JSONObject publishApp(ApiAppEntity apiApp, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "发布成功";
        ApiAppEntity apiAppOld = apiAppService.get(ApiAppEntity.class, apiApp.getId());
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "应用发布成功！");
        try {
            String hql0 = "from ApiAppChargeEntity where 1 = 1 AND app_id = ? order by createTime asc";
            List<ApiAppChargeEntity> apiAppChargeEntityList = systemService.findHql(hql0, apiAppOld.getId());
            if(apiAppChargeEntityList.size() == 0){
                result.put("success", false);
                result.put("msg", "请先配置计费模型!");
            }else {
                apiAppOld.setPublishStatus(PublishStatus.SUCCESS_PUBLISH.getValue());
                apiAppOld.setAuditStatus(Status.UN_AUDIT.getValue());
                apiAppService.saveOrUpdate(apiAppOld);
                systemService.addLog("应用[ " + apiApp.getId() + " ]发布成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("error:",e);
            systemService.addLog("应用[" + apiApp.getId() +"]作废失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
            result.put("success", false);
            result.put("msg", "应用发布失败！");
        }
        j.setMsg(message);
        return result;
    }

    /**
     * 跳转应用发布审核页面
     * @return
     */
    @RequestMapping(params = "auditList")
    public ModelAndView auditList() {
        ModelAndView model = new ModelAndView("com/kd/op/app/appPublishAuditList");
        return model;
    }

    /**
     * 应用信息表查看页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAppInfo")
    public ModelAndView goAppInfo(ApiAppEntity apiApp, Integer type, HttpServletRequest req) {
        ModelAndView model = new ModelAndView();
        if (type == 2) {
            //以只读方式打开
            model = new ModelAndView("com/kd/op/app/appInfo-detail");
        }
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            model.addObject("apiInfoPage", apiApp);
        }
        return model;
    }

    /**
     * 应用信息表配置计费模型页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAppChargeModel")
    public ModelAndView goAppChargeModel(ApiAppEntity apiApp, HttpServletRequest req) {
        ModelAndView model = new ModelAndView();
        model = new ModelAndView("com/kd/op/app/appInfo-add");
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            req.setAttribute("apiInfoPage", apiApp);
            List<ApiAppChargeEntity> appChargeEntity = systemService.findByProperty(ApiAppChargeEntity.class, "appId", apiApp.getId());
            StringBuffer sb = new StringBuffer();
            String chargeModeIds = "";
            for (ApiAppChargeEntity appChargeModel : appChargeEntity){
                sb.append(appChargeModel.getChargeId() + ",");
            }
            chargeModeIds = sb.toString();
            if (!chargeModeIds.isEmpty()) {
                chargeModeIds = chargeModeIds.substring(0, chargeModeIds.length() - 1);
            }
            model.addObject("chargeModeIds", chargeModeIds);
        }
        return model;
    }


    /**
     * 配置/更新应用计费模型
     *
     * @return
     */
    @RequestMapping(params = "doAppChargeModel")
    @ResponseBody
    public JSONObject doAppChargeModel(ApiAppEntity apiApp,String chargeModeIds, HttpServletRequest request) {
        String[] chargeIds = chargeModeIds.split(",");
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "应用计费模型更新成功！");
        try{
            if (StringUtil.isNotEmpty(apiApp.getId())) {
                apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            }
            List<ApiAppChargeEntity> AppChargeEntity = systemService.findByProperty(ApiAppChargeEntity.class, "appId", apiApp.getId());
            if (AppChargeEntity != null && AppChargeEntity.size() > 0) {
                for (ApiAppChargeEntity AppChargeEntitys : AppChargeEntity) {
                    String id = AppChargeEntitys.getId();
                    apiAppService.deleteEntityById(ApiAppChargeEntity.class,id);
                }
            }

            if(chargeIds != null && chargeIds.length>0){
                 for (int i=0;i<chargeIds.length;i++){

                     ApiAppChargeEntity apiAppCharge = new ApiAppChargeEntity();
                     apiAppCharge.setAppId(apiApp.getId());
                     apiAppCharge.setAppName(apiApp.getAppName());
                     apiAppCharge.setChargeId(chargeIds[i]);
                     apiAppService.save(apiAppCharge);
                     systemService.addLog("应用[ " + apiApp.getId() + " ]更新计费模型成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
                 }
            }
        } catch (Exception e) {
            logger.error("error:",e);
            systemService.addLog("应用[ " + apiApp.getId() + " ]更新计费模型失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
            result.put("success", false);
            result.put("msg", "应用计费模型更新失败！");
        }
        return result;
    }



    /**
     * 跳转应用审核页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goAppInfoAudit")
    public ModelAndView goAppInfoAudit(ApiAppEntity apiApp, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            request.setAttribute("apiInfoPage", apiApp);
        }
        return new ModelAndView("com/kd/op/app/appInfoAudit");
    }


    /**
     * 更新审核状态
     *
     * @return
     */
    @RequestMapping(params = "doAppInfoAudit")
    @ResponseBody
    public AjaxJson doAppInfoAudit(ApiAppEntity apiApp, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "审核成功";
        try {
            ApiAppEntity apiAppOld = apiAppService.get(ApiAppEntity.class, apiApp.getId());
            MyBeanUtils.copyBeanNotNull2Bean(apiApp, apiAppOld);
            if (apiAppOld.getAuditStatus() == Status.SUCCESS_AUDIT.getValue()) {
                apiAppOld.setPublishStatus(PublishStatus.SUCCESS_PUBLISH.getValue());
            }
            apiAppService.saveOrUpdate(apiAppOld);
            systemService.addLog("应用[" + apiApp.getId() + "]审核状态更新成功" , Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            systemService.addLog("应用[" + apiApp.getId() + "]审核状态更新失败" , Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
            message = "审核失败";
        }
        j.setMsg(message);
        return j;
    }
}
