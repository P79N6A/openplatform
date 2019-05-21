package com.kd.op.api.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.Status;
//import com.sun.deploy.panel.ITreeNode;
import com.kd.op.util.StringTransUtil;
import com.kd.openplatform.activeAPI.OpenplatformAPI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.kd.op.api.page.ApiInfoPage;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.entity.ApiInfoEntity;
import io.swagger.annotations.Api;


/**
 * @author 张健云
 * @version V1.0
 * @Title: Controller
 * @Description: 接口信息表
 * @date 2018-09-30 16:43:26
 */
@Api(value = "ApiInfo", description = "能力信息表", tags = "apiInfoController")
@Controller
@RequestMapping("/apiInfoController")
public class ApiInfoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiInfoController.class);

    @Autowired
    private ApiInfoServiceI apiInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private OpenplatformAPI openplatformAPI;

    /**
     * 能力注册列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/api/apiInfoList");
    }


    /**
     * 能力测试 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "test")
    public ModelAndView test(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/api/apiInfoTestList");
    }

    /**
     * easyui AJAX请求数据
     * 能力数据加载
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiInfoEntity apiInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiInfo.getApiName())) {
            String s = StringTransUtil.stringReplace(apiInfo.getApiName());
            apiInfo.setApiName("*" + s + "*");
        }
        if (StringUtil.isNotEmpty(apiInfo.getApiMethodName())) {
            String s = StringTransUtil.stringReplace(apiInfo.getApiMethodName());
            apiInfo.setApiMethodName("*" + s + "*");
        }
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        //已作废能力不显示
        cq.notEq("apiPublishStatus", -1);
        //根据审核员过滤
        String username = ResourceUtil.getSessionUserName().getUserName();
        TSBaseUser tsBaseUser = systemService.findUniqueByProperty(TSBaseUser.class, "userName", username);
        if(tsBaseUser!=null){
            //获取角色
            String userKey = tsBaseUser.getUserKey();
            //根据当前登录人过滤 管理员查看全部预留
            if(!userKey.equals("系统管理员") && !userKey.equals("开放平台审核员")){
                cq.eq("createBy",username);
            }
            if(userKey.equals("开放平台审核员")){
                cq.eq("auditBy",username);
            }
        }
        cq.add();
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * easyui AJAX请求数据
     * 能力测试数据加载
     */

    @RequestMapping(params = "testdatagrid")
    @ResponseBody
    public JSONArray testdatagrid(ApiInfoEntity apiInfo) {
        JSONArray array = new JSONArray();
        List<ApiParamEntity> params = apiInfoService.getByAPiAndType(apiInfo.getId(), 0);
        for (ApiParamEntity param : params) {
            JSONObject json = new JSONObject();
            json.put("id", param.getId());
            json.put("paramName", param.getParamName());
            json.put("parentId", param.getParentId());
            json.put("paramVisible", param.getparamVisible());
            json.put("paramEncrypt", param.getParamEncrypt());
            json.put("paramDesc", param.getParamDesc());
            json.put("dataType", param.getDataType());
            json.put("defaultValue", param.getDefaultValue());
            array.add(json);
        }
        return array;
    }

    /**
     * easyui AJAX请求数据
     * 测试列表 二级参数加载
     *
     * @param response
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "testsubdatagrid")
    public void testsubdatagrid(String id, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiParamEntity.class, dataGrid);
        //查询条件组装器
        ApiParamEntity apiParam = new ApiParamEntity();
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiParam);
        try {
            //自定义追加查询条件
            //String apiParamID = apiParam.getId();       //获取父表中当前行的id
            cq.eq("parentId", id);
            cq.notEq("paramType", 1);

        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.commonService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 能力作废
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        apiInfo = systemService.getEntity(ApiInfoEntity.class, apiInfo.getId());
        String message = "能力作废成功";
        try {
            //判断能力是否能够作废
            if (apiInfo.getApiAuditStatus() == Status.UN_AUDIT.getValue()) {
                j.setSuccess(false);
                j.setMsg("当前能力不允许作废！");
                return j;
            } else if (apiInfo.getApiAuditStatus() == Status.SUCCESS_AUDIT.getValue()) {
                if (apiInfo.getApiStatus() == 1 || apiInfo.getApiVisibleStatus() == 1) {
                    j.setSuccess(false);
                    j.setMsg("当前能力不允许作废！");
                    return j;
                }
            }
            apiInfo.setApiPublishStatus(-1);
            apiInfoService.updateEntitie(apiInfo);
            systemService.addLog("能力[" + apiInfo.getId() + "]作废", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力作废失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]作废", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加能力
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public JSONObject doAdd(ApiInfoTotalEntity infoTotal, ApiInfoPage apiInfoPage, HttpServletRequest request) {
        String headers = request.getParameter("headers");
        String requests = request.getParameter("requests");
        String returns = request.getParameter("returns");
//		AjaxJson j = new AjaxJson();
        String message = "添加成功";
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "能力添加成功！");


        ApiInfoEntity apiInfo = infoTotal.getApiInfo();
        String apiNameCheck = apiInfo.getApiName();
        if(apiInfo.getApiName()!=null && apiInfo.getApiName()!=""){
            ApiInfoEntity apiNameTemp = systemService.findUniqueByProperty(ApiInfoEntity.class, "apiName", apiNameCheck);
            if(apiNameTemp!=null){
                result.put("success", false);
                result.put("msg", "您输入的能力名称已经存在!");
                return result;
            }
        }

        if (apiInfo.getApiName() == null) {
            result.put("success", false);
            result.put("msg", "请输入基本信息中星号部分内容!");
            return result;
        }
        //如果是被动能力
        if (apiInfo.getApiInvokeType() == 1 ) {
            if ( apiInfo.getPubMode() == null || apiInfo.getApiClassName() == null || apiInfo.getVersion() == null
                    || apiInfo.getApiMethodName() == null || apiInfo.getPubMode() == null) {
                result.put("success", false);
                result.put("msg", "请输入基本信息中星号部分内容!");
                return result;
            }
            String reqAddHttp = apiInfo.getReqAddrHttp();
            String reqAddHsf = apiInfo.getReqAddrHsf();
            //拿到地址去查，如果没有默认填写，如果有就提示地址已经有了
            if(StringUtil.isNotEmpty(apiInfo.getReqAddrHttp())){
                ApiInfoEntity apiInfoHttp = systemService.findUniqueByProperty(ApiInfoEntity.class, "reqAddrHttp", reqAddHttp);
                if(apiInfo.getPubMode().equals("0") && apiInfoHttp!=null){
                    result.put("success", false);
                    result.put("msg", "您输入的http地址已经存在!");
                    return result;
                }
                apiInfo.setReqAddrHttp(reqAddHttp);
            }
            if(StringUtil.isNotEmpty(apiInfo.getReqAddrHsf())){
                ApiInfoEntity apiInfoHsf = systemService.findUniqueByProperty(ApiInfoEntity.class, "reqAddrHsf", reqAddHsf);
                if(apiInfoHsf!=null){
                    result.put("success", false);
                    if (apiInfo.getPubMode().equals("1")){
                        result.put("msg", "您输入的hsf请求地址已经存在!");
                    }
                    if (apiInfo.getPubMode().equals("2")){
                        result.put("msg", "您输入的webservice标识已经存在!");
                    }
                    return result;
                }
                apiInfo.setReqAddrHsf(reqAddHsf);
            }
            if(StringUtil.isNotEmpty(apiInfo.getReqAddrHsf())  && StringUtil.isNotEmpty(apiInfo.getReqAddrHttp())){
                apiInfo.setReqAddrHttp(reqAddHttp);
                apiInfo.setReqAddrHsf(reqAddHsf);
            }
            if(StringUtil.isEmpty(apiInfo.getReqAddrHsf())){
                if (apiInfo.getPubMode().equals("1")) {
                    apiInfo = apiInfoService.generateAddr(apiInfo);
                }
                if (apiInfo.getPubMode().equals("2")) {
                    apiInfo = apiInfoService.generateAddrWs(apiInfo);
                }

            }
            //hsf能力
            if ( apiInfo.getPubMode().equals("1")) {
                if (apiInfo.getGroupName() == null || apiInfo.getHsfGroup() == null) {
                    result.put("success", false);
                    result.put("msg", "请输入基本信息中星号部分内容!");
                    return result;
                }
                //根据类名称自动生成HTTP、HSF请求地址(被动能力)
//                apiInfo = apiInfoService.generateAddr(apiInfo);
                //webservice能力
            } else if (apiInfo.getPubMode().equals("2")) {
                if (apiInfo.getReqAddrHttp() == null) {
                    result.put("success", false);
                    result.put("msg", "请输入基本信息中星号部分内容!");
                    return result;
                }
            }
        }
        try {
            apiInfoService.addApi(infoTotal, apiInfo, headers, requests, returns);
        } catch (Exception e) {
            logger.error("error:", e);
            result.put("success", false);
            result.put("msg", "能力信息添加失败");
            systemService.addLog("能力[" + apiInfo.getApiName() + "]添加", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        try {
            systemService.addLog("能力[" + apiInfo.getApiName() + "]添加", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return result;
    }

    /**
     * 更新能力
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public JSONObject doUpdate(ApiInfoTotalEntity infoTotal, ApiInfoPage apiInfoPage, HttpServletRequest request) {
        String headers = request.getParameter("headers");
        String requests = request.getParameter("requests");
        String returns = request.getParameter("returns");
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "能力更新成功！");

        ApiInfoEntity apiInfo = infoTotal.getApiInfo();
        
        String apiNameCheck = apiInfo.getApiName();
        if(apiInfo.getApiName()!=null && apiInfo.getApiName()!=""){
            ApiInfoEntity apiNameTemp = systemService.findUniqueByProperty(ApiInfoEntity.class, "apiName", apiNameCheck);
            if(apiNameTemp!=null && !apiInfo.getId().equals(apiNameTemp.getId())){
                result.put("success", false);
                result.put("msg", "您输入的能力名称已经存在!");
                return result;
            }
        }
        if (apiInfo.getApiName() == null) {
            result.put("success", false);
            result.put("msg", "请输入基本信息中星号部分内容!");
            return result;
        }
        try {
            //如果是被动能力
            if (apiInfo.getApiInvokeType() == 1 ) {
                if ( apiInfo.getPubMode() == null || apiInfo.getApiClassName() == null || apiInfo.getVersion() == null
                        || apiInfo.getApiMethodName() == null || apiInfo.getPubMode() == null) {
                    result.put("success", false);
                    result.put("msg", "请输入基本信息中星号部分内容!");
                    return result;
                }

                String reqAddHttp = apiInfo.getReqAddrHttp();
                String reqAddHsf = apiInfo.getReqAddrHsf();
                //拿到地址去查，如果没有默认填写，如果有就提示地址已经有了
                if(StringUtil.isNotEmpty(apiInfo.getReqAddrHttp())){
                    ApiInfoEntity apiInfoHttp = systemService.findUniqueByProperty(ApiInfoEntity.class, "reqAddrHttp", reqAddHttp);
                    if(apiInfo.getPubMode().equals("0") && apiInfoHttp!=null && !apiInfo.getId().equals(apiInfoHttp.getId())){
                        result.put("success", false);
                        result.put("msg", "您输入的http地址已经存在!");
                        return result;
                    }
                    apiInfo.setReqAddrHttp(reqAddHttp);
                }
                if(StringUtil.isNotEmpty(apiInfo.getReqAddrHsf())){
                    ApiInfoEntity apiInfoHsf = systemService.findUniqueByProperty(ApiInfoEntity.class, "reqAddrHsf", reqAddHsf);
                    if(apiInfoHsf!=null && !apiInfo.getId().equals(apiInfoHsf.getId())){
                        result.put("success", false);
                        if (apiInfo.getPubMode().equals("1")){
                            result.put("msg", "您输入的hsf请求地址已经存在!");
                        }
                        if (apiInfo.getPubMode().equals("2")){
                            result.put("msg", "您输入的webservice标识已经存在!");
                        }
                        return result;
                    }
                    apiInfo.setReqAddrHsf(reqAddHsf);
                }
                if(StringUtil.isNotEmpty(apiInfo.getReqAddrHsf())  && StringUtil.isNotEmpty(apiInfo.getReqAddrHttp())){
                    apiInfo.setReqAddrHttp(reqAddHttp);
                    apiInfo.setReqAddrHsf(reqAddHsf);
                }
                if(StringUtil.isEmpty(apiInfo.getReqAddrHsf())){
                    if (apiInfo.getPubMode().equals("1")) {
                        apiInfo = apiInfoService.generateAddr(apiInfo);
                    }
                    if (apiInfo.getPubMode().equals("2")) {
                        apiInfo = apiInfoService.generateAddrWs(apiInfo);
                    }

                }
                //hsf能力
                if ( apiInfo.getPubMode().equals("1")) {
                    if (apiInfo.getGroupName() == null || apiInfo.getHsfGroup() == null) {
                        result.put("success", false);
                        result.put("msg", "请输入基本信息中星号部分内容!");
                        return result;
                    }
                    //根据类名称自动生成HTTP、HSF请求地址(被动能力)
                    apiInfo = apiInfoService.generateAddr(apiInfo);
                    //webservice能力
                } else if (apiInfo.getPubMode().equals("2")) {
                    if (apiInfo.getReqAddrHttp() == null) {
                        result.put("success", false);
                        result.put("msg", "请输入基本信息中星号部分内容!");
                        return result;
                    }
                }
            }
            apiInfoService.updateApi(infoTotal, apiInfo, headers, requests, returns);
            systemService.addLog("能力[" + apiInfo.getId() + "]更新", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            result.put("success", false);
            result.put("msg", "能力更新失败！");
            systemService.addLog("能力[" + apiInfo.getId() + "]更新", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
            return result;
        }
        return result;
    }

    /**
     * 添加能力页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiInfoEntity apiInfo, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiInfo.getId())) {
            apiInfo = apiInfoService.getEntity(ApiInfoEntity.class, apiInfo.getId());
        }
        if (StringUtil.isNotEmpty(apiInfo.getGroupId())) {
            ApiGroupEntity group = systemService.getEntity(ApiGroupEntity.class, apiInfo.getGroupId());
            if (group != null) {
                apiInfo.setGroupName(group.getGroupName());
            }
        }
        req.setAttribute("apiInfoPage", apiInfo);
        ModelAndView model = new ModelAndView("com/kd/op/api/apiInfo-add");
        model.addObject("apiInfo", apiInfo);
        return model;
    }

    /**
     * 更新能力页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiInfoEntity apiInfo, Integer type, HttpServletRequest req) {
        ModelAndView model = new ModelAndView();
        if (type == 1) {
            //以编辑方式打开
            model.setViewName("com/kd/op/api/apiInfo-update");
            ApiInfoEntity orgIdInfo = systemService.getEntity(ApiInfoEntity.class, apiInfo.getId());
        } else if (type == 2 || type ==4) {
            //以只读方式打开
            model = new ModelAndView("com/kd/op/api/apiInfo-detail");
            model.addObject("type",type);
        } else if (type == 3) {
            //以只读方式打开
            ApiInfoEntity orgIdInfo = systemService.getEntity(ApiInfoEntity.class, apiInfo.getId());
            //ISV查看能力详情时所看到的页面
            model = new ModelAndView("com/kd/op/api/apiInfo-detail-isv");
            //拼装https地址
            String httpsAddr = "";
            List<ApiSysEnvironment> sysEnvironments = systemService.findByProperty(ApiSysEnvironment.class, "type", 0);
            if (sysEnvironments != null && sysEnvironments.size() > 0) {
                String ip = sysEnvironments.get(0).getIp();
//                Integer port = sysEnvironments.get(0).getPort();
                httpsAddr = "https://" + ip + "/protocolTrans/openapi/" + orgIdInfo.getReqAddrHttp();
                model.addObject("httpsAddr", httpsAddr);
            }
        }
        if (StringUtil.isNotEmpty(apiInfo.getId())) {
            apiInfo = apiInfoService.getEntity(ApiInfoEntity.class, apiInfo.getId());
            model.addObject("apiInfoPage", apiInfo);

            String hql0 = "from ApiParamEntity where 1 = 1 AND aPI_ID = ? order by paramType asc,parentId asc,sort asc";
            try {
                List<ApiParamEntity> apiParamEntityList = systemService.findHql(hql0, apiInfo.getId());
                if (apiParamEntityList != null && apiParamEntityList.size() > 0) {
                    List<ApiParamEntity> headerParams = new ArrayList<>();
                    List<ApiParamEntity> requestParams = new ArrayList<>();
                    List<ApiParamEntity> resultParams = new ArrayList<>();
                    for (ApiParamEntity param : apiParamEntityList) {
                        if (param.getParamType() == 2) {
                            if (param.getParentId() == null) {
                                headerParams.add(param);
                                for (ApiParamEntity p : apiParamEntityList) {
                                    if (p.getParentId() != null && p.getParentId().equals(param.getId())) {
                                        headerParams.add(p);
                                    }
                                }
                            }
                        } else if (param.getParamType() == 0) {
                            if (param.getParentId() == null) {
                                requestParams.add(param);
                                for (ApiParamEntity p : apiParamEntityList) {
                                    if (p.getParentId() != null && p.getParentId().equals(param.getId())) {
                                        requestParams.add(p);
                                    }
                                }
                            }
                        } else if (param.getParamType() == 1) {
                            if (param.getParentId() == null) {
                                resultParams.add(param);
                                for (ApiParamEntity p : apiParamEntityList) {
                                    if (p.getParentId() != null && p.getParentId().equals(param.getId())) {
                                        resultParams.add(p);
                                    }
                                }
                            }
                        }
                    }
                    model.addObject("headerParams", headerParams);
                    model.addObject("requestParams", requestParams);
                    model.addObject("resultParams", resultParams);
                }
                List<ApiChargeModeRela> relas = systemService.findByProperty(ApiChargeModeRela.class, "apiId", apiInfo.getId());
                StringBuffer sb = new StringBuffer();
                String chargeModeIds = "";
                for (ApiChargeModeRela rela : relas) {
                    sb.append(rela.getChargeModeId() + ",");
                }
                chargeModeIds = sb.toString();
                if (!chargeModeIds.isEmpty()) {
                    chargeModeIds = chargeModeIds.substring(0, chargeModeIds.length() - 1);
                }
                model.addObject("chargeModeIds", chargeModeIds);

                List<ApiFlowModeRelaEntity> rela = systemService.findByProperty(ApiFlowModeRelaEntity.class, "apiId", apiInfo.getId());
                //                List<ApiFlowModeRelaEntity> rela = systemService.findByProperty(ApiFlowModeRelaEntity.class, "apiId", apiInfo.getId());
//                String flowModeIds = rela.get(0).getFlowCtrlModes().replace(';', ',');
//                model.addObject("flowModeIds", flowModeIds);
                sb = new StringBuffer();
                String flowModeIds = "";
                for (ApiFlowModeRelaEntity relation : rela) {
                    sb.append(relation.getFlowCtrlModes() + ",");
                }
                flowModeIds =sb.toString();
                if(!flowModeIds.isEmpty()){
                    flowModeIds =flowModeIds.substring(0,flowModeIds.length() - 1);
                }
                model.addObject("flowModeIds", flowModeIds);
                List<ApiResourceAccessRelaEntity> relation = systemService.findByProperty(ApiResourceAccessRelaEntity.class, "apiId", apiInfo.getId());
                sb = new StringBuffer();
                String resourceAccessIds = "";
                for (ApiResourceAccessRelaEntity relat : relation) {
                    sb.append(relat.getResourceId() + ",");
                }
                resourceAccessIds = sb.toString();
                if (!resourceAccessIds.isEmpty()) {
                    resourceAccessIds = resourceAccessIds.substring(0, resourceAccessIds.length() - 1);
                }
                model.addObject("resourceAccessIds", resourceAccessIds);
            } catch (Exception e) {
                logger.error("error:", e);
            }
        }
        return model;
    }

    /**
     * 能力测试页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goTest")
    public ModelAndView goTest(ApiInfoEntity apiInfo) {
        ModelAndView model;
        if (StringUtil.isNotEmpty(apiInfo.getId())) {
            apiInfo = apiInfoService.getEntity(ApiInfoEntity.class, apiInfo.getId());
        }

        if (apiInfo.getApiInvokeType() == 1) {
            model = new ModelAndView("com/kd/op/api/apiInfo-unactive-test");
        } else {
            model = new ModelAndView("com/kd/op/api/apiInfo-active-test");
        }
        model.addObject("apiInfoPage", apiInfo);
        try {
            List<TSType> typeList = ResourceUtil.allTypes.get("parDType".toLowerCase());
            Collections.sort(typeList);
            JSONArray typeArray = new JSONArray();
            if (typeList != null && !typeList.isEmpty()) {
                for (TSType type : typeList) {
                    JSONObject typeJson = new JSONObject();
                    typeJson.put("id", type.getTypecode());
                    typeJson.put("name", type.getTypename());
                    typeArray.add(typeJson);
                }
            }
            model.addObject("dataTypes", typeArray);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return model;
    }

    @RequestMapping(params = "activeDoTest")
    @ResponseBody
    public String activeDoTest(String arg) {
        logger.info("=========主动能力调用开始=========");
        logger.info("=========页面传入的参数========="+arg);
        JSONObject jsonArg = JSONObject.parseObject(arg);
        JSONObject jsonData = new JSONObject();
        if (jsonArg != null ) {
            jsonData.put("data",jsonArg);
            Object apiId = jsonArg.get("apiId");
            if (apiId != null) {
                String apiIdStr = apiId.toString();
                jsonData.put("apiId",apiIdStr);
            }
        }
        String data="";
        if (jsonData != null) {
            data = jsonData.toString();
        }
        String openplatformData = openplatformAPI.getOpenplatformData(data);
        logger.info("=========主动能力调用结束=========");
        return openplatformData;
    }

    /**
     * 能力审核列表页面跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "apiInfoAuditList")
    public ModelAndView apiInfoAuditList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/api/apiInfoAuditList");
    }

    /**
     * 跳转能力审核页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goApiInfoAudit")
    public ModelAndView goApiInfoAudit(ApiInfoEntity apiInfo, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiInfo.getId())) {
            apiInfo = apiInfoService.getEntity(ApiInfoEntity.class, apiInfo.getId());
            request.setAttribute("apiInfoPage", apiInfo);
        }
        return new ModelAndView("com/kd/op/api/apiInfoAudit");
    }


    /**
     * 更新审核状态
     *
     * @return
     */
    @RequestMapping(params = "doApiInfoAudit")
    @ResponseBody
    public AjaxJson doApiInfoAudit(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "审核成功";
        try {
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            MyBeanUtils.copyBeanNotNull2Bean(apiInfo, apiInfoOld);
            apiInfoService.saveOrUpdate(apiInfoOld);
            systemService.addLog("能力[" + apiInfo.getId() + "]审核", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "审核失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]审核", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新发布状态
     *
     * @return
     */
    @RequestMapping(params = "changePublish")
    @ResponseBody
    public AjaxJson changePublish(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "发布成功";
        try {
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            apiInfoOld.setApiPublishStatus(CustomConstant.PUBLIS_PASS);
            apiInfoOld.setApiStatus(CustomConstant.CAN_USE);
            apiInfoOld.setApiRunStatus(1);
            apiInfoService.saveOrUpdate(apiInfoOld);

            if (apiInfoOld.getPubMode().equals("4")) {
                //mq能力启动监听
                apiInfoService.startMQListener(apiInfoOld);
            }
            systemService.addLog("能力[" + apiInfo.getId() + "]发布", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "发布失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]发布", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新运行状态
     *
     * @return
     */
    @RequestMapping(params = "changeRun")
    @ResponseBody
    public AjaxJson changeRun(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "运行状态修改成功";
        try {
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            MyBeanUtils.copyBeanNotNull2Bean(apiInfo, apiInfoOld);
            apiInfoService.saveOrUpdate(apiInfoOld);
            systemService.addLog("能力[" + apiInfo.getId() + "]修改运行状态", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "运行状态修改失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]修改运行状态", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
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
    public AjaxJson changeStatus(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "可用状态修改成功";
        try {
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            MyBeanUtils.copyBeanNotNull2Bean(apiInfo, apiInfoOld);
            apiInfoService.saveOrUpdate(apiInfoOld);
            systemService.addLog("能力[" + apiInfo.getId() + "]可用状态修改", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "可用状态修改失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]可用状态修改", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 提交审核
     *
     * @return
     */
    @RequestMapping(params = "auditApi")
    @ResponseBody
    public AjaxJson auditApi(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try {
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            apiInfoOld.setApiAuditStatus(1);
            apiInfoService.saveOrUpdate(apiInfoOld);
            systemService.addLog("能力[" + apiInfo.getId() + "]提交审核", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "提交审核失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]提交审核", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 能力提交给指定审核员
     *
     * @param apiInfo
     * @param request
     * @return
     */
    @RequestMapping(params = "apiSubmitToCheck")
    @ResponseBody
    public AjaxJson apiSubmitToCheck(ApiInfoEntity apiInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try {
            String apiData = request.getParameter("apiData");
            JSONArray data = JSONArray.parseArray(apiData);
            com.alibaba.fastjson.JSONObject jb = (com.alibaba.fastjson.JSONObject)data.get(0);
            ApiInfoEntity apiInfoOld = apiInfoService.get(ApiInfoEntity.class, apiInfo.getId());
            apiInfoOld.setApiAuditStatus(1);
            if (jb.get("auditBy") != null) {
                apiInfoOld.setAuditBy(jb.get("auditBy").toString());
            }
            apiInfoService.saveOrUpdate(apiInfoOld);
            systemService.addLog("能力[" + apiInfo.getId() + "]提交审核", Globals.MODULE_API, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:",e);
            message = "提交审核失败";
            systemService.addLog("能力[" + apiInfo.getId() + "]提交审核", Globals.MODULE_API, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 文档下载列表页面
     *
     * @return
     */
    @RequestMapping(params = "apiDocs")
    public ModelAndView apiDocs() {
        return new ModelAndView("com/kd/op/api/apiDocs");
    }

    @RequestMapping(params = "getDocs")
    @ResponseBody
    public JSONObject getDocs() {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id", 1);
        obj.put("docName", "能力开放平台能力接入文档.doc");
        array.add(obj);

        result.put("rows", array);
        result.put("total", 1);
        return result;
    }

    @RequestMapping(params = "docDownload")
    public void docDownload(String fileName, HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fileIn = null;
        BufferedInputStream in = null;
        DataOutputStream out = null;
        try {
            String filePath = File.separator + "WEB-INF" + File.separator + "files" + File.separator + fileName;
            filePath = request.getSession().getServletContext().getRealPath(filePath);
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            builder.append("HttpOnly; ");
            //设置cookie
            response.addHeader("Set-Cookie", builder.toString());
            fileIn = new FileInputStream(filePath);
            in = new BufferedInputStream(fileIn);
            out = new DataOutputStream(response.getOutputStream());
            byte[] buffered = new byte[1024];
            while (in.read(buffered, 0, buffered.length) != -1) {
                out.write(buffered, 0, buffered.length);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    logger.error("error:", e1);
                }
            }
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (Exception e1) {
                    logger.error("error:", e1);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                    logger.error("error:", e1);
                }
            }
        }
    }


    //导出为Excel
//    @RequestMapping(params = "exportXls")
//    public String exportXls(ApiInfoEntity apiInfo, HttpServletRequest request, HttpServletResponse response
//            , DataGrid dataGrid,ModelMap modelMap) {
//        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);
//        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo, request.getParameterMap());
//        List<ApiInfoEntity> apiInfos = this.systemService.getListByCriteriaQuery(cq,false);
//        try {
//            modelMap.put(NormalExcelConstants.FILE_NAME,"能力信息表"+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd));
//            modelMap.put(NormalExcelConstants.CLASS,ApiInfoEntity.class);
//            modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("能力信息表列表",
//                    "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
//                    "api_info"));
//            modelMap.put(NormalExcelConstants.DATA_LIST,apiInfos);
//        } catch (Exception e) {
//            logger.error("error:", e);
//        }
//        return NormalExcelConstants.JEECG_EXCEL_VIEW;
//    }

//    @RequestMapping(params = "upload")
//    public ModelAndView upload(HttpServletRequest req) {
//        req.setAttribute("controller_name","apiInfoController");
//        return new ModelAndView("common/upload/pub_excel_upload1");
//    }

//    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
//        AjaxJson j = new AjaxJson();
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//            MultipartFile file = entity.getValue();// 获取上传文件对象
//            ImportParams params = new ImportParams();
//            params.setTitleRows(2);
//            params.setHeadRows(1);
//            params.setNeedSave(true);
//            try {
//               List<ApiInfoEntity> apiInfos = ExcelImportUtil.importExcel(file.getInputStream(),ApiInfoEntity.class,params);
//               if(apiInfos.size() == 0){
//                   j.setMsg("excel文件不能为空，导入失败");
//               }else{
//                   for (ApiInfoEntity apiInfo : apiInfos) {
//                   String apiId = apiInfo.getId();
//                   if(apiId == null && StringUtils.isEmpty(apiId)){
//                       j.setMsg("缺少必填字段ID，导入失败");
//                   }else{
//                       List<ApiInfoEntity> infos = systemService.findByProperty(ApiInfoEntity.class,"id",apiId);
//                       if(infos.size()!=0){
//                           //更新
//                           ApiInfoEntity  apiInfoEntity = infos.get(0);
//                           MyBeanUtils.copyBeanNotNull2Bean(apiInfo,apiInfoEntity);
//                           systemService.saveOrUpdate(apiInfoEntity);
//                       }else{
//                           systemService.save(apiInfo);
//                       }
//                       j.setMsg("文件导入成功！");
//                   }
//                }
//               }
//            } catch (Exception e) {
//                j.setMsg("文件导入失败！");
//                logger.error(ExceptionUtil.getExceptionMessage(e));
//            }finally{
//                try {
//                    file.getInputStream().close();
//                } catch (IOException e) {
//                    logger.error("执行失败：" + e.getMessage());
//                }
//            }
//        }
//        return j;
//    }



    @RequestMapping(params = "exportXls")
    public void exportXls(ApiInfoEntity apiInfo, HttpServletRequest request, HttpServletResponse response) {
        try {
            apiInfoService.exportExcel(apiInfo, response);
        } catch (Exception e) {
            logger.error("error:", e);
        }
    }

    @RequestMapping(params = "importXls")
    public ModelAndView importXls() {
        return new ModelAndView("com/kd/op/api/importXls");
    }

    @RequestMapping(params = "doImportXls")
    @ResponseBody
    public AjaxJson doImportXls(@RequestParam("file") MultipartFile[] files) {
        AjaxJson j = new AjaxJson();
        String message = "导入成功";
        try {
            apiInfoService.importExcel(files);
        } catch (Exception e) {
            message = "导入失败";
            logger.error("error:", e);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 能力发布列表
     *
     * @return
     */
    @RequestMapping(params = "apiPublishList")
    public ModelAndView apiPublishList() {

        return new ModelAndView("com/kd/op/api/apiPublishList");
    }

    /**
     * 能力参数录入页面，为了嵌入iFrame，防止js冲突test
     *
     * @return
     */
    @RequestMapping(params = "paramPage")
    public ModelAndView table(Integer loadType, Integer paramType, String apiId, String ifId) {
        ModelAndView model = new ModelAndView("com/kd/op/api/paramTable");
        //加载数据类型
        List<TSType> typeList = ResourceUtil.allTypes.get("parDType".toLowerCase());
        Collections.sort(typeList);
        JSONArray typeArray = new JSONArray();
        if (typeList != null && !typeList.isEmpty()) {
            for (TSType type : typeList) {
                JSONObject typeJson = new JSONObject();
                typeJson.put("id", type.getTypecode());
                typeJson.put("name", type.getTypename());
                typeArray.add(typeJson);
            }
        }
        model.addObject("dataTypes", typeArray);
        model.addObject("loadType", loadType);
        model.addObject("paramType", paramType == null ? "" : paramType);
        model.addObject("apiId", apiId);
        model.addObject("ifId", ifId);
        return model;
    }

    @RequestMapping(params = "loadParamsByApiAndType")
    @ResponseBody
    public JSONArray loadParamsByApiAndType(String apiId, Integer paramType) {
        JSONArray array = new JSONArray();
        List<ApiParamEntity> params = apiInfoService.getByAPiAndType(apiId, paramType);
        for (ApiParamEntity param : params) {
            JSONObject json = new JSONObject();
            json.put("id", param.getId());
            json.put("paramName", param.getParamName());
            json.put("parentId", param.getParentId());
            json.put("paramVisible", param.getparamVisible());
            json.put("paramEncrypt", param.getParamEncrypt());
            json.put("isSource", param.getIsSource());
            json.put("paramDesc", param.getParamDesc());
            json.put("dataType", param.getDataType());
            json.put("defaultValue", param.getDefaultValue());
            array.add(json);
        }
        return array;
    }
}
