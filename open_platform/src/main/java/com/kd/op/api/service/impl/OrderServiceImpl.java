package com.kd.op.api.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAppRelaServiceI;
import com.kd.op.api.service.OrderServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.StringTransUtil;
import org.commonJar.CommonUtils;
import org.hibernate.Query;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl extends CommonServiceImpl implements OrderServiceI {

    @Autowired
    private SystemService systemService;

    @Autowired
    private ApiAppRelaServiceI appRelaServiceI;


    @Override
    public void saveOrUpdateOrder(ApiOrderEntity order, HttpServletRequest request) throws Exception {
        //用于更新订单
        ApiOrderEntity apiOrder = null;
        //用于创建订单
        ApiOrderEntity apiOrderEntity = null;

        ApiAppEntity app = null;
        //更新
        if (StringUtil.isNotEmpty(order.getId())) {
            //获取订单数据
            apiOrder = getEntity(ApiOrderEntity.class, order.getId());
            //修改审核状态  审核未通过订购 可以编辑购再次提交
            //apiOrderEntity.setAuditStatus(CustomConstant.AUDIT_TEMP);
            if (apiOrder != null) {
                //删除旧的订单详情数据
                String delOldDetail = "DELETE FROM api_order_detail WHERE order_id = ? ";
                systemService.executeSql(delOldDetail, order.getId());
            }
            //创建
        } else if (StringUtil.isNotEmpty(order.getAppId())) {
            //获取应用信息
            app = systemService.getEntity(ApiAppEntity.class, order.getAppId());
            //组装订单主信息
            apiOrderEntity = new ApiOrderEntity();
            apiOrderEntity.setAppId(app.getId());
            apiOrderEntity.setAppName(app.getAppName());
            apiOrderEntity.setMoney(0.0);
            apiOrderEntity.setOrderType(order.getOrderType());
            apiOrderEntity.setAuditStatus(CustomConstant.AUDIT_TEMP);
            apiOrderEntity.setPayStatus(CustomConstant.PAY_UNPAY);
            systemService.save(apiOrderEntity);
            systemService.addLog("下单成功订单编号为:" + apiOrderEntity.getId(), Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO, "1");
        }
        //获取选择的订购服务信息
        String apiData = request.getParameter("apiData");
        JSONArray data = JSONArray.parseArray(apiData);
        //组装订单详情信息
        List<ApiOrderDetailEntity> orderDetails = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject jb = (JSONObject) data.get(i);
            ApiOrderDetailEntity orderDetail = new ApiOrderDetailEntity();
            if (apiOrder != null) {
                orderDetail.setOrderId(order.getId());
            } else {
                if (apiOrderEntity != null) {
                    orderDetail.setOrderId(apiOrderEntity.getId());
                }
            }
            if (jb.get("apiId") != null) {
                orderDetail.setApiId(jb.get("apiId").toString());
            }
            if (jb.get("apiName") != null) {
                orderDetail.setApiName(jb.get("apiName").toString());
            }
            if (jb.get("sceneId") != null) {
                orderDetail.setSceneId(jb.get("sceneId").toString());
            }
            if (jb.get("sceneName") != null) {
                orderDetail.setSceneName(jb.get("sceneName").toString());
            }
            if (jb.get("chargeModeId") != null) {
                String chargeModeId = jb.get("chargeModeId").toString();
                ApiChargeMode apiChargeMode = systemService.get(ApiChargeMode.class, chargeModeId);
                BigDecimal price = apiChargeMode.getPrice();
                orderDetail.setChargeId(chargeModeId);
            }
            if (jb.get("resourceIds") != null) {
                orderDetail.setResourceId(jb.get("resourceIds").toString());
            }
            //处理主动推送能力的URL
            if (jb.get("apiInvokeType") != null && jb.get("apiInvokeType").toString().equals("2")) {
                if (jb.get("url") != null) {
                    orderDetail.setMethodPath(jb.get("url").toString());
                }
            }
//            //保存mq配置的tag
//            if (jb.get("pubMode") != null && jb.get("pubMode").toString().equals("2")) {
//                if (jb.get("tag") != null) {
//                    orderDetail.setMqTag(jb.get("tag").toString());
//                }
//            }
            //保存资源控制信息
            if (jb.get("resourceCtrl") != null && jb.get("resourceCtrl").toString().equals("1")) {
                if (jb.get("apiResource") != null ) {
                    String apiResource = jb.get("apiResource").toString().replace("=", "\":\"").replace("[","{").replace("]","}");
                    orderDetail.setResourceParam(apiResource);
                }
            }

            orderDetails.add(orderDetail);
        }

        systemService.batchSave(orderDetails);
    }

    @Override
    public void saveOrUpdateOrderConfig(ApiOrderEntity apiOrder,HttpServletRequest request) {

        String apiData = request.getParameter("apiData");
        JSONArray data = JSONArray.parseArray(apiData);
//        List<ApiIsvParamEntity> apiIsvParams = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject jb = (JSONObject) data.get(i);
            ApiIsvParamEntity apiIsvParamEntity = new ApiIsvParamEntity();
            String apiId=null;
            String pubmode = null;
            if (jb.get("apiId") != null) {
                apiId=jb.getString("apiId");
            }
            String sql ="DELETE FROM api_isv_param WHERE api_id = ? and app_id=?";
            systemService.executeSql(sql, apiId,apiOrder.getAppId());
            apiIsvParamEntity.setApiId(apiId);
            apiIsvParamEntity.setAppId(apiOrder.getAppId());
            TSUser sessionUserName = ResourceUtil.getSessionUserName();
            String userName = sessionUserName.getUserName();
            apiIsvParamEntity.setIsvId(userName);
            apiIsvParamEntity.setIsVisible(0);
           /* //处理主动推送能力的URL
            if (jb.get("apiInvokeType") != null && jb.get("apiInvokeType").toString().equals("2")) {
                if (jb.get("url") != null) {
                    apiIsvParamEntity.setParamName("path");
                    apiIsvParamEntity.setParamValue(jb.get("url").toString());
                }
            }
            //保存webService配置
            if (jb.get("pubMode") != null && jb.get("pubMode").toString().equals("4")) {
                if (jb.get("webServiceInfo") != null) {
                    apiIsvParamEntity.setParamName("webService");
                    String webServiceInfo = jb.get("webServiceInfo").toString().replace("[", "{").replace("]", "}");
                    String replace = webServiceInfo.replace("=", "\":\"");
                    apiIsvParamEntity.setParamValue(replace);
                }
            }*/
            if (jb.get("webServiceInfo") != null){
                JSONObject noPubMode = null;
                String getWebServiceIn = jb.getString("webServiceInfo");
                String webServiceArry[] = jb.getString("webServiceInfo").split(",");
                String stringTemp = "";
                String webServiceIn = null;
                for(int j = 0;j < webServiceArry.length - 1;j++){
                    stringTemp = stringTemp + webServiceArry[j].replaceFirst("=","\":\"") + ",";
                }
                stringTemp = stringTemp + webServiceArry[webServiceArry.length-1].replaceFirst("=","\":\"");
                webServiceIn = stringTemp.replace("[", "{").replace("]", "}");

                JSONObject webServiceJson = JSONObject.parseObject(webServiceIn);
                pubmode = webServiceJson.getString("pubmode");
                if(pubmode != null && pubmode.equals("0")){
                    apiIsvParamEntity.setParamName("path");
                    apiIsvParamEntity.setParamValue(webServiceJson.getString("httpsAddress"));
                }

                //保存webService配置
                if (pubmode != null && pubmode.equals("2")) {
                    apiIsvParamEntity.setParamName("webService");
                    for(String key : webServiceJson.keySet()){
                        String returnInfo = "{\"methodName\":\""+webServiceJson.getString("methodName")+"\",\"namespaceURI\":\""+webServiceJson.getString("namespaceURI")+"\",\"endpoint\":\""+webServiceJson.getString("endpoint")+"\"}";
                        apiIsvParamEntity.setParamValue(returnInfo);
                    }
                }
                //保存mq配置
                if (pubmode != null && pubmode.equals("4")) {
                    apiIsvParamEntity.setParamName("MQ");
                    String returnInfo = "{\"topic\":\""+webServiceJson.getString("topic")+"\",\"tag\":\""+webServiceJson.getString("tag")+"\"}";
                    apiIsvParamEntity.setParamValue(returnInfo);
                }
            }
            systemService.save(apiIsvParamEntity);
        }
    }

    @Override
    public String getOrderDetailJSON(ApiOrderEntity apiOrder) throws Exception {
        //查询订单详情
        String queryOrderDetail = "FROM ApiOrderDetailEntity WHERE orderId = ?";
        List<ApiOrderDetailEntity> orderDetails = systemService.findHql(queryOrderDetail, apiOrder.getId());
        ApiOrderEntity apiOrderEntity = systemService.get(ApiOrderEntity.class, apiOrder.getId());
        String appId = apiOrderEntity.getAppId();
        String sql ="select param_value from api_isv_param where api_id=? and app_id=?";
        ArrayList<Object> list = new ArrayList<>();
        for (ApiOrderDetailEntity orderDetail : orderDetails) {
            String apiId = orderDetail.getApiId();
            Map<String, Object> map = systemService.findOneForJdbc(sql, apiId, appId);
            if (map!=null && map.get("param_value") != null) {
                String paramValue = (String) map.get("param_value");
                JSONObject o = (JSONObject) JSONObject.toJSON(orderDetail);
                o.put("paramValue", paramValue);
                o.put("fromDatabase","1");
                list.add(o);
            } else {
                list.add(orderDetail);
            }
        }
        String data = JSONHelper.toJSONString(list);
        return data;
    }

    @Override
    public List<ApiOrderDetailEntity> getOrderDetail(ApiOrderEntity apiOrder) throws Exception {
        //查询订单详情
        String queryOrderDetail = "FROM ApiOrderDetailEntity WHERE orderId = ?";
        List<ApiOrderDetailEntity> orderDetails = systemService.findHql(queryOrderDetail, apiOrder.getId());
        return orderDetails;
    }

    @Override
    public String[] getOrderDetailAPiOrSceneIds(String orderId) throws Exception {
        ApiOrderEntity apiOrderEntity = getEntity(ApiOrderEntity.class, orderId);
        String[] idList = new String[]{"XXXXXXXXX"};//防止报空
        //查询订单详情
        String queryOrderDetail = "FROM ApiOrderDetailEntity WHERE orderId = ?";
        List<ApiOrderDetailEntity> apiOrderDetails = systemService.findHql(queryOrderDetail, apiOrderEntity.getId());
        if (apiOrderDetails != null && apiOrderDetails.size() > 0) {
            idList = new String[apiOrderDetails.size()];
            for (int i = 0; i < apiOrderDetails.size(); i++) {
                if (CustomConstant.API.equals(apiOrderEntity.getOrderType())) {
                    idList[i] = apiOrderDetails.get(i).getApiId();
                }
                if (CustomConstant.SCENE.equals(apiOrderEntity.getOrderType())) {
                    idList[i] = apiOrderDetails.get(i).getSceneId();
                }

            }
        }
        return idList;
    }

    @Override
    public String[] getSceneApiIds(String sceneId) throws Exception {
        String[] idList = new String[]{"XXXXXXXXX"};//防止报空
        //查询订单详情
        String querySceneDetail = "FROM ApiSceneRelaEntity WHERE sceneId = ?";
        List<ApiSceneRelaEntity> apiSceneDetails = systemService.findHql(querySceneDetail, sceneId);
        if (apiSceneDetails != null && apiSceneDetails.size() > 0) {
            idList = new String[apiSceneDetails.size()];
            for (int i = 0; i < apiSceneDetails.size(); i++) {
                idList[i] = apiSceneDetails.get(i).getApiId();
            }
        }
        return idList;
    }

    @Override
    public String[] getChargeIdsByApiOrScene(String apiId, String sceneId, String orderId) throws Exception {
        String[] idList = new String[]{"XXXXXXXXX"};//防止报空

        //如果有订单id则是订单查看详情显示订单中的计费模型
        if (StringUtil.isNotEmpty(orderId)) {
            //查询订单中的场景或者服务的计费模型
            if (StringUtil.isNotEmpty(apiId)) {
                //查询服务的计费模型
                String queryOrderApiCharges = "FROM ApiOrderDetailEntity WHERE apiId = ? and orderId = ?";
                List<ApiOrderDetailEntity> apiChargeModeRelas = systemService.findHql(queryOrderApiCharges, apiId, orderId);
                if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                    idList = new String[apiChargeModeRelas.size()];
                    for (int i = 0; i < apiChargeModeRelas.size(); i++) {
                        idList[i] = apiChargeModeRelas.get(i).getChargeId();
                    }
                }

            } else if (StringUtil.isNotEmpty(sceneId)) {
                //查询场景的计费模型
                String queryOrderSceneCharges = "FROM ApiOrderDetailEntity WHERE sceneId = ? and orderId = ?";
                List<ApiOrderDetailEntity> apiChargeModeRelas = systemService.findHql(queryOrderSceneCharges, sceneId, orderId);
                if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                    idList = new String[apiChargeModeRelas.size()];
                    for (int i = 0; i < apiChargeModeRelas.size(); i++) {
                        idList[i] = apiChargeModeRelas.get(i).getChargeId();
                    }
                }
            }
        } else if (StringUtil.isNotEmpty(apiId)) {
            //查询服务的计费模型
            String queryApiCharges = "FROM ApiChargeModeRela WHERE apiId = ?";
            List<ApiChargeModeRela> apiChargeModeRelas = systemService.findHql(queryApiCharges, apiId);
            if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                idList = new String[apiChargeModeRelas.size()];
                for (int i = 0; i < apiChargeModeRelas.size(); i++) {
                    idList[i] = apiChargeModeRelas.get(i).getChargeModeId();
                }
            }
        } else if (StringUtil.isNotEmpty(sceneId)) {
            //查询场景的计费模型
            String querySceneCharges = "FROM ApiSceneChargeEntity WHERE sceneId = ?";
            List<ApiSceneChargeEntity> apiSceneChargeEntities = systemService.findHql(querySceneCharges, sceneId);
            if (apiSceneChargeEntities != null && apiSceneChargeEntities.size() > 0) {
                idList = new String[apiSceneChargeEntities.size()];
                for (int i = 0; i < apiSceneChargeEntities.size(); i++) {
                    idList[i] = apiSceneChargeEntities.get(i).getChargeId();
                }
            }
        }
        return idList;
    }

    @Override
    public void doOrderAudit(ApiOrderEntity apiOrder) throws Exception {
        ApiOrderEntity apiOrderEntity = systemService.get(ApiOrderEntity.class, apiOrder.getId());
        apiOrderEntity.setAuditStatus(apiOrder.getAuditStatus());
        apiOrderEntity.setAuditMsg(apiOrder.getAuditMsg());
        systemService.saveOrUpdate(apiOrderEntity);
        systemService.addLog("订单审核成功，订单编号为:" + apiOrder.getId(), Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO, "1");
        //执行关联操作 预留
        if (CustomConstant.AUDIT_PASS.equals(apiOrderEntity.getAuditStatus())) {
            try {
                appRelaServiceI.updateChargeRela(apiOrder, this.getOrderDetail(apiOrder));
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public String[] getChargeIdsByAppId(String appId, String orderId) throws Exception {
        String[] idList = new String[]{"XXXXXXXXX"};//防止报空

        //如果有订单id则是订单查看详情显示订单中的计费模型
        if (StringUtil.isNotEmpty(orderId)) {
            //查询订单中的场景或者服务的计费模型
            if (StringUtil.isNotEmpty(appId)) {
                //查询引用的计费模型
                String queryOrderApiCharges = "FROM ApiOrderDetailEntity WHERE appId = ? and orderId = ?";
                List<ApiOrderDetailEntity> apiChargeModeRelas = systemService.findHql(queryOrderApiCharges, appId, orderId);
                if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                    idList = new String[apiChargeModeRelas.size()];
                    for (int i = 0; i < apiChargeModeRelas.size(); i++) {
                        idList[i] = apiChargeModeRelas.get(i).getChargeId();
                    }
                } else if (StringUtil.isNotEmpty(appId)) {
                    //查询服务的计费模型
                    String querySceneCharges = "FROM ApiAppChargeEntity WHERE appId = ?";
                    List<ApiAppChargeEntity> apiAppChargeEntitys = systemService.findHql(querySceneCharges, appId);
                    if (apiAppChargeEntitys != null && apiAppChargeEntitys.size() > 0) {
                        idList = new String[apiAppChargeEntitys.size()];
                        for (int i = 0; i < apiAppChargeEntitys.size(); i++) {
                            idList[i] = apiAppChargeEntitys.get(i).getChargeId();
                        }
                    }
                }
            }
        }
        return idList;
    }


    @Override
    public String[] getChargeIdsByAppId(String appId) throws Exception {
        String[] idList = new String[]{"XXXXXXXXX"};//防止报空
        //查询订单中的场景或者服务的计费模型
        if (StringUtil.isNotEmpty(appId)) {
            //查询引用的计费模型
            String queryAppCharges = "FROM AppChargeEntity WHERE app_id = ?";
            List<AppChargeEntity> apiChargeModeRelas = systemService.findHql(queryAppCharges, appId);
            if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                idList = new String[apiChargeModeRelas.size()];
                for (int i = 0; i < apiChargeModeRelas.size(); i++) {
                    idList[i] = apiChargeModeRelas.get(i).getCharge_id();
                }
            }
        }

        return idList;
    }

    @Override
    public void saveOrUpdateOrderId(ApiOrderEntity order, HttpServletRequest request) throws Exception {
        //用于更新订单
        ApiOrderEntity apiOrder = null;
        //用于创建订单
        ApiOrderEntity apiOrderEntity = null;

        ApiAppEntity app = null;

        List<ApiOrderDetailEntity> orderDetails = null;

        //更新
        if (StringUtil.isNotEmpty(order.getId()) && order.getOrderType().equals("app")) {
            //获取订单数据
            apiOrder = getEntity(ApiOrderEntity.class, order.getId());
            if (apiOrder != null) {
                //删除旧的订单详情数据
                String delOldDetail = "DELETE FROM api_order_detail WHERE order_id = ? ";
                systemService.executeSql(delOldDetail, order.getId());
            }
            //创建
        } else if (StringUtil.isNotEmpty(order.getAppId())) {
            //获取应用信息
            app = systemService.getEntity(ApiAppEntity.class, order.getAppId());
            //组装订单主信息
            apiOrderEntity = new ApiOrderEntity();
            apiOrderEntity.setAppId(app.getId());
            apiOrderEntity.setAppName(app.getAppName());
            apiOrderEntity.setMoney(0.0);
            apiOrderEntity.setOrderType(order.getOrderType());
            apiOrderEntity.setAuditStatus(CustomConstant.AUDIT_TEMP);
            apiOrderEntity.setPayStatus(CustomConstant.PAY_UNPAY);
            systemService.save(apiOrderEntity);
        }

        // 根据appid查询订单表的信息
        List<ApiOrderEntity> appOrderList = systemService.findByProperty(ApiOrderEntity.class, "appId", order.getAppId());
        // 新插入订单详情
        List<ApiOrderDetailEntity> apiOrderList = CommonUtils.getApiOrderList(appOrderList, systemService);
        //获取选择的订购服务信息
        //根据app中的orderId去查询ApiOrderDetailEntity
        /*if ( apiOrderEntity.getId()!=null) {
            orderDetails = systemService.findByProperty(ApiOrderDetailEntity.class, "orderId", apiOrderEntity.getId());
        }*/
        systemService.batchSave(apiOrderList);
    }

    public DataGrid queryApiDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int total = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map> results = new ArrayList<Map>();
        int rows = dataGrid.getRows();
        int pages = dataGrid.getPage();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        String apiId = request.getParameter("apiId");
        Map<String, Object> roleMap = ResourceParams.getAuthorityUsers();
        String username = (String) roleMap.get(CustomConstant.USERNAME);
        String isFilter = (String) roleMap.get(CustomConstant.IS_FILTER);
//        String sql = "select a.id orderId,a.order_type,a.app_name,a.create_name,a.create_date,a.pay_status,b.api_name,b.scene_name from api_order a,api_order_detail b where a.id=b.order_id and a.order_type='api' and a.pay_status='1'";
        String sql = "select a.id orderId,a.order_type,a.app_name,a.create_name,a.create_date,a.pay_status,b.api_name,b.scene_name,c.group_id,c.group_name from api_order a,api_order_detail b,api_info c where a.id=b.order_id and a.order_type='api' and a.pay_status='1' and b.api_id = c.id";
        StringBuffer sb = new StringBuffer(sql);
        if (isFilter.equals("true")) {
            sb.append(" and c.create_by='" + username + "'");
        }
        if (StringUtil.isNotEmpty(groupId)) {
            String s = StringTransUtil.stringReplace(groupId);
            if(!s.equals("0")){
                sb.append(" and c.group_id='" + s + "'");
            }

        }
       /* if(isFilter.equals("true")){
             sb.append(" and b.api_id in (select c.id from ap9i_info c where  c.create_by='"+username+"'");
            if(StringUtil.isNotEmpty(groupId)){
                sb.append(" and c.group_id='"+groupId+"'");
            }
            sb.append(")");
        }else{
            if(StringUtil.isNotEmpty(groupId)){
                sb.append(" and b.api_id in (select c.id from api_info c where  c.group_id='"+groupId+"')");
            }
        }*/
        if (StringUtil.isNotEmpty(startDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(startDate);
            if(s!=null){
                sb.append(" and a.create_date>'" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(endDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(endDate);
            if(s!=null){
                sb.append(" and a.create_date<'" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(apiId)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(apiId);
            if(s!=null){
                sb.append(" and b.api_id='" + s + "'");
            }
        }
        sb.append(" ORDER BY create_date DESC");


        //查询总订单数
        List<Map<String, Object>> totalList = this.findForJdbc(sb.toString());
        if (totalList != null && totalList.size() > 0) {
            total = totalList.size();
        }
        //根据条件分页查询出一页
        list = this.findForJdbc(sb.toString(), pages, rows);
        dataGrid.setResults(list);
        dataGrid.setTotal(total);

        return dataGrid;
    }

    public DataGrid querySceneDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int total = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map> results = new ArrayList<Map>();
        int rows = dataGrid.getRows();
        int pages = dataGrid.getPage();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String sceneId = request.getParameter("sceneId");
        String sql = "select a.id orderId,a.order_type,a.app_name,a.create_name,a.create_date,a.pay_status,b.api_id,b.api_name,b.scene_id,b.scene_name from api_order a,api_order_detail b where 1=1 and a.id = b.order_id and a.pay_status =1 and a.order_type = 'scene'";
        StringBuffer sb = new StringBuffer(sql);
        if (StringUtil.isNotEmpty(sceneId)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(sceneId);
            if(s!=null){
                sb.append(" and b.scene_id = '" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(startDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(startDate);
            if(s!=null){
                sb.append(" and a.create_date>='" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(endDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(endDate);
            if(s!=null){
                sb.append(" and a.create_date<='" + s + "'");
            }
        }
        sb.append(" ORDER BY create_date DESC");
        //查询总订单数
        List<Map<String, Object>> totalList = this.findForJdbc(sb.toString());
        if (totalList != null && totalList.size() > 0) {
            total = totalList.size();
        }
        //根据条件分页查询出一页
        list = this.findForJdbc(sb.toString(), pages, rows);
        dataGrid.setResults(list);
        dataGrid.setTotal(total);
        return dataGrid;
    }

    public DataGrid queryAppDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int total = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map> results = new ArrayList<Map>();
        int rows = dataGrid.getRows();
        int pages = dataGrid.getPage();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String appid = request.getParameter("appId");
        String sql = "select a.id orderId,a.order_type,a.app_name,a.create_name,a.create_date,a.pay_status from api_order a where 1=1 and a.pay_status =1 and a.order_type = 'app'";
        StringBuffer sb = new StringBuffer(sql);
        if (StringUtil.isNotEmpty(appid)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(appid);
            if(s!=null){
                sb.append(" and a.app_id = '" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(startDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(startDate);
            if(s!=null){
                sb.append(" and a.create_date>='" + s + "'");
            }
        }
        if (StringUtil.isNotEmpty(endDate)) {
            //处理危险字符，防止sql注入
            String s = StringTransUtil.stringReplace(endDate);
            if(s!=null){
                sb.append(" and a.create_date<='" + s + "'");
            }
        }
        sb.append(" ORDER BY create_date DESC");
        //查询总订单数
        List<Map<String, Object>> totalList = this.findForJdbc(sb.toString());

        if (totalList != null && totalList.size() > 0) {
            total = totalList.size();
        }
        //根据条件分页查询出一页
        list = this.findForJdbc(sb.toString(), pages, rows);
        dataGrid.setResults(list);
        dataGrid.setTotal(total);
        return dataGrid;
    }

    @Override
    public String findRoleById(String id) {
        String sql = "SELECT r.rolecode FROM t_s_role r \n" +
                "LEFT JOIN t_s_role_user ur ON r.ID = ur.roleid\n" +
                "LEFT JOIN t_s_user u ON ur.userid=u.id WHERE u.id = :uId";
        Query query = getSession().createSQLQuery(sql).setParameter("uId", id);
        List list = query.list();
        String rCode = list.get(0).toString();
        return rCode;
    }

}