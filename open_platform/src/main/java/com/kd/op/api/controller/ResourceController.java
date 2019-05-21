package com.kd.op.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.service.OrderServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.log4j.Logger;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kd.op.api.service.ApiResourceServiceI;
import com.kd.op.api.service.ApiFlowModeServiceI;
import sun.security.pkcs11.wrapper.Constants;

import java.util.*;


@Controller
@RequestMapping("/resourceController")
public class ResourceController extends BaseController {
    private static final Logger logger = Logger.getLogger(ResourceController.class);
    @Autowired
    private OrderServiceI orderService;
    @Autowired
    private ApiResourceServiceI apiResourceService;
    @Autowired
    private ApiFlowModeServiceI ApiFlowModeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiInfoServiceI apiInfoService;

    @RequestMapping(params = "dataResourceList")
    public ModelAndView dataResourceList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/dataacess");
    }

    @RequestMapping(params = "flowMode")
    public ModelAndView flowStrategyList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/llclgl");
    }

    @RequestMapping(params = "middlewareList")
    public ModelAndView middlewareList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/middle");
    }

    @RequestMapping(params = "storageCalculateList")
    public ModelAndView storageCalculateList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/store");
    }


    /**
     * easyui 资源控制列表AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param
     */

    @RequestMapping(params = "resourceControlDatagrid")
    public void resourceControlDatagrid(ApiOrderEntity apiOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        //存储最终输出结果
        List<ApiOrderEntity> endList  = new ArrayList<>();
        int page =dataGrid.getPage();
        int rows=dataGrid.getRows();
        String selectResult = "select a.id,a.app_id as appID,a.app_name as appName,a.money,a.pay_status as payStatus,a.pay_time as payTime,a.audit_status as auditStatus,a.order_type as orderType,a.create_date as createDate from api_order a,api_order_detail b ,api_info c where a.id = b.order_id and b.api_id = c.id and c.resource_ctrl = '1'";

        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiOrder.getAppName())) {
            String s = StringTransUtil.stringReplace(apiOrder.getAppName());
            apiOrder.setAppName("*" + s + "*");
        }
        try {
            //从订单记录列表加载已支付的数据
            String listType = request.getParameter("listType");
            if (StringUtil.isNotEmpty(listType) &&  CustomConstant.LIST_RESOURCE.equals(listType)) {
                String currentUserName = null;
                String roleById = null;
                TSUser sessionUser = ResourceUtil.getSessionUserName();
                if (sessionUser != null) {
                    currentUserName = sessionUser.getUserName();
                    String id = sessionUser.getId();
                    roleById = orderService.findRoleById(id);
                }
            if (roleById != null && !roleById.equals("admin")) {
                selectResult = selectResult + " and create_by = " + currentUserName;
            }
            selectResult = selectResult + " and pay_status = " + CustomConstant.PAY_HASPAY;
            selectResult = selectResult + " and audit_status = " + CustomConstant.AUDIT_PASS;
        }

            if (StringUtil.isNotEmpty(listType) && StringUtil.isNotEmpty(apiOrder.getOrderType()) && apiOrder.getOrderType().equals("app")) {
                selectResult = selectResult + " and order_type = app";
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        List<Map<String, Object>> list = systemService.findForJdbcParam(selectResult,page,rows);
        int total = systemService.findListbySql(selectResult).size();


        dataGrid.setResults(list);
        dataGrid.setPage(page);
        dataGrid.setRows(rows);
        dataGrid.setTotal(total);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "dataResourcedatagrid")
    public void datagrid(ApiResourceAccessEntity resourceInfo, String apiId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiResourceAccessEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, resourceInfo);
        try {
            //自定义追加查询条件
            if (StringUtil.isNotEmpty(apiId)) {
                List<ApiResourceAccessRelaEntity> relas = systemService.findByProperty(ApiResourceAccessRelaEntity.class, "apiId", apiId);
                List<String> modeIds = new ArrayList<>();
                relas.forEach(p -> {
                    modeIds.add(p.getResourceId());
                });
                cq.in("id", modeIds.toArray());
            }
            cq.isNotNull("resourceName");
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiResourceService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "dataResourcegoAdd")
    public ModelAndView goAdd(HttpServletRequest req) {

        return new ModelAndView("com/kd/op/resource/resource-add");
    }


    /**
     * 添加接口分组表
     */
    @RequestMapping(params = "dataResourcedoAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiResourceAccessEntity resourceInfo, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口分组表添加成功";
        try {

            apiResourceService.save(resourceInfo);
            systemService.addLog("能力分组["+resourceInfo.getId()+"]添加", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "接口分组表添加失败";
            systemService.addLog("能力分组["+resourceInfo.getId()+"]添加", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除接口信息表
     *
     * @return
     */
    @RequestMapping(params = "dataResourcedoDel")
    @ResponseBody
    public AjaxJson doDel(ApiResourceAccessEntity resourceInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        resourceInfo = systemService.getEntity(ApiResourceAccessEntity.class, resourceInfo.getId());
        String message = "资源信息删除成功";
        try {
            apiResourceService.delMain(resourceInfo);
            systemService.addLog("能力分组["+resourceInfo.getId()+"]删除", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "资源信息删除失败";
            systemService.addLog("能力分组["+resourceInfo.getId()+"]删除", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "dataResourcegoEdit")
    public ModelAndView goEditPrepayment(Integer id) {
        ModelAndView model = new ModelAndView("com/kd/op/resource/resource-edit");
        if (id != null) {
            ApiResourceAccessEntity resource = apiResourceService.getEntity(ApiResourceAccessEntity.class, id);
            model.addObject("chargeMode", resource);
        }
        return model;
    }

    @RequestMapping(params = "dataResourceupdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiResourceAccessEntity chargeMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "资源配置更新成功";
        j.setSuccess(true);
        ApiResourceAccessEntity t = apiResourceService.get(ApiResourceAccessEntity.class, chargeMode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(chargeMode, t);
            apiResourceService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO, "1");
        } catch (Exception e) {
            logger.error("error:", e);
            message = "资源配置更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    private String checkResourceInfo(ApiResourceAccessEntity resourceInfo) {
        String msg = "repeat";
        CriteriaQuery cq = new CriteriaQuery(ApiResourceAccessEntity.class);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, resourceInfo);
        try {
            //自定义追加查询条件
            cq.eq("resourcePlace", resourceInfo.getResourcePlace());
            //cq.isNotNull("resourcePlace");
            //cq.eq("resourcePlace","北京");

        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        return "";
    }

    @RequestMapping(params = "flowModeList")
    public ModelAndView prepayment(String type) {
//        type = "1";
        ModelAndView model = new ModelAndView("com/kd/op/resource/flowModeList");
        model.addObject("type", type);
        return model;
    }

    @RequestMapping(params = "flowdatagrid")
    public void datagrid(ApiFlowModeEntity charge, String apiId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiFlowModeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charge, request.getParameterMap());
        try {
            //自定义追加查询条件
            //只回显当前服务关联的计费模型
            if (StringUtil.isNotEmpty(apiId)) {
                List<ApiFlowModeRelaEntity> relas = systemService.findByProperty(ApiFlowModeRelaEntity.class, "apiId", apiId);
                List<String> modeIds = new ArrayList<>();
                String delimeter1 = ";";  // 指定分割字符， . 号需要转义
//                String[] temp;
//                if (relas != null && relas.size() > 0) {
//                    temp = relas.get(0).getFlowCtrlModes().split(delimeter1); // 分割字符串
//                    for (String x : temp) {
////                   Integer i = Integer.parseInt(x);
//                        modeIds.add(x);
//                    }
//                    cq.in("id", modeIds.toArray());
//                }
                modeIds.add("xxxxxx");
                relas.forEach(p->{
                    modeIds.add(p.getFlowCtrlModes());
                });
                cq.in("id", modeIds.toArray());

            }

            cq.eq("type", charge.getType());
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.ApiFlowModeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "flowdelete")
    @ResponseBody
    public AjaxJson doFlowDel(ApiFlowModeEntity flowMode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        flowMode = systemService.getEntity(ApiFlowModeEntity.class, flowMode.getId());
        String message = "资源信息删除成功";
        try {
            systemService.delete(flowMode);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO, "1");
        } catch (Exception e) {
            logger.error("error:", e);
            message = "资源信息删除失败";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "flowgoAdd")
    public ModelAndView goAddFlow(String type) {
        ModelAndView model = new ModelAndView("com/kd/op/resource/flowMode-add");
        model.addObject("type", type);
        return model;
    }

    @RequestMapping(params = "flowDoAdd")
    @ResponseBody
    public AjaxJson doFlowAdd(ApiFlowModeEntity flowMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "计费方式添加成功";
        j.setSuccess(true);
        try {
            ApiFlowModeService.save(flowMode);

            systemService.addLog("计费方式["+flowMode.getId()+"]添加", Globals.MODULE_CHARGE,Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "计费方式添加失败";
            j.setSuccess(false);
            systemService.addLog("计费方式["+flowMode.getId()+"]添加", Globals.MODULE_CHARGE,Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "flowEdit")
    public ModelAndView goEditFlow(String type, String id) {
        ModelAndView model = new ModelAndView("com/kd/op/resource/flowMode-edit");
        model.addObject("type", type);
        if (id != null) {
            ApiFlowModeEntity resource = ApiFlowModeService.getEntity(ApiFlowModeEntity.class, id);
            model.addObject("chargeMode", resource);
        }
        return model;
    }

    @RequestMapping(params = "flowUpdate")
    @ResponseBody
    public AjaxJson doflowUpdate(ApiFlowModeEntity chargeMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "流量策略更新成功";
        j.setSuccess(true);
        ApiFlowModeEntity t = ApiFlowModeService.get(ApiFlowModeEntity.class, chargeMode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(chargeMode, t);
            ApiFlowModeService.saveOrUpdate(t);
            systemService.addLog("流量策略["+t.getId()+"]更新", Globals.MODULE_FLOW,Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "流量策略更新失败";
            systemService.addLog("流量策略["+t.getId()+"]更新", Globals.MODULE_FLOW,Globals.Log_Leavel_ERROR, Globals.FAILD);
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 打开资源选择页面或者回显页面
     *
     * @param id    服务或者场景的id
     * @return
     */
    @RequestMapping(params = "selectResource")
    public ModelAndView selectResource(String id,String orderId) {
        ModelAndView model = new ModelAndView("com/kd/op/resource/resourceSelect");
        model.addObject("id", id);
        Map<Object,Object> map = apiResourceService.findApiResourceControl(id,orderId);
        model.addObject("paramMap",map);
        return model;
    }

    /**
     * 打开资源选择页面或者回显页面
     *
     * @param id    服务或者场景的id
     * @return
     */
    @RequestMapping(params = "selectResourceNew")
    public ModelAndView selectResourceNew(String id,String orderId) {
        ModelAndView model = new ModelAndView("com/kd/op/resource/resourceSelect-new");
        model.addObject("id", id);
        JSONArray  array = apiResourceService.findApiResourceControlNew(id,orderId);
        model.addObject("paramMap",array);
        return model;
    }


    /*
     * @Author zyz
     * @Description 查看资源控制
     * @Date  2019/3/14
     * @Param [param]
     * @return  org.springframework.web.servlet.ModelAndView
     */
    /*   @RequestMapping(params = "lookResourceCtrol")
   // public ModelAndView lookResourceCtrol(String param,String orderId) {
    public ModelAndView lookResourceCtrol(String apiId,String orderId) {
     System.out.println("orderId============="+orderId);
        System.out.println("apiId============="+apiId);
        ModelAndView model = new ModelAndView("com/kd/op/resource/resourceLook");
        Map paramMap = getParamMap(param);
        model.addObject("paramMap",paramMap);
        return model;
    }*/
   @RequestMapping(params = "lookResourceCtrol")
    public ModelAndView lookResourceCtrol(String apiId,String orderId) {
       Map<String,String> map = new HashMap<>();
       List<ApiResourceControlEntity> result = apiResourceService.findResourceControls(apiId,orderId);
       if (!result.isEmpty() && result.size()!=0){
           for (ApiResourceControlEntity apiResourceControlResult : result) {
               map.put(apiResourceControlResult.getResourceParamName(),apiResourceControlResult.getResourceParamValue());
           }
       }else{
           String hql="from ApiParamEntity where apiId=? and isSource=?";
           List<ApiParamEntity> list = systemService.findHql(hql,apiId,CustomConstant.IS_RESOURCE);
           for (ApiParamEntity entity : list) {
               map.put(entity.getParamName(),"");
           }
       }

        ModelAndView model = new ModelAndView("com/kd/op/resource/resourceLook");
        model.addObject("paramMap",map);
        return model;
    }



    private Map getParamMap(String param) {
        HashMap<String, String> paramMap = new HashMap<>();
        if (param != null && StringUtils.isNotEmpty(param)) {
            String[] resourceParams = param.split(",");
            if (resourceParams.length == 1) {
                String[] paramSplit = resourceParams[0].replace("\"","").split(":");
                String resourceParamKey=paramSplit[0].replace("[","");
                String resourceParamValue = paramSplit[1].replace("]", "");
                paramMap.put(resourceParamKey,resourceParamValue);
            } else {
                for (int i = 0; i < resourceParams.length; i++) {
                    String[] paramSplit = resourceParams[i].replace("\"","").split(":");
                    if (i == 0) {
                        String resourceParamKey=paramSplit[0].replace("[","");
                        paramMap.put(resourceParamKey,paramSplit[1]);
                    } else if (i == resourceParams.length - 1) {
                        String resourceParamValue = paramSplit[1].replace("]", "");
                        paramMap.put(paramSplit[0], resourceParamValue);
                    } else {
                        paramMap.put(paramSplit[0],paramSplit[1]);
                    }
                }
            }
        }

        return paramMap;
    }
    /**
     * 资源控制列表
     *
     * @param listType
     * @return
     */
    @RequestMapping(params = "resourceControlList")
    public ModelAndView resourceControlList(String listType, HttpServletRequest request) {
        String url = "";
        ModelAndView mv = new ModelAndView();
        logger.info("resourceControlList");
        if (CustomConstant.LIST_RESOURCE.equals(listType)) {
            url = "com/kd/op/resource/resourceControl";
        }
        mv.setViewName(url);
        return mv;
    }



    /**
     * 获取资源控制范围数据（中台商户的数据）
     *
     * @param level 1:服务或者场景选择资源范围，2：应用订购服务或者场景时选择资源范围
     * @param type  api：为服务获取资源范围，scene：为场景获取资源范围
     * @param id    服务或者场景的id
     * @return
     */
    @RequestMapping(params = "getCorpTree")
    @ResponseBody
    public JSONObject getCorpTree(String level, String type, String id, String orderId) {
        //构建树
        JSONObject result = null;
        try {
            result = apiResourceService.getCorpTree(type, id, level, orderId);
        } catch (Exception e) {
            logger.error("getCorpTree error");
        }

        return result;
    }

    /**
     * 查询当前场景或服务是否需要资源控制
     *
     * @param type api服务  scene场景
     * @param id   对应的服务或场景的id
     * @return
     */
    @RequestMapping(params = "getResourceCtrlSts")
    @ResponseBody
    public JSONObject getResourceCtrlSts(String type, String id) {
        JSONObject result = new JSONObject();
        Integer isCtrl = 0;
        try {
            isCtrl = apiResourceService.getResourceCtrlSts(type, id);
        } catch (Exception e) {
            result.put("success", false);
        }
        result.put("success", true);
        result.put("isCtrl", isCtrl);
        return result;
    }

    /**
     * 查询当前场景或服务是否需要资源控制
     *
     *
     * @param
     * @return
     */
    @RequestMapping(params = "insertResource")
    @ResponseBody
    public  String  insertResource(HttpServletRequest req , String info,String orderId) {
        JSONObject jsonObject = JSON.parseObject(info);
        JSONArray drArr = jsonObject.getJSONArray("drArr");
        apiResourceService.saveResourceControl(drArr,orderId);
        return null;
    }



}
