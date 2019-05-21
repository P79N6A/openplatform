package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.api.service.OrderServiceI;
import com.kd.op.util.PublishStatus;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
import com.sgcc.hlht.entity.CMerchantInf;
import com.sgcc.hlht.service.OpenPlatformWebSupportService;
import com.sgcc.hlht.vo.MerchantWorkeyInfo;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sunbox.core.hsf.api.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 接口应用表
 * @date 2018-10-05 10:10:48
 */
@Api(value = "ApiApp", description = "接口应用表", tags = "apiAppController")
@Controller
@RequestMapping("/apiAppController")
public class ApiAppController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiAppController.class);

    @Autowired
    private ApiAppServiceI apiAppService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OpenPlatformWebSupportService openPlatformWebSupportService;


    /**
     * 应用配置
     *
     * @return
     */
    @RequestMapping(params = "apiConfigList")
    public ModelAndView configList() {
        return new ModelAndView("com/kd/op/app/apiConfigList");
    }

    /**
     * 应用注册
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "appRegiConfigList")
    public ModelAndView appRegiconfigList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/app/AppRegiConfigList");
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
    public void datagrid(ApiAppEntity apiApp, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
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
            String type = request.getParameter("type");
            if (type != null && type.equals("isv")) {
                TSUser user = ResourceUtil.getSessionUser();
                cq.eq("createBy", user.getUserName());
                cq.notEq("publishStatus", PublishStatus.FAILURE_PUBLISH.getValue());
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiAppService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除应用
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiAppEntity apiApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        apiApp = systemService.getEntity(ApiAppEntity.class, apiApp.getId());
        message = "应用表删除成功";
        try {
            apiAppService.delete(apiApp);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO, "1");
            systemService.addLog("应用[" + apiApp.getId() + "]删除成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "应用表删除失败";
            systemService.addLog("应用[" + apiApp.getId() + "]删除失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        try {
            if (apiApp.getAppType()!=null && apiApp.getAppType()==1) {
                openPlatformWebSupportService.deleteMerchantAndWorkeyByMerchantNo(apiApp.getOperator_id());
            }
        } catch (Exception e) {
            message = "应用表删除失败";
            systemService.addLog("互联互通应用[" + apiApp.getId() + "]删除失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加应用
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiAppEntity apiApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表添加成功";
        BigInteger count = apiAppService.nameCount(apiApp.getAppName());
        if (count.compareTo(new BigInteger("0")) == 1) {
            message = "添加失败：应用名称重复";
            j.setSuccess(false);
            j.setMsg(message);
            return j;
        }
        try {
            apiApp.setAuditStatus(Status.WORKING_AREA.getValue());
            apiApp.setPublishStatus(0);
            if (apiApp.getAppType() != null && apiApp.getAppType() == 1) {

            }
            apiAppService.save(apiApp);
            ApiAppKeysEntity apiAppKeys = apiAppService.getApiAppKeys(apiApp);
            //保存ApiAppKeysEntity
            apiAppService.save(apiAppKeys);
            systemService.addLog("应用[" + apiApp.getId() + "]注册成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力开放平台接口应用表添加失败";
            systemService.addLog("应用[" + apiApp.getId() + "]注册失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        // 待调试代码5.13
//        try {
//            if (apiApp.getAppType()!=null && apiApp.getAppType()==1) {
//                saveHlhtApp(apiApp, request);
//            }
//        } catch (Exception e) {
//            systemService.addLog("互联互通应用[" + apiApp.getId() + "]注册失败", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
//            message = "互联互通接口应用表添加失败";
//            logger.info("Exception:"+e);
//        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新应用
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiAppEntity apiApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表更新成功";
        ApiAppEntity t = apiAppService.get(ApiAppEntity.class, apiApp.getId());
        Integer appType = t.getAppType();
        String operatorId = t.getOperator_id();
//        待调试代码 5.13  根据operatorId获取merchantWorkeyInfo
//        MerchantWorkeyInfo merchantWorkeyInfo = openPlatformWebSupportService.queryMerchantAndWorkeyByMerchantNo(operatorId);
        try {
//            待调试代码 5.13
//            try {
            //互联互通应用更新为普通应用
//                if (appType==1 && apiApp.getAppType()==0) {
//                    openPlatformWebSupportService.deleteMerchantAndWorkeyByMerchantNo(operatorId);
//                }
            //普通应用更新为互联互通应用
//                if (appType == 0 && apiApp.getAppType() == 1) {
//                    saveHlhtApp(apiApp, request);
//                }
            //互联互通引用类型不变，其他内容跟新
//                if ((!(appType == 0 && apiApp.getAppType() == 1)) && apiApp.getAppType() != null && apiApp.getAppType() == 1) {
//                    String workeyFour = request.getParameter("workeyFour");
//                    String workeySix = request.getParameter("workeySix");
//                    String workeyEight = request.getParameter("workeyEight");
//                    String workeyTen = request.getParameter("workeyTen");
//                    if (operatorId != null) {
//                        if (merchantWorkeyInfo != null) {
//                            merchantWorkeyInfo.getMerchantInfo().setInnerMerchantNo(apiApp.getInnerMerchantno());
//                            merchantWorkeyInfo.setWorkeyFour(workeyFour);
//                            merchantWorkeyInfo.setWorkeySix(workeySix);
//                            merchantWorkeyInfo.setWorkeyEight(workeyEight);
//                            merchantWorkeyInfo.setWorkeyTen(workeyTen);
//                            openPlatformWebSupportService.saveOrUpdateMerchantAndWorKey(merchantWorkeyInfo);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                logger.error("error:", e);
//                message = "接口应用表更新失败";
//                systemService.addLog("应用[" + apiApp.getId() + "]互联互通同步失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
//                return j;
//            }
            MyBeanUtils.copyBeanNotNull2Bean(apiApp, t);
            t.setAuditStatus(Status.WORKING_AREA.getValue());
            if (apiApp.getAppType() != null && apiApp.getAppType() == 0) {
                ApiAppKeysEntity apiAppKeys = findApiAppKeysByAppId(apiApp.getId());
                String ivStr = request.getParameter("ivStr");
                String passKey = request.getParameter("passKey");
                apiAppKeys.setPassKey(passKey);
                apiAppKeys.setIvStr(ivStr);
                systemService.saveOrUpdate(apiAppKeys);
            }
            systemService.addLog("应用[" + apiApp.getId() + "]修改成功", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
            apiAppService.saveOrUpdate(t);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "接口应用表更新失败";
            systemService.addLog("应用[" + apiApp.getId() + "]修改失败", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 通过路径查询findApiAppKeys
     *
     * @param appId
     * @return http://localhost:8082/apiAppController.do?findApiAppKeys&appId=4028dc8168752f6d01687530daa90003
     */
    @RequestMapping(params = "findApiAppKeys")
    @ResponseBody
    public ApiAppKeysEntity findApiAppKeysByAppId(String appId) {
        return apiAppService.getApiAppKeysEntityByAppId(appId);
    }


    /**
     * 接口应用表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiAppEntity apiApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            req.setAttribute("apiApp", apiApp);
        }
        return new ModelAndView("com/kd/op/app/apiApp-add");
    }

    /**
     * 接口应用表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiAppEntity apiApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            Integer appType = apiApp.getAppType();
            req.setAttribute("apiApp", apiApp);
            if (appType != null && appType == 0) {
                ApiAppKeysEntity apiAppKeys = findApiAppKeysByAppId(apiApp.getId());
                String ivStr = apiAppKeys.getIvStr();
                String passKey = apiAppKeys.getPassKey();
                req.setAttribute("ivStr", ivStr);
                req.setAttribute("passKey", passKey);
            }
            try {
                if (appType != null && appType == 1) {
                    String operatorId = apiApp.getOperator_id();
                    MerchantWorkeyInfo merchantWorkeyInfo = openPlatformWebSupportService.queryMerchantAndWorkeyByMerchantNo(operatorId);
                    if (merchantWorkeyInfo != null) {
                        req.setAttribute("workeyOne", merchantWorkeyInfo.getWorkeyOne());
                        req.setAttribute("workeyTwo", merchantWorkeyInfo.getWorkeyTwo());
                        req.setAttribute("workeyThree", merchantWorkeyInfo.getWorkeyThree());
                        req.setAttribute("workeyFour", merchantWorkeyInfo.getWorkeyFour());
                        req.setAttribute("workeyFive", merchantWorkeyInfo.getWorkeyFive());
                        req.setAttribute("workeySix", merchantWorkeyInfo.getWorkeySix());
                        req.setAttribute("workeySeven", merchantWorkeyInfo.getWorkeySeven());
                        req.setAttribute("workeyEight", merchantWorkeyInfo.getWorkeyEight());
                        req.setAttribute("workeyNine", merchantWorkeyInfo.getWorkeyNine());
                        req.setAttribute("workeyTen", merchantWorkeyInfo.getWorkeyTen());
                    }
                }
            } catch (Exception e) {
                 logger.info("互联互通接口调用失败：" + e);
                 e.printStackTrace();
            }


        }
        return new ModelAndView("com/kd/op/app/apiApp-update");
    }

    /**
     * 接口应用表查看页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goLook")
    public ModelAndView goLook(ApiAppEntity apiApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiApp.getId())) {
            apiApp = apiAppService.getEntity(ApiAppEntity.class, apiApp.getId());
            req.setAttribute("apiApp", apiApp);
        }
        return new ModelAndView("com/kd/op/app/apiApp-Look");
    }


    @RequestMapping(params = "appAuditList")
    public ModelAndView auditList(ApiAppEntity apiApp, HttpServletRequest req) {
        return new ModelAndView("com/kd/op/app/appAuditList");
    }

    @RequestMapping(params = "getAppAuditList")
    public void getAppAuditList(ApiAppEntity apiApp, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiAppEntity.class, dataGrid);
        //查询条件组装器
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiApp.getAppName())) {
            String s = StringTransUtil.stringReplace(apiApp.getAppName());
            apiApp.setAppName("*" + s + "*");
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiApp, request.getParameterMap());
        try {
            cq.eq("auditStatus", Status.UN_AUDIT.getValue());
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiAppService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAuditApp")
    public ModelAndView goAuditApp(ApiAppEntity apiApp, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appAudit");
        ApiAppEntity app = systemService.getEntity(ApiAppEntity.class, apiApp.getId());
        model.addObject("app", app);
        return model;
    }

    @RequestMapping(params = "auditApp")
    @ResponseBody
    public AjaxJson auditApp(ApiAppEntity app) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "应用审核成功";
        ApiAppEntity old = systemService.getEntity(ApiAppEntity.class, app.getId());
        try {
            old.setAuditStatus(app.getAuditStatus());
            old.setAuditMsg(app.getAuditMsg());
            apiAppService.saveOrUpdate(old);
            systemService.addLog("应用[" + old.getId() + "]审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            message = "应用审核失败";
            systemService.addLog("应用[" + old.getId() + "]审核", Globals.MODULE_APP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAppOrderList")
    public ModelAndView goAppOrderList(ApiAppEntity apiApp, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appList");
        return model;
    }

    @RequestMapping(params = "appOrderList")
    public void appOrderList(ApiInfoEntity apiInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        try {
            String appId = request.getParameter("appId");
            if (appId != null && !appId.isEmpty()) {
                CriteriaQuery subCq = new CriteriaQuery(ApiAppRelaEntity.class);
                subCq.setProjection(Property.forName("apiId"));
                subCq.eq("appId", appId);
                subCq.add();
                cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
                cq.add();
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiAppService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goConfigCharging")
    public ModelAndView goConfigCharging(ApiInfoEntity apiInfo, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/app/configCharging");
        String selectedId = req.getParameter("selectedId");
        if (selectedId != null && !selectedId.isEmpty()) {
            model.addObject("ss", selectedId);
        }
        model.addObject("apiId", apiInfo.getId());
        return model;
    }

    @RequestMapping(params = "goSceneCharging")
    public ModelAndView goSceneCharging(ApiSceneEntity apiScene, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/scene/configSceneCharging");
        String selectedId = req.getParameter("selectedId");
        if (selectedId != null && !selectedId.isEmpty()) {
            model.addObject("ss", selectedId);
        }
        model.addObject("apiId", apiScene.getId());
        return model;
    }

    @RequestMapping(params = "configChargingList")
    public ModelAndView configChargingList(String type, String ss, String apiId) {
        ModelAndView model = new ModelAndView("com/kd/op/app/configChargingList");
        model.addObject("type", type);
        model.addObject("ss", ss);
        model.addObject("apiId", apiId);
        return model;
    }

    /**
     * 服务选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "apiInfoSelect")
    public ModelAndView apiInfoSelect(String appId, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/app/apiInfo_select");
        String orgId;
        TSUser currentUser = ResourceUtil.getSessionUserName();
        if (currentUser != null) {
            TSUserOrg userOrg = systemService.findByProperty(TSUserOrg.class, "tsUser.id", currentUser.getId()).get(0);
            TSDepart depart = userOrg.getTsDepart();
            orgId = depart.getId();
        }
        model.addObject("appId", appId);
        List<ApiAppRelaEntity> relas = systemService.findByProperty(ApiAppRelaEntity.class, "appId", appId);
        JSONArray apiChargeModes = new JSONArray();
        for (ApiAppRelaEntity rela : relas) {
            JSONObject obj = new JSONObject();
            apiChargeModes.add(obj);
        }
        model.addObject("apiChargeModes", apiChargeModes);
        return model;
    }

    @RequestMapping(params = "goApiAppRelaList")
    public ModelAndView goApiAppRelaList(ApiAppEntity apiApp, HttpServletRequest req) {
        req.setAttribute("appId", apiApp.getId());
        return new ModelAndView("com/kd/op/app/appApiRelaList");
    }

    @RequestMapping(params = "getRelaByApiAndApp")
    @ResponseBody
    public JSONObject getRelaByApiAndApp(String apiId, String appId) {
        JSONObject result = new JSONObject();
        result.put("success", true);
        try {
            String sql = "select charge_mode_id from api_app_rela where api_id='%s' and app_id='%s' ";
            sql = String.format(sql, apiId, appId);
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list != null && list.size() > 0) {
                ApiChargeMode mode = systemService.getEntity(ApiChargeMode.class, list.get(0).get("charge_mode_id") + "");
                result.put("mode", mode);
            }
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }


    @RequestMapping(params = "getApiAppSelect")
    @ResponseBody
    public JSONArray getApiAppSelect() {
        List<ApiAppEntity> apiAppEntities = apiAppService.loadAll(ApiAppEntity.class);
        JSONArray array = new JSONArray();
        apiAppEntities.forEach(p -> {
            JSONObject app = new JSONObject();
            app.put("id", p.getId());
            app.put("text", p.getAppName());
            array.add(app);
        });

        return array;
    }

    /**
     * @param apiApp
     * @param req
     * @return
     */
    @RequestMapping(params = "apiAppRelaUpdate")
    public ModelAndView appApiSubscribeAuditList(ApiAppEntity apiApp, HttpServletRequest req) {
        return new ModelAndView("com/kd/op/app/appApiSubscribeAuditList");
    }


    private void saveHlhtApp(ApiAppEntity apiApp, HttpServletRequest req) {
        MerchantWorkeyInfo merchantWorkeyInfo = new MerchantWorkeyInfo();
        CMerchantInf cMerchantInf = new CMerchantInf();
        cMerchantInf.setInnerMerchantNo(apiApp.getInnerMerchantno());
        cMerchantInf.setMerchantNo(apiApp.getOperator_id());
        cMerchantInf.setAppId(apiApp.getId());
        merchantWorkeyInfo.setMerchantInfo(cMerchantInf);
        merchantWorkeyInfo.setWorkeyThree(openPlatformWebSupportService.generateSingleWorkey());
        merchantWorkeyInfo.setWorkeyFour(req.getParameter("workeyFour"));
        merchantWorkeyInfo.setWorkeyFive(openPlatformWebSupportService.generateSingleWorkey());
        merchantWorkeyInfo.setWorkeySix(req.getParameter("workeySix"));
        merchantWorkeyInfo.setWorkeySeven(openPlatformWebSupportService.generateSingleWorkey());
        merchantWorkeyInfo.setWorkeyEight(req.getParameter("workeyEight"));
        merchantWorkeyInfo.setWorkeyNine(openPlatformWebSupportService.generateSingleWorkey());
        merchantWorkeyInfo.setWorkeyTen(req.getParameter("workeyTen"));
        openPlatformWebSupportService.saveOrUpdateMerchantAndWorKey(merchantWorkeyInfo);
    }
}