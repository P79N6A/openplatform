package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.*;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.StringTransUtil;
import com.sgcc.hlht.service.OpenPlatformWebSupportService;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 服务订单表
 * @date 2018-11-21 17:33:39
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceI orderService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiInfoServiceI apiInfoService;
    @Autowired
    private ApiSceneServiceI apiSceneService;
    @Autowired
    private ApiChargeModeServiceI chargeModeService;




    /**
     * 服务订单表列表 页面跳转
     *
     * @param listType 1 订单管理   2 订单记录  3 订单审核
     * @return
     */
    @RequestMapping(params = "apiOrderList")
    public ModelAndView apiOrderList(String listType, HttpServletRequest request) {
        String url = "";
        ModelAndView mv = new ModelAndView();
        if (CustomConstant.LIST_ORDER.equals(listType)) {
            url = "com/kd/op/order/apiOrderList";
        } else if (CustomConstant.LIST_RECORD.equals(listType)) {
            url = "com/kd/op/order/apiOrderRecordList";
        } else if (CustomConstant.LIST_AUDIT.equals(listType)) {
            url = "com/kd/op/order/apiOrderAuditList";
        }
        mv.setViewName(url);
        return mv;
    }

    /**
     * 应用订单表列表 页面跳转
     *
     * @param listType 1 订单管理   2 订单记录  3 订单审核
     * @return
     */
    @RequestMapping(params = "appOrderList")
    public ModelAndView appOrderList(String listType) {
        String url = "";
        ModelAndView mv = new ModelAndView();
        if (CustomConstant.LIST_ORDER.equals(listType)) {
            url = "com/kd/op/order/appOrderList";
        } else if (CustomConstant.LIST_RECORD.equals(listType)) {
            url = "com/kd/op/order/appOrderRecordList";
        } else if (CustomConstant.LIST_AUDIT.equals(listType)) {
            url = "com/kd/op/order/appOrderAuditList";
        }
        mv.setViewName(url);
        return mv;
    }

    /**
     * easyui 服务订单AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param
     */

    @RequestMapping(params = "orderDatagrid")
    public void orderDatagrid(ApiOrderEntity apiOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiOrder.getAppName())) {
            String s = StringTransUtil.stringReplace(apiOrder.getAppName());
            apiOrder.setAppName("*" + s + "*");
        }
        CriteriaQuery cq = new CriteriaQuery(ApiOrderEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiOrder, request.getParameterMap());
        try {
            //从订单记录列表加载已支付的数据
            String listType = request.getParameter("listType");
            if (StringUtil.isNotEmpty(listType) && CustomConstant.LIST_RECORD.equals(listType)) {
                String currentUserName = null;
                String roleById=null;
                TSUser sessionUser = ResourceUtil.getSessionUserName();
                if (sessionUser != null) {
                    currentUserName = sessionUser.getUserName();
                    String id = sessionUser.getId();
                    roleById = orderService.findRoleById(id);
                }
                if (roleById!=null && !roleById.equals("admin")){
                    cq.eq("createBy", currentUserName);
                }
                cq.eq("payStatus", CustomConstant.PAY_HASPAY);
                cq.eq("auditStatus", CustomConstant.AUDIT_PASS);
            }

            //如果是从订单列表进行查看 则当前登录人只能看到自己的
            if (StringUtil.isNotEmpty(listType) && CustomConstant.LIST_ORDER.equals(listType)) {

                String currentUserName = "xxxxx";
                if (ResourceUtil.getSessionUserName() != null) {
                    currentUserName = ResourceUtil.getSessionUserName().getUserName();
                }
                cq.eq("createBy", currentUserName);
            }
            //根据审核员过滤
            if (StringUtil.isNotEmpty(listType) && CustomConstant.LIST_AUDIT.equals(listType)) {
                String username = ResourceUtil.getSessionUserName().getUserName();
                TSBaseUser tsBaseUser = systemService.findUniqueByProperty(TSBaseUser.class, "userName", username);
                if(tsBaseUser!=null){
                    //获取角色
                    String userKey = tsBaseUser.getUserKey();
                    if(userKey.equals("系统管理员")){

                    }
                    if(userKey.equals("开放平台审核员")){
                        cq.eq("auditBy",username);
                    }
                }
            }



            if (StringUtil.isNotEmpty(listType) && StringUtil.isNotEmpty(apiOrder.getOrderType()) && apiOrder.getOrderType().equals("app")) {
                cq.eq("orderType", "app");
            } else {
                cq.notEq("orderType", "app");
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.orderService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 跳转订单中配置服务页面
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "orderApiSelect")
    public ModelAndView orderApiSelect(ApiOrderEntity apiOrder, HttpServletRequest request) {
        //根据订单类型跳转相应的页面
        ApiOrderEntity apiOrderEntity = systemService.get(ApiOrderEntity.class, apiOrder.getId());
        ApiAppEntity   apiAppEntity =systemService.findUniqueByProperty(ApiAppEntity.class, "id",apiOrderEntity.getAppId());
        //  ApiAppEntity   apiAppEntity1 = systemService.get(ApiAppEntity.class, apiOrder.getAppId());  //增加查询应用类型  必须要用id

        ModelAndView model = new ModelAndView("com/kd/op/order/orderApiSelect");
        String data = null;
        try {
            data = orderService.getOrderDetailJSON(apiOrder);
        } catch (Exception e) {
            logger.error("orderApiSelect error");
        }
        model.addObject("orderDetails", data);
        model.addObject("orderId", apiOrder.getId());
        model.addObject("appType",apiAppEntity.getAppType());
        return model;
    }

    /**
     * 跳转订单中配置场景页面
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "orderSceneSelect")
    public ModelAndView orderSceneSelect(ApiOrderEntity apiOrder, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderSceneSelect");
        String data = null;
        try {
            data = orderService.getOrderDetailJSON(apiOrder);
        } catch (Exception e) {
            logger.error("orderSceneSelect error");
        }
        model.addObject("orderDetails", data);
        model.addObject("orderId", apiOrder.getId());
        return model;
    }

    /**
     * 从应用订购过来的直接保存订单
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrder")
    @ResponseBody
    public JSONObject saveOrder(ApiOrderEntity apiOrderEntity, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String apiData = request.getParameter("apiData");
        JSONArray data = JSONArray.parseArray(apiData);
        //查询该应用之前的订购记录
        String sql = "SELECT aod.scene_id sceneId,aod.api_id apiId  FROM api_order ao,api_order_detail aod " +
                "WHERE ao.id=aod.order_id AND ao.app_id=?;";
        String hql = "from ApiSceneRelaEntity where sceneId=?";
        ApiOrderEntity apiOrder;
        if (apiOrderEntity.getId() != null) {
            apiOrder = systemService.get(apiOrderEntity.getClass(), apiOrderEntity.getId());
        } else {
            apiOrder=apiOrderEntity;
        }
        List<Map<String, Object>> list = systemService.findForJdbc(sql, apiOrder.getAppId());
        for (int i = 0; i < data.size(); i++) {
            com.alibaba.fastjson.JSONObject jb = (com.alibaba.fastjson.JSONObject) data.get(i);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    //之前订购的所有场景
                    if (map.get("sceneId") != null) {
                        String sceneId = (String) map.get("sceneId");
                        if (jb.get("sceneId") != null && !sceneId.equals(jb.getString("sceneId"))) {
                            //本次订购的为场景
                            result.put("success", false);
                            result.put("msg", "您已订购其他场景，不能订购该场景！");
                            return result;
                        } else if (jb.get("apiId") != null) {
                            //本次订购的为能力
                            List<ApiSceneRelaEntity> apiSceneRelaList = systemService.findHql(hql, sceneId);
                            for (ApiSceneRelaEntity apiSceneRela : apiSceneRelaList) {
                                if (apiSceneRela.getApiId()!=null&&apiSceneRela.getApiId().equals(jb.getString("apiId"))) {
                                    result.put("success", false);
                                    result.put("msg", "您订购的能力已在场景中订购过，不能再次订购！");
                                    return result;
                                }
                            }
                        }
                    } else if (map.get("apiId") != null) {
                        //之前订购的能力 本次的订购的为场景
                        List<ApiSceneRelaEntity> apiSceneRelaList = systemService.findHql(hql, jb.getString("sceneId"));
                        for (ApiSceneRelaEntity apiSceneRela : apiSceneRelaList) {
                            if (apiSceneRela.getApiId()!=null && apiSceneRela.getApiId().equals(map.get("apiId"))) {
                                result.put("success", false);
                                result.put("msg", "您订购的场景包含已订购的能力，不能订购该场景！");
                                return result;
                            }
                        }
                    }
                }
            }
        }
        try {
            orderService.saveOrUpdateOrder(apiOrder, request);
            orderService.saveOrUpdateOrderConfig(apiOrder,request);
            if (StringUtil.isNotEmpty(apiOrder.getAppId())) {
                result.put("success", true);
                result.put("msg", "订购成功，请到订单中查看");
            } else if (StringUtil.isNotEmpty(apiOrder.getId())) {
                result.put("success", true);
                result.put("msg", "订单更新成功");
            }
            systemService.addLog("订单[" + apiOrder.getId() + "]下单成功", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            if (StringUtil.isNotEmpty(apiOrder.getAppId())) {
                result.put("success", false);
                result.put("msg", "订购失败");
            } else if (StringUtil.isNotEmpty(apiOrder.getId())) {
                result.put("success", false);
                result.put("msg", "订单更新失败");
            }
            systemService.addLog("订单[" + apiOrder.getId() + "]下单失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);

        }
        return result;
    }

    /**
     * 订单提交审核
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "orderSubmit")
    @ResponseBody
    public AjaxJson orderSubmit(ApiOrderEntity apiOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try {
            ApiOrderEntity apiOrderEntity = orderService.get(ApiOrderEntity.class, apiOrder.getId());
            apiOrderEntity.setAuditStatus(CustomConstant.AUDIT_WAIT);
            orderService.saveOrUpdate(apiOrderEntity);
            systemService.addLog("订单[" + apiOrder.getId() + "]提交审核成功", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "提交审核失败";
            systemService.addLog("订单[" + apiOrder.getId() + "]提交审核失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 订单的详细信息查看
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "goOrderDetailPage")
    public ModelAndView goOrderDetailPage(ApiOrderEntity apiOrder, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderDetailPage");
        ApiOrderEntity apiOrderEntity = orderService.get(ApiOrderEntity.class, apiOrder.getId());
        model.addObject("apiOrder", apiOrderEntity);
        return model;
    }


     /**
      * @Author zyz
      * @Description 跳转webService配置
      * @Date  2019/4/3
      * @Param [apiOrder, request]
      * @return  org.springframework.web.servlet.ModelAndView
      */
    @RequestMapping(params = "appOrderWebserviceConfig")
    public ModelAndView appOrderWebserviceConfig(String id,String fromDatabase) {
        ModelAndView model = new ModelAndView("com/kd/op/order/appOrderWebserviceConfig");
        model.addObject("id", id);
        model.addObject("fromDatabase",fromDatabase);
        return model;
    }
    /**
     * 订单查看详情页面 不可编辑
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "lookDetail")
    public ModelAndView lookDetail(ApiOrderEntity apiOrder, HttpServletRequest request,String type) {
        //根据订单类型跳转相应的页面
        ApiOrderEntity apiOrderEntity = systemService.get(ApiOrderEntity.class, apiOrder.getId());
        ApiAppEntity   apiAppEntity =systemService.findUniqueByProperty(ApiAppEntity.class, "id",apiOrderEntity.getAppId());
      //  ApiAppEntity   apiAppEntity1 = systemService.get(ApiAppEntity.class, apiOrder.getAppId());  //增加查询应用类型  必须要用id
        String data = null;
        try {
            data = orderService.getOrderDetailJSON(apiOrder);
        } catch (Exception e) {
            logger.error("lookDetail error");
        }
        request.setAttribute("orderDetails", data);
        request.setAttribute("orderId", apiOrder.getId());

        TSUser sessionUser = ResourceUtil.getSessionUser();
        String id = sessionUser.getId();
        String rCode =orderService.findRoleById(id);
        request.setAttribute("rCode",rCode);

        if (CustomConstant.API.equals(apiOrderEntity.getOrderType())&& CustomConstant.Order.equalsIgnoreCase(type)) {
            return new ModelAndView("com/kd/op/order/apiOrderResourceCDetailList");
        }else if (CustomConstant.API.equals(apiOrderEntity.getOrderType())) {
            ModelAndView  modelAndView=   new ModelAndView("com/kd/op/order/apiOrderDetailList");
            modelAndView.addObject("appType",apiAppEntity.getAppType());
            modelAndView.addObject("orderDetails",data);
            return modelAndView;
        }else if (CustomConstant.SCENE.equals(apiOrderEntity.getOrderType())) {
            return new ModelAndView("com/kd/op/order/sceneOrderDetailList");
        }

        return new ModelAndView("common/404");
    }

    /**
     * 订单支付
     *
     * @param apiOrder
     * @return
     */
    @RequestMapping(params = "orderPay")
    @ResponseBody
    public AjaxJson orderPay(ApiOrderEntity apiOrder) {
        AjaxJson j = new AjaxJson();
        String message = "支付成功";
        try {
            ApiOrderEntity apiOrderEntity = orderService.get(ApiOrderEntity.class, apiOrder.getId());
            apiOrderEntity.setPayStatus(CustomConstant.AUDIT_WAIT);
            apiOrderEntity.setPayTime(new Date());
            orderService.saveOrUpdate(apiOrderEntity);
            systemService.addLog("订单[" + apiOrder.getId() + "]支付成功", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "支付失败";
            systemService.addLog("订单[" + apiOrder.getId() + "]支付失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转订单审核页面
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "goOrderAudit")
    public ModelAndView goOrderAudit(ApiOrderEntity apiOrder, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderAudit");
        ApiOrderEntity apiOrderEntity = orderService.get(ApiOrderEntity.class, apiOrder.getId());
        model.addObject("order", apiOrderEntity);
        return model;
    }

    /**
     * 执行审核操作
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "doOrderAudit")
    @ResponseBody
    public AjaxJson doOrderAudit(ApiOrderEntity apiOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "审核成功";
        try {
            orderService.doOrderAudit(apiOrder);
            systemService.addLog("订单[" + apiOrder.getId() + "]审核成功", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (RuntimeException e) {
            logger.error("error:", e);
            message = "互联互通授权失败";
            systemService.addLog("订单[" + apiOrder.getId() + "]审核失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "审核失败";
            systemService.addLog("订单[" + apiOrder.getId() + "]审核失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转至审核员列表页面
     */
    @RequestMapping(params = "goCheckUserList")
    public ModelAndView goCheckUserList(String id, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/checkUserList");
        model.addObject("id", id);
        return model;
    }

    /**
     * 审核员列表数据
     */
    @RequestMapping(params = "checkUserDatagrid")
    public void checkUserDatagrid(TSBaseUser tsBaseUser, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSBaseUser.class, dataGrid);
        TSUser user = ResourceUtil.getSessionUser();
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsBaseUser, request.getParameterMap());
        try {
            //自定义追加查询条件
            if(StringUtil.isNotEmpty(user.getUserName())){
                List<String> baseUsers = new ArrayList<>();
                String hql0 = "from TSBaseUser where 1 = 1 AND userKey = ?";
                List<TSBaseUser> baseUserList = systemService.findHql(hql0,"开放平台审核员");
                for (TSBaseUser tsBaseUser1:baseUserList){
                    baseUsers.add(tsBaseUser1.getUserKey());
                }
                cq.in("userKey", baseUsers.toArray());
            }
        } catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 订单提交给指定审核员
     *
     * @param apiOrder
     * @param request
     * @return
     */
    @RequestMapping(params = "orderSubmitToCheck")
    @ResponseBody
    public AjaxJson orderSubmitToCheck(ApiOrderEntity apiOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try {
            String apiData = request.getParameter("apiData");
            JSONArray data = JSONArray.parseArray(apiData);
            com.alibaba.fastjson.JSONObject jb = (com.alibaba.fastjson.JSONObject)data.get(0);
            ApiOrderEntity apiOrderEntity = orderService.get(ApiOrderEntity.class, apiOrder.getId());
            apiOrderEntity.setAuditStatus(CustomConstant.AUDIT_WAIT);
            if (jb.get("auditBy") != null) {
                apiOrderEntity.setAuditBy(jb.get("auditBy").toString());
            }
            orderService.saveOrUpdate(apiOrderEntity);
            systemService.addLog("订单[" + apiOrder.getId()+"]提交审核成功", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:",e);
            message = "提交审核失败";
            systemService.addLog("订单[" + apiOrder.getId()+"]提交审核失败", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 订购应用 跳转计费模型选择页面
     *
     * @param appId
     * @return
     */
    @RequestMapping(params = "appSelect")
    public ModelAndView appInfoSelect(String appId) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderConfigCharging");
        model.addObject("appId", appId);
        return model;
    }


    /**
     * 删除服务订单表
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiOrderEntity apiOrder, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        apiOrder = systemService.getEntity(ApiOrderEntity.class, apiOrder.getId());
        message = "能力订单表删除成功";
        try {
            orderService.delete(apiOrder);
            systemService.addLog("订单[" + apiOrder.getId() + "]删除", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力订单表删除失败";
            systemService.addLog("订单[" + apiOrder.getId() + "]删除", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 应用订购服务 跳转服务选择页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "appApiSelect")
    public ModelAndView apiInfoSelect(String appId, String appType,HttpServletRequest request) {

        TSUser sessionUser = ResourceUtil.getSessionUser();
        String id = sessionUser.getId();
        String rCode =orderService.findRoleById(id);
        ModelAndView model = new ModelAndView("com/kd/op/order/appApiSelect");
        model.addObject("appId", appId);
        model.addObject("appType", appType);
        model.addObject("rCode",rCode);
        return model;
    }

    /**
     * 应用场景选择 跳转页面
     *
     * @param appId
     * @param request
     * @return
     */
    @RequestMapping(params = "appSceneSelect")
    public ModelAndView appSceneSelect(String appId, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/appSceneSelect");
        model.addObject("appId", appId);
        return model;
    }

    /**
     * 加载审核订单的服务
     *
     * @param apiInfo
     * @param orderId
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridAuditApi")
    public void datagridAuditApi(ApiInfoEntity apiInfo, String orderId, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        try {
            //获取当前订单的服务列表
            String[] idList = orderService.getOrderDetailAPiOrSceneIds(orderId);
            cq.in("id", idList);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 加载审核订单的场景
     *
     * @param apiScene
     * @param orderId,
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridAuditScene")
    public void datagridAuditScene(ApiSceneEntity apiScene, String orderId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiSceneEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiScene, request.getParameterMap());
        try {
            //获取当前订单的服务列表
            String[] idList = orderService.getOrderDetailAPiOrSceneIds(orderId);
            cq.in("id", idList);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiSceneService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 跳转场景的服务列表查看页面
     *
     * @param apiScene
     * @param request
     * @return
     */
    @RequestMapping(params = "sceneApiList")
    public ModelAndView sceneApiList(ApiSceneEntity apiScene, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/order/sceneApiList");
        model.addObject("sceneId", apiScene.getId());
        return model;
    }

    /**
     * 加载场景的服务列表
     *
     * @param apiInfo
     * @param sceneId
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridSceneApiList")
    public void datagridSceneApiList(ApiInfoEntity apiInfo, String sceneId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        try {
            //获取当前订单的服务列表
            String[] idList = orderService.getSceneApiIds(sceneId);
            cq.in("id", idList);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 订单 订购中选择场景或者服务 跳转计费模型页面
     *
     * @param sceneId
     * @param apiId
     * @param req
     * @return
     */
    @RequestMapping(params = "orderConfigCharging")
    public ModelAndView orderConfigCharging(String sceneId, String apiId, String orderId, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderConfigCharging");
        String selectedId = req.getParameter("selectedId");
        if (selectedId != null && !selectedId.isEmpty()) {
            model.addObject("ss", selectedId);
        }
        if (StringUtil.isNotEmpty(sceneId)) {
            model.addObject("sceneId", sceneId);
        } else if (StringUtil.isNotEmpty(apiId)) {
            model.addObject("apiId", apiId);
        }
        if (StringUtil.isNotEmpty(orderId)) {
            model.addObject("orderId", orderId);
        }

        return model;
    }

    /**
     * 订单 订购中选择场景或者服务 加载子页面
     *
     * @param type
     * @param ss
     * @param apiId
     * @param sceneId
     * @return
     */
    @RequestMapping(params = "orderConfigChargingList")
    public ModelAndView orderConfigChargingList(String type, String ss, String apiId, String sceneId, String orderId) {
        ModelAndView model = new ModelAndView("com/kd/op/order/orderConfigChargingList");
        model.addObject("type", type);
        model.addObject("ss", ss);
        if (StringUtil.isNotEmpty(sceneId)) {
            model.addObject("sceneId", sceneId);
        } else if (StringUtil.isNotEmpty(apiId)) {
            model.addObject("apiId", apiId);
        }
        if (StringUtil.isNotEmpty(orderId)) {
            model.addObject("orderId", orderId);
        }
        return model;
    }

    /**
     * 加载子页面数据
     *
     * @param charge
     * @param apiId
     * @param sceneId
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridOrderCharge")
    public void datagridOrderCharge(ApiChargeMode charge, String apiId, String sceneId, String orderId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiChargeMode.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charge, request.getParameterMap());
        try {
            String[] idList = orderService.getChargeIdsByApiOrScene(apiId, sceneId, orderId);
            cq.in("id", idList);
            cq.eq("type", charge.getType());
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.chargeModeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 订单可选择的服务列表加载
     *
     * @param apiInfo
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "orderApidatagrid")
    public void orderApidatagrid(ApiInfoEntity apiInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //修改为模糊查询
//        if (StringUtil.isNotEmpty(apiInfo.getApiName())) {
//            apiInfo.setApiName("*" + apiInfo.getApiName() + "*");
//        }
        CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class, dataGrid);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInfo);
        //发布状态
        cq.eq("apiPublishStatus", 1);


        if (StringUtil.isNotEmpty(apiInfo.getGroupName())) {
            String s = StringTransUtil.stringReplace(apiInfo.getGroupName());
            if(s.equals("互联互通")){
                apiInfo.setGroupName("互联互通");
            }

        }else{
           // apiInfo.setGroupName("!互联互通");
            cq.notEq("groupName","互联互通");
            //apiApp.setAppNamesetAppName("*" + s + "*");
        }
        //可用状态
        cq.eq("apiStatus", 1);
        //可见状态
//        cq.eq("apiVisibleStatus",1);
        //审核状态
        cq.eq("apiAuditStatus", 2);
        //根据不用的appType加上条件

        cq.add();
        this.apiInfoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "orderSceneDatagrid")
    public void orderSceneDatagrid(ApiSceneEntity apiScene, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiSceneEntity.class, dataGrid);
        //修改为模糊查询
//        if(StringUtil.isNotEmpty(apiScene.getSceneName())) {
//            apiScene.setSceneName("*"+apiScene.getSceneName()+"*");
//        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiScene, request.getParameterMap());
        //过滤审核通过的
        cq.eq("sceneAuditStatus", 2);
        cq.add();
        this.apiSceneService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 应用订购
     *
     * @return
     */
    @RequestMapping(params = "appConfigList")
    public ModelAndView configList() {
        return new ModelAndView("com/kd/op/app/appConfigList");
    }

    /**
     * easyui AJAX请求数据
     * 根据用户名，审核成功，发布成功查询
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
                cq.eq("auditStatus", CustomConstant.AUDIT_PASS);
                cq.eq("publishStatus", CustomConstant.PUBLIS_PASS);
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "appChargeSelect")
    public ModelAndView appChargeSelect(String appId) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appOrder");
        model.addObject("appId", appId);
        return model;
    }

    //根据appid查询chargeid，查询计费模型
    @RequestMapping(params = "datagridCharge")
    public void datagridCharge(ApiChargeMode charge, String appId, String orderId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiChargeMode.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charge, request.getParameterMap());
        try {
            String[] idList = orderService.getChargeIdsByAppId(appId);
            cq.in("id", idList);
//            cq.eq("type",charge.getType());
            cq.eq("type", 1);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        cq.add();
        this.chargeModeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 从应用订购过来的直接保存订单
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "saveAppOrder")
    @ResponseBody
    public JSONObject saveAppOrder(ApiOrderEntity apiOrder, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String chargeId = request.getParameter("chargeId");
        try {
            orderService.saveOrUpdateOrderId(apiOrder, request);
            if (StringUtil.isNotEmpty(apiOrder.getAppId())) {
                result.put("success", true);
                result.put("msg", "订购成功，请到订单中查看");
            } else if (StringUtil.isNotEmpty(apiOrder.getId())) {
                result.put("success", true);
                result.put("msg", "订单更新成功");
            }
            systemService.addLog("能力订单[" + apiOrder.getId() + "]下单", Globals.MODULE_ORDER, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            if (StringUtil.isNotEmpty(apiOrder.getAppId())) {
                result.put("success", false);
                result.put("msg", "订购失败");
            } else if (StringUtil.isNotEmpty(apiOrder.getId())) {
                result.put("success", false);
                result.put("msg", "订单更新失败");
            }
            systemService.addLog("能力订单[" + apiOrder.getId() + "]下单", Globals.MODULE_ORDER, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        return result;
    }

    //服务购买统计
    @RequestMapping(params = "apiStatisticsList")
    public ModelAndView apiStatisticsList(HttpServletRequest request, String listType) {
        return new ModelAndView("com/kd/op/order/apiStatisticsList");
    }

    //应用购买统计
    @RequestMapping(params = "appStatisticsList")
    public ModelAndView appStatisticsList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/order/appStatisticsList");
    }

    //场景购买统计
    @RequestMapping(params = "sceneStatisticsList")
    public ModelAndView sceneStatisticsList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/order/sceneStatisticsList");
    }

    /**
     * 服务购买统计列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "queryApiDatagird")
    public void queryApiDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        DataGrid apiDataGrid = orderService.queryApiDatagird(request, response, dataGrid);
        TagUtil.datagrid(response, apiDataGrid);
    }

    /**
     * 场景购买统计列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "querySceneDatagird")
    public void querySceneDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        DataGrid apiDataGrid = orderService.querySceneDatagird(request, response, dataGrid);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 应用购买统计列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "queryAppDatagird")
    public void queryAppDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        DataGrid apiDataGrid = orderService.queryAppDatagird(request, response, dataGrid);
        TagUtil.datagrid(response, dataGrid);
    }

}
