package com.kd.op.api.controller;

import com.kd.op.api.entity.ApiAppEntity;
import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.ApiInvokeLogEntity;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.service.ApiInvokeLogServiceI;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kd.op.common.CustomConstant;
import com.kd.op.util.ResourceParams;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import io.swagger.annotations.Api;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 接口调用日志
 * @date 2018-10-03 12:24:57
 */
@Api(value = "ApiInvokeLog", description = "接口调用日志", tags = "apiInvokeLogController")
@Controller
@RequestMapping("/apiInvokeLogController")
public class ApiInvokeLogController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiInvokeLogController.class);

    @Autowired
    private ApiInvokeLogServiceI apiInvokeLogService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiInfoServiceI apiInfoService;



    /**
     * 接口调用日志列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/api/apiInvokeLogList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInvokeLogEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInvokeLog, request.getParameterMap());
        try {
            //自定义追加查询条件
            String query_invokeTime_begin = request.getParameter("invokeTime_begin");
            String query_invokeTime_end = request.getParameter("invokeTime_end");
            if (StringUtil.isNotEmpty(query_invokeTime_begin)) {
                cq.ge("invokeTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_invokeTime_begin));
            }
            if (StringUtil.isNotEmpty(query_invokeTime_end)) {
                cq.le("invokeTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_invokeTime_end));
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getAppId())) {
                cq.eq("appId", apiInvokeLog.getAppId());
            }
        } catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiInvokeLogService.getDataGridReturn(cq, true);
        if(dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            if(dataGrid.getResults() != null){
                for(int i = 0;i < dataGrid.getResults().size();i++){
                    ApiInvokeLogEntity log = (ApiInvokeLogEntity) dataGrid.getResults().get(i);
                    if(log.getApiId() != null){
                        ApiInfoEntity apiInfo = systemService.getEntity(ApiInfoEntity.class,log.getApiId());
                        if(apiInfo != null){
                            log.setApiName(apiInfo.getApiName());
                        }
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "invokeDatagrid")
    public void invokeDatagrid(String groupId,ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInvokeLogEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInvokeLog, request.getParameterMap());
        try {
            //自定义追加查询条件
            String query_invokeTime_begin = request.getParameter("invokeTime_begin");
            String query_invokeTime_end =request.getParameter("invokeTime_end");
//            String[] endArr = request.getParameter("invokeTime_end").split(" ");
//            String[] hourArr = endArr[1].split(":");
//            String query_invokeTime_end = endArr[0]+" "+hourArr[0]+":59"+":59";
            if (StringUtil.isNotEmpty(groupId)) {
                List<ApiInfoEntity> list = apiInfoService.getApiInfoByGroupId(groupId);
                ArrayList<String> groupIds = new ArrayList<>();
                for (ApiInfoEntity apiInfoEntity : list) {
                    groupIds.add(apiInfoEntity.getId());
                }
                cq.in("apiId",groupIds.toArray());
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getAppId())) {
                cq.eq("appId",apiInvokeLog.getAppId());
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getSceneId())) {
                cq.eq("sceneId",apiInvokeLog.getSceneId());
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getApiId())) {
                cq.eq("apiId",apiInvokeLog.getApiId());
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getInvokeServiceproviderResult())){
                if (apiInvokeLog.getInvokeServiceproviderResult() == 0) {
                    cq.eq("invokeServiceproviderResult", apiInvokeLog.getInvokeServiceproviderResult());
                } else{
                    cq.notEq("invokeServiceproviderResult", 0);
                }
            }
            if (StringUtil.isNotEmpty(query_invokeTime_begin)) {
                cq.ge("invokeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_invokeTime_begin));
            }
            if (StringUtil.isNotEmpty(query_invokeTime_end)) {
                cq.le("invokeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_invokeTime_end));
            }
            if (StringUtil.isNotEmpty(apiInvokeLog.getAppId())) {
                cq.eq("appId", apiInvokeLog.getAppId());
            }
        } catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiInvokeLogService.getDataGridReturn(cq, true);
        if(dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            if(dataGrid.getResults() != null){
                for(int i = 0;i < dataGrid.getResults().size();i++){
                    ApiInvokeLogEntity log = (ApiInvokeLogEntity) dataGrid.getResults().get(i);
                    if(log.getApiId() != null){
                        ApiInfoEntity apiInfo = systemService.getEntity(ApiInfoEntity.class,log.getApiId());
                        if(apiInfo != null){
                            log.setApiName(apiInfo.getApiName());
                        }
                    }
                    if(log.getAppId() != null){
                        ApiAppEntity apiApp = systemService.getEntity(ApiAppEntity.class,log.getAppId());
                        if(apiApp != null){
                            log.setAppName(apiApp.getAppName());
                        }
                    }
                    if(log.getSceneId() != null){
                        ApiSceneEntity apiScene = systemService.getEntity(ApiSceneEntity.class,log.getSceneId());
                        if(apiScene != null){
                            log.setSceneName(apiScene.getSceneName());
                        }
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除接口调用日志
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        apiInvokeLog = systemService.getEntity(ApiInvokeLogEntity.class, apiInvokeLog.getId());
        message = "接口调用日志"+apiInvokeLog.getId()+"删除成功";
        try {
            apiInvokeLogService.delete(apiInvokeLog);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO, "1");
        } catch (Exception e) {
            logger.error("error:",e);
           /* message = "接口调用日志"+apiInvokeLog.getId()+"删除失败";*/
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除接口调用日志
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口调用日志"+ids+"删除成功";
        try {
            for (String id : ids.split(",")) {
                ApiInvokeLogEntity apiInvokeLog = systemService.getEntity(ApiInvokeLogEntity.class,
                        id
                );
                apiInvokeLogService.delete(apiInvokeLog);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO, "1");
            }
        } catch (Exception e) {
            logger.error("error:",e);
            message = "接口调用日志"+ids+"删除失败";
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 添加接口调用日志
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口调用日志"+apiInvokeLog.getId()+"添加成功";
        try {
            apiInvokeLogService.save(apiInvokeLog);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO, "1");
        } catch (Exception e) {
            logger.error("error:",e);
            message = "接口调用日志"+apiInvokeLog.getId()+"添加失败";
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 更新接口调用日志
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口调用日志"+apiInvokeLog.getId()+"更新成功";
        ApiInvokeLogEntity t = apiInvokeLogService.get(ApiInvokeLogEntity.class, apiInvokeLog.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(apiInvokeLog, t);
            apiInvokeLogService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO, "1");
        } catch (Exception e) {
            logger.error("error:",e);
            message = "接口调用日志"+apiInvokeLog.getId()+"更新失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 接口调用日志新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiInvokeLog.getId())) {
            apiInvokeLog = apiInvokeLogService.getEntity(ApiInvokeLogEntity.class, apiInvokeLog.getId());
            req.setAttribute("apiInvokeLog", apiInvokeLog);
        }
        return new ModelAndView("com/kd/op/api/apiInvokeLog-add");
    }

    /**
     * 接口调用日志编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiInvokeLog.getId())) {
            apiInvokeLog = apiInvokeLogService.getEntity(ApiInvokeLogEntity.class, apiInvokeLog.getId());
            req.setAttribute("apiInvokeLog", apiInvokeLog);
            if(apiInvokeLog.getApiId() != null){
                ApiInfoEntity apiInfo = systemService.getEntity(ApiInfoEntity.class,apiInvokeLog.getApiId());
                if(apiInfo != null){
                    apiInvokeLog.setApiName(apiInfo.getApiName());
                }
            }
            if(apiInvokeLog.getAppId() != null){
                ApiAppEntity apiApp = systemService.getEntity(ApiAppEntity.class,apiInvokeLog.getAppId());
                if(apiApp != null){
                    apiInvokeLog.setApiName(apiApp.getAppName());
                }
            }
            if(apiInvokeLog.getSceneId() != null){
                ApiSceneEntity apiScene = systemService.getEntity(ApiSceneEntity.class,apiInvokeLog.getSceneId());
                if(apiScene != null){
                    apiInvokeLog.setSceneName(apiScene.getSceneName());
                }
            }
        }
        return new ModelAndView("com/kd/op/api/apiInvokeLog-update");
    }

}
