package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.service.ApiInvokeLogServiceI;
import com.kd.op.api.service.ApiMonitorService;
import com.kd.op.api.service.ApiSceneServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.DateTimeRangeUtil;
import com.kd.op.util.JSONSerializer;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private final static Logger logger = Logger.getLogger(MonitorController.class);

    @Resource
    private ApiMonitorService apiMonitorService;
    @Resource
    private SystemService systemService;
    @Resource
    private ApiInvokeLogServiceI apiInvokeLogService;
    @Resource
    private ApiInfoServiceI apiInfoService;



    @RequestMapping(params = "getMonitorData")
    @ResponseBody
    public JSONObject getMonitorData(){
        JSONObject result = new JSONObject();
        //处理今天实时接口数量
        Map<Integer,Integer> countApi = apiMonitorService.countInDay();
        List<Integer> apiCounts = new ArrayList<>();
        for(int i = 0;i <= new Date().getHours();i++){
            apiCounts.add(countApi.get(i) == null?0:countApi.get(i));
        }
        result.put("apiCounts",apiCounts);
        //处理每个分组对应今天的实时接口数量
        List<String> groupNames = new ArrayList<>();
        List<List<Integer>> groupDatas = new ArrayList<>();
        List<ApiGroupEntity> groups = apiMonitorService.loadAll(ApiGroupEntity.class);

        for(ApiGroupEntity group:groups){
            groupNames.add(group.getGroupName());
            Map<Integer,Integer> groupCount = apiMonitorService.countInDayByGroup(group.getId());
            List<Integer> groupData = new ArrayList<>();
            for(int i = 0;i <= new Date().getHours();i++){
                groupData.add(groupCount.get(i) == null?0:groupCount.get(i));
            }
            groupDatas.add(groupData);
        }
        result.put("groupNames",groupNames);
        result.put("groupDatas",groupDatas);
        return result;
    }

    @RequestMapping(params = "apiMonitor")
    public ModelAndView apiMonitor(ApiInfoEntity apiInfo, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/apiMonitor");
//        旧版监控
//        List<ApiGroupEntity> groups = apiMonitorService.loadAll(ApiGroupEntity.class);
//        model.addObject("groups",groups);
        return model;
    }


    @RequestMapping(params = "appOrderMonitor")
    public ModelAndView appOrderMonitor() {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/appOrderMonitor");
        return model;
    }

    /**
     * 应用交易图表数据
     * @param request
     * @return
     */
    @RequestMapping(params = "getAppOrderMonitorData")
    @ResponseBody
    public JSONObject getAppOrderMonitorData(HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
//        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Integer> count = new ArrayList<>();//app每日总数统计
        JSONObject result = new JSONObject();
        String startStr = request.getParameter("start");
        String endStr = request.getParameter("end");
        String appId = request.getParameter("appId");
        String start = startStr + " 00:00:00";
        String end = endStr + " 23:59:59";
        try {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(sdf.parse(start));
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(sdf.parse(end));
            Map<String, Map<String, Object>> mapList = apiMonitorService.countAppBetweenTimes(start, end, appId);
            if (endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 <= 1) {
                //如果选择的时间间隔小于1天 按小时显示
                if(startStr!=null&&StringUtils.isNotEmpty(startStr)&&endStr!=null&&StringUtils.isNotEmpty(endStr)) {
                    List<String> timeScale = DateTimeRangeUtil.getTimeScale(startStr, endStr);
                    for (String s : timeScale) {
                        StringBuffer stringBuffer = new StringBuffer(s);
                        xIndex.add(stringBuffer.append(":00:00").toString());
                        if (mapList.get(s) != null) {
                            Map<String, Object> map = mapList.get(s);
                            count.add(map.get("count") == null ? 0 : Integer.parseInt(map.get("count") + ""));
                        } else {
                            count.add(0);
                        }
                    }
                }
            } else {
                if(startStr!=null&&StringUtils.isNotEmpty(startStr)&&endStr!=null&&StringUtils.isNotEmpty(endStr)) {
                    List<String> timeScale = DateTimeRangeUtil.getTimeScale(startStr, endStr);
                    xIndex.addAll(timeScale);
                    for (String s : timeScale) {
                        if (mapList.get(s) != null) {
                            Map<String, Object> map = mapList.get(s);
                            count.add(map.get("count") == null ? 0 : Integer.parseInt(map.get("count") + ""));
                        } else {
                            count.add(0);
                        }
                    }
                }

            }
//                if (endCal.getTimeInMillis()/1000/60/60/24-startCal.getTimeInMillis()/1000/60/60/24>1 && endCal.getTimeInMillis()/1000/60/60/24-startCal.getTimeInMillis()/1000/60/60/24<=31){
//                //如果选择的时间间隔小于31天 大于1天 按天显示
//                for (; startCal.before(endCal); startCal.add(Calendar.HOUR_OF_DAY, 1)) {
//                    //获取每一天的日期
//                    String dayStr = sdf1.format(startCal.getTime());
//                    xIndex.add(dayStr);
//                }
//                for (String index : xIndex) {
//                    if (mapList.get(index) != null) {
//                        Map<String, Object> map = mapList.get(index);
//                        count.add(map.get("count") == null ? 0 : Integer.parseInt(map.get("count") + ""));
//                    } else {
//                        count.add(0);
//                    }
//                }
//            } else if (endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 > 31 && endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 <= 366) {
//                //如果选择的时间间隔小于365天 大于31天 按月显示
//                for (; startCal.before(endCal); startCal.add(Calendar.DAY_OF_MONTH, 1)) {
//                    //获取月份
//                    String dayStr = sdf2.format(startCal.getTime());
//                    xIndex.add(dayStr);
//                }
//                for (String index : xIndex) {
//                    if (mapList.get(index) != null) {
//                        Map<String, Object> map = mapList.get(index);
//                        count.add(map.get("count") == null ? 0 : Integer.parseInt(map.get("count") + ""));
//                    } else {
//                        count.add(0);
//                    }
//                }
//
//            } else {
//                //按年显示
//                for (; startCal.before(endCal); startCal.add(Calendar.MONTH, 1)) {
//                    String yearStr = sdf3.format(startCal.getTime());
//                    //获取年份
//                    xIndex.add(yearStr);
//                }
//                for (String index : xIndex) {
//                    if (mapList.get(index) != null) {
//                        Map<String, Object> map = mapList.get(index);
//                        count.add(map.get("count") == null ? 0 : Integer.parseInt(map.get("count") + ""));
//                    } else {
//                        count.add(0);
//                    }
//                }
//
//            }
//
        } catch (Exception e) {
            logger.error("error:",e);
        }
        result.put("xIndex", xIndex);
        result.put("count", count);
        return result;
    }


    @RequestMapping(params = "getMonitorSortData")
    @ResponseBody
    public JSONObject getAppOrderSortData(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String type = request.getParameter("type");
        String start = request.getParameter("start");
        start = start + " 00:00:00";
        String end = request.getParameter("end");
        end = end + " 23:59:59";
        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Integer> count = new ArrayList<>();//y轴的数值
        try {
            if(type!=null&& StringUtils.isNotEmpty(type)){
                //应用交易排名
                if (type.equals("appOrder")) {
                    List<Map<String, String>> maps = apiMonitorService.appOrderSortBetweenTimes(start, end);
                    for (Map<String, String> map : maps) {
                        xIndex.add(map.get("appName"));
                        if (map.get("COUNT") != null) {
                            count.add(Integer.parseInt(String.valueOf(map.get("COUNT"))));
                        } else {
                            count.add(0);
                        }
                    }
                }
                //能力调用排名
                if (type.equals("apiInvoke")) {
                    List<Map<String, String>> maps = apiMonitorService.apiInvokeSortBetweenTimes(start, end);
                    for (Map<String, String> map : maps) {
                        ApiInfoEntity apiInfo = systemService.get(ApiInfoEntity.class, map.get("apiId"));
                        xIndex.add(apiInfo.getApiName());
                        if (map.get("COUNT") != null) {
                            count.add(Integer.parseInt(String.valueOf(map.get("COUNT"))));
                        } else {
                            count.add(0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("error:",e);
        }
        result.put("xIndex", xIndex);
        result.put("count", count);
        return result;
    }

    @RequestMapping(params = "consumerMonitor")
    public ModelAndView consumerMonitor(ApiInfoEntity apiInfo, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/consumerMonitor");
        TSUser user = ResourceUtil.getSessionUserName();
        String userName = user.getUserName();
        List<ApiAppEntity> apps = systemService.findByProperty(ApiAppEntity.class, "createBy", userName);
//        List<ApiAppEntity> apps = systemService.loadAll(ApiAppEntity.class);
        model.addObject("apps", apps);
        return model;
    }

    /**
     * 日志调用跳转
     * @param apiInfo
     * @param req
     * @return
     */
    @RequestMapping(params = "invokeMonitor")
    public ModelAndView invokeMonitor(ApiInfoEntity apiInfo, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/invokeMonitor");
        return model;
    }


    /**
     * 加载场景选择下拉列表
     */
    @RequestMapping(params = "invokeSceneDatagrid")
    @ResponseBody
    public List<ApiSceneEntity> invokeSceneDatagrid() {
        String hql ="from ApiSceneEntity where sceneAuditStatus=2";
        List<ApiSceneEntity> results = systemService.findHql(hql);
        return results;
    }

    /**
     * 加载应用选择下拉列表
     */
    @RequestMapping(params = "invokeAppDatagrid")
    @ResponseBody
    public List<ApiAppEntity> invokeAppDatagrid() {
        String hql ="from ApiAppEntity ";
        List<ApiAppEntity> results = systemService.findHql(hql);
        return results;
    }

    /**
     * 加载服务选择下拉列表
     */
    @RequestMapping(params = "invokeApiDatagrid")
    @ResponseBody
    public List<ApiInfoEntity> invokeApiDatagrid(String groupId,HttpServletRequest request) {
        List<ApiInfoEntity> results =  apiMonitorService.invokeApiDatagrid(groupId);
        return results;
    }

    @RequestMapping(params = "getConsumerMonitorData")
    @ResponseBody
    public JSONObject getConsumerMonitorData(HttpServletRequest request){
        JSONObject result = new JSONObject();
        String start = request.getParameter("start");
        start = start + " 00:00:00";
        String end = request.getParameter("end");
        end = end + " 23:59:59";
        String appId = request.getParameter("appId");
        List<String> appNames = new ArrayList<>();
        JSONArray datas = new JSONArray();
        JSONArray items = new JSONArray();
        try{
            List<Map<String, Object>> list = apiMonitorService.countAppLog(start,end,appId);
            for(Map<String,Object> map:list){
                appNames.add(map.get("app_name") + "");
                JSONObject obj = new JSONObject();
                obj.put("value",map.get("count"));
                obj.put("name",map.get("app_name"));
                datas.add(obj);
                //拼接表格数据
                if(StringUtil.isNotEmpty(map.get("app_id"))) {
                	 JSONObject json = new JSONObject();
                	 json.put("app_id",map.get("app_id"));
                     json.put("app_name",map.get("app_name"));
                     json.put("requestFlowSize",map.get("requestFlowSize"));
                     json.put("responseFlowSize",map.get("responseFlowSize"));
                     json.put("successNum",map.get("successNum"));
                     json.put("faildNum",map.get("faildNum"));
                     items.add(json);
                }
               
            }

        }catch(Exception e){
            logger.error("error:",e);
        }
        result.put("appNames",appNames);
        result.put("datas",datas);
        result.put("items",items);
        return result;
    }

    @RequestMapping(params = "getConsumerMonitorData1")
    @ResponseBody
    public JSONArray getConsumerMonitorData1(HttpServletRequest request){
        JSONArray result = new JSONArray();
        String appId = request.getParameter("appId");
        try{
            List<ApiInvokeLogEntity> logs = systemService.findByProperty(ApiInvokeLogEntity.class,"appId",appId);
            for(ApiInvokeLogEntity log:logs){
                JSONObject json = new JSONObject();
                json.put("app_id",log.getApiId());
                json.put("app_name",log.getAppName());
                json.put("requestFlowSize",log.getRequestFlowSize());
                json.put("responseFlowSize",log.getResponseFlowSize());
                //json.put("invokeResult",log.getInvokeResult());
                result.add(json);
            }

        }catch(Exception e){
            logger.error("error:",e);
        }
        return result;
    }

    @RequestMapping(params = "getApisByGroup")
    @ResponseBody
    public JSONArray getApisByGroup(String groupId){
        JSONArray array = new JSONArray();
        List<ApiInfoEntity> appiInfos = new ArrayList<>();
        if(groupId == null || groupId.isEmpty()){
            appiInfos = systemService.loadAll(ApiInfoEntity.class);
        }else{
            appiInfos = apiMonitorService.findByProperty(ApiInfoEntity.class,"groupId",groupId);
        }
        for(ApiInfoEntity api:appiInfos){
            JSONObject json = new JSONObject();
            json.put("id",api.getId());
            json.put("name",api.getApiName());
            array.add(json);
        }
        return array;
    }
    
    @RequestMapping(params = "getApisByApp")
    @ResponseBody
    public List<ApiAppRelaEntity> getApisByApp(String appId){
    	
        List<ApiAppRelaEntity> appiInfos = apiMonitorService.findByProperty(ApiAppRelaEntity.class,"appId",appId);
        
        return appiInfos;
    }

    /**
     * 实例监控页面
     * @param req
     * @return
     */
    @RequestMapping(params = "instanceMonitor")
    public ModelAndView instanceMonitor(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/instanceMonitor");
        return model;
    }

    @RequestMapping(params = "getInstanceList")
    @ResponseBody
    public JSONObject getInstanceList(){
        JSONObject result = new JSONObject();
        return result;
    }

    /**
     * 实例详情页面
     * @param req
     * @return
     */
    @RequestMapping(params = "instanceDetail")
    public ModelAndView instanceDetail(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/instanceDetail");
        return model;
    }

    /**
     * 统计分析页面
     * @param req
     * @return
     */
    @RequestMapping(params = "countAnalyse")
    public ModelAndView countAnalyse(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/countAnalyse");
        List<TSUser> users = apiInfoService.getAllISV();
        model.addObject("users",users);
        return model;
    }

    /**
     * 获取关系图数据
     * @param userId 用户id
     * @return
     */
    @RequestMapping(params = "getGraphData")
    @ResponseBody
    public List<GraphNode> getGraphData(String userId){
        List<GraphNode> nodes = new ArrayList<>();
        //获取用户
        TSUser user = systemService.getEntity(TSUser.class,userId);
        nodes.add(new GraphNode("user_" + user.getId(),30,null,user.getUserName()));

        List<ApiAppEntity> apps = systemService.findByProperty(ApiAppEntity.class,"createBy",user.getUserName());
        for(ApiAppEntity app:apps){
            //添加应用节点
            nodes.add(new GraphNode("app_" + app.getId(),20,"user_" + user.getId(),app.getAppName()));

            List<ApiAppRelaEntity> relas = systemService.findByProperty(ApiAppRelaEntity.class,"appId",app.getId());
            for(ApiAppRelaEntity rela:relas){
                ApiInfoEntity info = systemService.getEntity(ApiInfoEntity.class,rela.getApiId());
                if(info != null){
                    //添加能力中心节点
                    GraphNode groupNode = new GraphNode("group_" + app.getId() + "_" + info.getGroupId(),10,
                            "app_" + app.getId(),info.getGroupName());
                    if(!groupNode.isContains(nodes)){
                        nodes.add(groupNode);
                    }
                    GraphNode infoNode = new GraphNode("api_" + app.getId() + "_" + info.getGroupId() + "_" + info.getId(),
                            5,"group_" + app.getId() + "_" + info.getGroupId(),info.getApiName());
                    if(!infoNode.isContains(nodes)){
                        nodes.add(infoNode);
                    }
                }
            }
        }
        return nodes;
    }

    /**
     * 统计分析页面节点详情数据页面
     * @param type 统计节点的类型 app:应用，group：能力中心，api:服务
     * @params id 应用，能力中心或者服务的id
     * @return
     */
    @RequestMapping(params = "countNodeData")
    public ModelAndView countNodeData(String type,String id,String appId) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/countNodeData");
        model.addObject("type",type);
        model.addObject("id",id);
        model.addObject("appId",appId);
        return model;
    }


    @RequestMapping(params="getCountNodeData")
    @ResponseBody
    public JSONObject getCountNodeData(HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
        JSONObject result = new JSONObject();
        String nodeType = request.getParameter("nodeType");
        String nodeId = request.getParameter("nodeId");
        String startStr = request.getParameter("start");
        String endStr = request.getParameter("end");
        String appId = request.getParameter("appId");

        List<String> xIndex = new ArrayList<>();//x轴的数值
        List<Integer> count = new ArrayList<>();//api每日总数统计
        try{
            Map<String,Map<String,Object>> mapList = apiMonitorService.countBetweenTimeByType(startStr,endStr,nodeType,nodeId,appId);

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(sdf.parse(startStr));

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(sdf.parse(endStr));
            //判断间隔时间，如果间隔时间超过一天，就按天统计，否则按小时统计
            int space = Calendar.DAY_OF_YEAR;
            SimpleDateFormat formateType = sdf1;
            long beginTime = startCal.getTime().getTime();
            long endTime = endCal.getTime().getTime();
            long betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24));
            long surplus = (long)((endTime - beginTime) % (1000 * 60 * 60 *24));
            //如果两个时间间隔超过1天就按天进行统计，否则就按小时进行统计
            if(betweenDays == 0 || (betweenDays == 1 && surplus == 0)){//按小时进行统计
                space = Calendar.HOUR_OF_DAY;
                formateType = hourSdf;
            }
            endCal.add(space,1);

            for(;startCal.before(endCal);startCal.add(space,1)){
                //获取每一天的日期
                String dayStr = formateType.format(startCal.getTime());
                xIndex.add(dayStr);
                if(mapList.get(dayStr) != null){
                    Map<String,Object> map = mapList.get(dayStr);
                    count.add(map.get("count") == null?0:Integer.parseInt(map.get("count") + ""));
                }else{
                    count.add(0);
                }
            }
        }catch(Exception e){
            logger.error("error:",e);
        }
        result.put("xIndex",xIndex);
        result.put("count",count);
        return result;
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiInvokeLogEntity apiInvokeLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiInvokeLogEntity.class, dataGrid);
        //修改为模糊查询
        if(StringUtil.isNotEmpty(apiInvokeLog.getApiName())) {
            String s = StringTransUtil.stringReplace(apiInvokeLog.getApiName());
            apiInvokeLog.setApiName("*"+s+"*");
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiInvokeLog, request.getParameterMap());
        try{
            //自定义追加查询条件
            String query_invokeTime_begin = request.getParameter("startDate");
            String query_invokeTime_end = request.getParameter("endDate");
            String nodeType = request.getParameter("nodeType");
            String nodeId = request.getParameter("nodeId");
            if(StringUtil.isNotEmpty(query_invokeTime_begin)){
                cq.ge("invokeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_invokeTime_begin));
            }
            if(StringUtil.isNotEmpty(query_invokeTime_end)){
                cq.le("invokeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_invokeTime_end));
            }

            //根据nodeType来判断服务id的范围
            List<String> apiIds = new ArrayList<>();
            if("app".equals(nodeType)){
                List<ApiAppRelaEntity> relas = systemService.findByProperty(ApiAppRelaEntity.class,"appId",nodeId);
                relas.forEach(rela->{
                    apiIds.add(rela.getApiId());
                });
            }else if("group".equals(nodeType)){
                List<ApiInfoEntity> apis = systemService.findByProperty(ApiInfoEntity.class,"groupId",nodeId);
                apis.forEach(api->{
                    apiIds.add(api.getId());
                });
            }else{
                apiIds.add(nodeId);
            }
            cq.in("apiId",apiIds.toArray());
        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.apiInvokeLogService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params="apiGraph")
    public ModelAndView apiGraph(){
        ModelAndView model = new ModelAndView("com/kd/op/monitor/apiGraph");
        List<ApiGroupEntity> groups = apiMonitorService.loadAll(ApiGroupEntity.class);
        model.addObject("groups",groups);
        return model;
    }

    /**
     * 获取关系图数据
     * @param apiId 服务id
     * @return
     */
    @RequestMapping(params = "getApiGraph")
    @ResponseBody
    public List<GraphNode> getApiGraph(String apiId){
        List<GraphNode> nodes = new ArrayList<>();
        if(apiId == null || apiId.isEmpty()){
            return nodes;
        }
        //获取当前的服务
        ApiInfoEntity api = systemService.getEntity(ApiInfoEntity.class,apiId);
        nodes.add(new GraphNode("api_" + api.getId(),30,null,api.getApiName()));

        List<ApiAppRelaEntity> relas = systemService.findByProperty(ApiAppRelaEntity.class,"apiId",api.getId());
        for(ApiAppRelaEntity rela:relas){
            //添加应用节点
            nodes.add(new GraphNode("app_" + rela.getAppId(),20,"api_" + api.getId(),rela.getAppName()));
            //添加应用对应的用户节点
            ApiAppEntity app = systemService.getEntity(ApiAppEntity.class,rela.getAppId());
            nodes.add(new GraphNode("user_" + app.getId() + "_" + app.getCreateBy(),10,
                    "app_" + app.getId(),app.getCreateBy()));
        }
        return nodes;
    }



    //跳转至系统操作日志页面
    @RequestMapping(params = "systemOperationMonitor")
    public ModelAndView logList(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/systemOperationMonitor");
        return model;
    }

    //系统操作日志页面展示
    @RequestMapping(params = "systemLogDatagrid")
    @ResponseBody
    public void systemLogDatagrid(TSLog tsLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSLog.class, dataGrid);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsLog, request.getParameterMap());
        try {
            //自定义追加查询条件
            //时间范围查询条件
            String operatetime_begin = request.getParameter("startDate");
            String operatetime_end = request.getParameter("endDate");
            if(StringUtil.isNotEmpty(operatetime_begin)){
                cq.ge("operatetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(operatetime_begin));
            }
            if(StringUtil.isNotEmpty(operatetime_end)){
                cq.le("operatetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(operatetime_end));
            }

            String successFlag = request.getParameter("successFlag");
            if(StringUtil.isNotEmpty(successFlag)){
                cq.eq("successFlag", successFlag);
            }

        } catch (ParseException e) {
            logger.error("error:",e);
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 查看系统日志详情页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goSystemLogLook")
    public ModelAndView goSystemLogLook(TSLog tsLog, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tsLog.getId())) {
            tsLog = systemService.getEntity(TSLog.class, tsLog.getId());
            Short loglevel = tsLog.getLoglevel();
            String logName = Globals.getLogType(loglevel);
            Short operatetype = tsLog.getOperatetype();
            String  operateName = Globals.getLogType(operatetype);
            String successFlag = tsLog.getSuccessFlag();
            //格式化时间，转换为"yyyy-MM-dd HH:mm:ss",否则页面显示的"yyyy-MM-dd HH:mm:ss.0"
            Date operatetime = tsLog.getOperatetime();
            DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
            String formatTime = dFormat.format(operatetime);
            if(successFlag == null){
                String flagName = "失败";
                req.setAttribute("flagName",flagName);
            }else if(successFlag != null && successFlag.equals("0")){
                String flagName = "失败";
                req.setAttribute("flagName",flagName);
            }else if(successFlag != null && successFlag.equals("1")){
                String flagName = "成功";
                req.setAttribute("flagName",flagName);
            }
            Integer aaa = tsLog.getAaa();
            if(aaa == 1){
                String aaaName ="系统事件" ;
                req.setAttribute("aaaName",aaaName);
            }else if(aaa == 0){
                String aaaName ="业务事件";
                req.setAttribute("aaaName",aaaName);
            }
            req.setAttribute("logName",logName);
            req.setAttribute("operateName",operateName);
            req.setAttribute("tsLog", tsLog);
            req.setAttribute("formatTime",formatTime);
        }
        return new ModelAndView("com/kd/op/monitor/systemLogInfo");
    }

    /**
     * 场景购买统计分析
     * @param req
     * @return
     */
    @RequestMapping(params = "sceneOrderMonitor")
    public ModelAndView sceneOrderMonitor(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/sceneOrderMonitor");
        return model;
    }

    @RequestMapping(params = "sceneOrderCount")
    @ResponseBody
    public MonitorEntity sceneOrderCount(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.sceneOrderCount(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    @RequestMapping(params = "sceneOrderRanking")
    @ResponseBody
    public MonitorEntity sceneOrderRanking(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.sceneOrderRanking(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    /**
     * 场景调用统计分析
     * @param req
     * @return
     */
    @RequestMapping(params = "sceneInvokeMonitor")
    public ModelAndView sceneInvokeMonitor(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/sceneInvokeMonitor");
        return model;
    }

    @RequestMapping(params = "sceneInvokeCount")
    @ResponseBody
    public MonitorEntity sceneInvokeCount(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.sceneInvokeCount(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    @RequestMapping(params = "sceneInvokeRanking")
    @ResponseBody
    public MonitorEntity sceneInvokeRanking(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.sceneInvokeRanking(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    /**
     * 场景购买统计分析
     * @param req
     * @return
     */
    @RequestMapping(params = "apiOrderMonitor")
    public ModelAndView apiOrderMonitor(HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/apiOrderMonitor");
        return model;
    }

    @RequestMapping(params = "apiOrderCount")
    @ResponseBody
    public MonitorEntity apiOrderCount(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.apiOrderCount(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    @RequestMapping(params = "apiOrderRanking")
    @ResponseBody
    public MonitorEntity apiOrderRanking(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.apiOrderRanking(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    /**
     * 能力调用统计分析
     * @return
     */
    @RequestMapping(params = "apiInvokeMonitor")
    public ModelAndView apiInvokeMonitor() {
        ModelAndView model = new ModelAndView("com/kd/op/monitor/apiInvokeMonitor");
        return model;
    }

    @RequestMapping(params = "apiInvokeCount")
    @ResponseBody
    public MonitorEntity apiInvokeCount(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.apiInvokeCount(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }

    @RequestMapping(params = "apiInvokeRanking")
    @ResponseBody
    public MonitorEntity apiInvokeRanking(MonitorEntity monitor,HttpServletRequest req){
        try {
            monitor = apiMonitorService.apiInvokeRanking(monitor);
        } catch (Exception e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        return monitor;
    }
}
