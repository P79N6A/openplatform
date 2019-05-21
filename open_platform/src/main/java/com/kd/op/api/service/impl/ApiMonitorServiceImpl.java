package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.MonitorEntity;
import com.kd.op.api.service.ApiMonitorService;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.DateTimeRangeUtil;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.StringTransUtil;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("apiMonitorService")
@Transactional
public class ApiMonitorServiceImpl extends CommonServiceImpl implements ApiMonitorService {

    private final static Logger logger = Logger.getLogger(ApiMonitorServiceImpl.class);
    @Autowired
    private SystemService systemService;

    @Override
    public Map<Integer, Integer> countInDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "SELECT HOUR(invoke_time) as hour,count(*) as count " +
                "FROM api_invoke_log " +
                "WHERE invoke_time LIKE:invoke_time" +
                "GROUP BY HOUR(invoke_time) ORDER BY Hour(invoke_time)";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("invoke_time", sdf.format(new Date()) + "%");
        List<Map<String, Object>> list = query.list();
        Map<Integer, Integer> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            map.put(Integer.parseInt(m.get("hour") + ""), Integer.parseInt(m.get("count") + ""));
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> countInDayByGroup(String groupId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "SELECT HOUR(invoke_time) as hour,count(*) as count " +
                "FROM " +
                "api_invoke_log log left join api_info info on info.id = log.api_id " +
                "left join api_group ag on ag.id = info.group_id " +
                "WHERE " +
                "invoke_time LIKE:invoke_time and ag.id=:groupId " +
                "GROUP BY HOUR(invoke_time) ORDER BY Hour(invoke_time)";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("invoke_time", sdf.format(new Date()) + "%");
        query.setParameter("groupId", groupId);
        List<Map<String, Object>> list = query.list();
        Map<Integer, Integer> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            map.put(Integer.parseInt(m.get("hour") + ""), Integer.parseInt(m.get("count") + ""));
        }
        return map;
    }

    @Override
    public Map<String, Map<String, Object>> countAppBetweenTimes(String start, String end, String appId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(sdf.parse(start));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(sdf.parse(end));
        Map<String, Map<String, Object>> map = new HashMap<>();
        String sql;
        //如果选择的时间间隔小于1天 按小时显示
        if (endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 <= 1) {
            sql = "SELECT " +
                    "DATE_FORMAT(pay_time,'%Y-%m-%d %H:%m:%s') AS date, " +
                    "count(*) AS count " +
                    "FROM api_order " +
                    "WHERE " +
                    "pay_time between :start and :end and pay_status=1 and order_type = 'app' ";
            if (appId != null && StringUtil.isNotEmpty(appId)) {
                sql += " and app_id=:appId ";
            }
            sql += " GROUP BY DATE_FORMAT(pay_time,'%H') ORDER BY hour (pay_time)";
            SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameter("start", start);
            query.setParameter("end", end);
            if (appId != null && StringUtil.isNotEmpty(appId)) {
                query.setParameter("appId", appId);
            }
            List<Map<String, Object>> list = query.list();
            for (Map<String, Object> m : list) {
                String[] dateStrArr = String.valueOf(m.get("date")).split(" ");
                String[] hourStr = dateStrArr[1].split(":");
                map.put(hourStr[0]+":00"+":00", m);
            }
            return map;

        } else if (endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 > 1 && endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 <= 31) {
            //如果选择的时间间隔小于31天 大于1天 按天显示
            sql = "SELECT " +
                    "DATE_FORMAT(pay_time,'%Y-%m-%d') AS date, " +
                    "count(*) AS count " +
                    "FROM api_order " +
                    "WHERE " +
                    "pay_time between :start and :end and pay_status=1 and order_type = 'app' ";
            if (appId != null && StringUtil.isNotEmpty(appId)) {
                sql += " and app_id=:appId ";
            }
            sql += " GROUP BY DATE_FORMAT(pay_time,'%Y-%m-%d') ORDER BY hour (pay_time)";

        } else if (endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 > 31 && endCal.getTimeInMillis() / 1000 / 60 / 60 / 24 - startCal.getTimeInMillis() / 1000 / 60 / 60 / 24 <= 366) {
            //如果选择的时间间隔小于365天 大于31天 按月显示
            sql = "SELECT " +
                    "DATE_FORMAT(pay_time,'%Y-%m') AS date, " +
                    "count(*) AS count " +
                    "FROM api_order " +
                    "WHERE " +
                    "pay_time between :start and :end and pay_status=1 and order_type = 'app' ";
            if (appId != null && StringUtil.isNotEmpty(appId)) {
                sql += " and app_id=:appId ";
            }
            sql += " GROUP BY DATE_FORMAT(pay_time,'%Y-%m') ORDER BY month (pay_time)";
        } else {
            //按年显示
            sql = "SELECT " +
                    "DATE_FORMAT(pay_time,'%Y') AS date, " +
                    "count(*) AS count " +
                    "FROM api_order " +
                    "WHERE " +
                    "pay_time between :start and :end and pay_status=1 and order_type = 'app' ";
            if (appId != null && StringUtil.isNotEmpty(appId)) {
                sql += " and app_id=:appId ";
            }
            sql += " GROUP BY DATE_FORMAT(pay_time,'%Y') ORDER BY year (pay_time)";

        }
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("start", start);
        query.setParameter("end", end);
        if (appId != null && StringUtil.isNotEmpty(appId)) {
            query.setParameter("appId", appId);
        }
        List<Map<String, Object>> list = query.list();
        for (Map<String, Object> m : list) {
            map.put(m.get("date") + "", m);
        }
        return map;
    }

    @Override
    public List<Map<String, String>> appOrderSortBetweenTimes(String start, String end) {
        String sql = "SELECT  COUNT(1) AS COUNT, " +
                "app_name AS appName " +
                "FROM api_order " +
                "WHERE " +
                "pay_time between :start and :end and pay_status=1 and order_type = 'app' "
                + "GROUP BY appName DESC ORDER BY COUNT LIMIT 0,5";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<Map<String, String>> list = query.list();
        return list;
    }

    @Override
    public List<Map<String, String>> apiInvokeSortBetweenTimes(String start, String end) {
        String sql = "SELECT  COUNT(1) AS COUNT, " +
                "api_Id AS apiId " +
                "FROM api_invoke_log " +
                "WHERE " +
                "invoke_time between :start and :end "
                + "GROUP BY apiId DESC ORDER BY COUNT LIMIT 0,5";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("start", start);
        query.setParameter("end", end);
        List<Map<String, String>> list = query.list();
        return list;
    }

    @Override
    public List<Map<String, Object>> countAppLog(String start, String end, String appId) {
        List<Map<String, Object>> list = new ArrayList<>();
        Session session = systemService.getSession();
        String sql = "SELECT " +
                "count(1) as count," +
                "sum(request_flow_size) as requestFlowSize," +
                "sum(response_flow_size) as responseFlowSize," +
                "count(case when invoke_result = 1 then 1 end) as successNum," +
                "count(case when invoke_result = 0 then 1 end) as faildNum," +
                "app_id," +
                "app_name " +
                "FROM api_invoke_log " +
                "WHERE " +
                "invoke_time >=:start and invoke_time <=:end and app_id=:appId ";
        sql += " ORDER BY invoke_time ";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("start", start);
        query.setParameter("end", end);
        query.setParameter("appId", appId);
        list = query.list();
        return list;
    }

    @Override
    public Map<String, Map<String, Object>> countBetweenTimeByType(String startStr, String endStr, String nodeType, String nodeId, String appId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formateStr = "DATE_FORMAT(invoke_time,'%Y-%m-%d')";
        String apiId = nodeId;//服务id的范围
        try {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(sdf.parse(startStr));
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(sdf.parse(endStr));

            long beginTime = startCal.getTime().getTime();
            long endTime = endCal.getTime().getTime();
            long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
            long surplus = (long) ((endTime - beginTime) % (1000 * 60 * 60 * 24));
            //如果两个时间间隔超过1天就按天进行统计，否则就按小时进行统计
            if (betweenDays == 0 || (betweenDays == 1 && surplus == 0)) {//按小时进行统计
                formateStr = "DATE_FORMAT(invoke_time,'%Y-%m-%d %H')";
            }
            if (nodeType.equals("app")) {
                apiId = "select api_id from api_app_rela where app_id =:nodeId";
            } else if (nodeType.equals("group")) {
                apiId = "select id from api_info where group_id=:nodeId";
            }

            if (appId == null) {
                appId = nodeId;
            }
        } catch (ParseException e) {
            logger.error("countBetweenTimeByType format date error");
        }

        String sql = "SELECT " +
                formateStr + " AS dateDay, " +
                "count(*) AS count " +
//                "count(case when invoke_result = 1 then 1 end) as successNum, " +
//                "count(case when invoke_result = 0 then 1 end) as faildNum, " +
//                "sum(response_time_length) as responseTimeLength, " +
//                "sum(request_flow_size) as requestFlowSize, " +
//                "sum(response_flow_size) as responseFlowSize " +
                "FROM api_invoke_log " +
                "WHERE " +
                "invoke_time >=:start and invoke_time <=:end and app_id=:appId ";
        if (nodeType.equals("app") || nodeType.equals("group")) {
            sql += " and api_id in (" + apiId + ") ";
        } else {
            sql += " and api_id in (:apiId) ";
        }
        sql += "GROUP BY " +
                formateStr + " " +
                "ORDER BY day (invoke_time) ";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("start", startStr);
        query.setParameter("end", endStr);
        query.setParameter("appId", appId);
        if (nodeType.equals("app") || nodeType.equals("group")) {
            query.setParameter("nodeId", nodeId);
        } else {
            query.setParameter("apiId", apiId);
        }
        List<Map<String, Object>> list = query.list();
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            map.put(m.get("dateDay") + "", m);
        }
        return map;
    }


    /**
     * 通用统计数据获取
     *
     * @param monitor
     * @return
     * @throws Exception
     */
    private MonitorEntity commonCount(MonitorEntity monitor) throws Exception {
        StringBuilder queryCount = new StringBuilder();
         /*准备开始时间和结束时间结束*/
        String startDate = "";
        String endDate = "";
        //如果开始时间和结束时间都不为空
        if (StringUtil.isNotEmpty(monitor.getStartDate()) && StringUtil.isNotEmpty(monitor.getEndDate())
                ) {
            //如果开始时间和结束时间是同一天 则展示这一天24小时内的变化
            if (monitor.getStartDate().equals(monitor.getEndDate())) {
                startDate = monitor.getStartDate() + " 00";
                endDate = monitor.getEndDate() + " 23";
            } else {
                startDate = monitor.getStartDate();
                endDate = monitor.getEndDate();
            }
        } else if (StringUtil.isNotEmpty(monitor.getStartDate())) {
            //如果开始时间不为空 从开始时间到当前时间
            startDate = monitor.getStartDate();
            endDate = DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            if(startDate.equals(endDate)){
                startDate = startDate + " 00";
                endDate = endDate + " 23";
            }
        } else {
            if (StringUtil.isNotEmpty(monitor.getEndDate())) {
                //如果结束时间不为空 那么就从结束时间向前推七天
                endDate = monitor.getEndDate();
            } else {
                //如果开始时间结束时间都为空 那么就从当前时间向前推七天
                endDate = DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = DateTimeRangeUtil.getDateBoforeOrAfterAnyDays(sdf.parse(endDate), CustomConstant.ADVANCE_DAYS);
            startDate = sdf.format(sDate);
        }
        /*准备开始时间和结束时间结束*/

        //准备sql
        if (CustomConstant.COUNT_TYPE_ORDER.equals(monitor.getCountType())) {
            queryCount = orderCountSql(monitor, startDate, endDate);
        } else if (CustomConstant.COUNT_TYPE_INVOKE.equals(monitor.getCountType())) {
            queryCount = invokeCountSql(monitor, startDate, endDate);
        }
        //组装查询器
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryCount.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        if (CustomConstant.COUNT_TYPE_ORDER.equals(monitor.getCountType())) {
            query.setParameter("dataType", monitor.getDataType());
        }
        query = appendQueryParams(monitor, query);
        //接收结果
        List<Map<String, Object>> list = query.list();
        if (list != null && list.size() > 0) {
            //解析结果
            monitor = analysisSceneCountResult(list, startDate, endDate);
        }
        return monitor;
    }

    /**
     * 通用排名数据获取
     *
     * @param monitor
     * @param timeColumn 时间字段名称
     * @return
     * @throws Exception
     */
    private MonitorEntity commonRank(MonitorEntity monitor, String timeColumn) throws Exception {
        StringBuilder queryRank = new StringBuilder();
         /*准备开始时间和结束时间结束*/
        String startDate = "";
        String endDate = "";
        if (CustomConstant.COUNT_TYPE_ORDER.equals(monitor.getCountType())) {
            queryRank = orderRankSql(monitor);
        } else if (CustomConstant.COUNT_TYPE_INVOKE.equals(monitor.getCountType())) {
            queryRank = invokeRankSql(monitor);
        }

        //如果开始时间和结束时间都不为空
        if (StringUtil.isNotEmpty(monitor.getStartDate()) && StringUtil.isNotEmpty(monitor.getEndDate())
                ) {
            queryRank.append("AND DATE_FORMAT(");
            queryRank.append(timeColumn);
            queryRank.append(",'%Y-%m-%d') BETWEEN :startDate AND :endDate ");
            startDate = monitor.getStartDate();
            endDate = monitor.getEndDate();
        } else if (StringUtil.isNotEmpty(monitor.getStartDate())) {
            //如果开始时间不为空 那么久大于等于开始时间
            startDate = monitor.getStartDate();
            queryRank.append("AND DATE_FORMAT(");
            queryRank.append(timeColumn);
            queryRank.append(",'%Y-%m-%d') >= :startDate ");
        } else if (StringUtil.isNotEmpty(monitor.getEndDate())) {
            //如果结束时间不为空 那么小于等于结束时间
            endDate = monitor.getEndDate();
            queryRank.append("AND DATE_FORMAT(");
            queryRank.append(timeColumn);
            queryRank.append(",'%Y-%m-%d') <= :endDate ");
        }
        queryRank.append(" GROUP BY id ORDER BY rank asc LIMIT 5");
        /*准备开始时间和结束时间结束*/

        //组装查询器
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryRank.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (CustomConstant.COUNT_TYPE_ORDER.equals(monitor.getCountType())) {
            query.setParameter("dataType", monitor.getDataType());
        }
        if(StringUtil.isNotEmpty(startDate)){
            String s = StringTransUtil.stringReplace(startDate);
            query.setParameter("startDate", s);
        }
        if(StringUtil.isNotEmpty(endDate)){
            String s = StringTransUtil.stringReplace(endDate);
            query.setParameter("endDate", s);
        }
        query = appendQueryParams(monitor, query);

        //接收结果
        List<Map<String, Object>> list = query.list();
        if (list != null && list.size() > 0) {
            //解析结果
            monitor = analysisSceneRankResult(list);
        }
        return monitor;
    }

    /**
     * 准备sql 订购统计语句
     *
     * @return
     */
    private StringBuilder orderCountSql(MonitorEntity monitor, String sStartTime, String sEndTime) {
        StringBuilder preparSql = new StringBuilder();
        String DATE_FORMAT = DateTimeRangeUtil.getDateFormat(sStartTime, sEndTime);
        preparSql.append("SELECT COUNT(ao.id) dayCount,");
        if (sEndTime.length() > 10 || sStartTime.equals(sEndTime)) {
            preparSql.append(" DATE_FORMAT(pay_time,'%H') dayTime ");
        } else {
            preparSql.append(" DATE_FORMAT(pay_time,'");
            preparSql.append(DATE_FORMAT);
            preparSql.append("') dayTime ");
        }
        //查询订购表
        preparSql.append(CustomConstant.query_Order_Table);
        //关联能力表 查询能力中心
        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiInfo_Table);
        }else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiScene_Table);
        }
        //拼接订购的公共条件
        preparSql.append(CustomConstant.queryOrder_Public_Where);

        //关联能力表 查询能力中心
        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.rela_ApiInfo_OrderDetail);
        }else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.rela_ApiScene_OrderDetail);
        }
        //拼接其他查询字段
        preparSql = appendQueryCondition(monitor, preparSql);
        //订单类型
        preparSql.append(CustomConstant.query_DataType);
        preparSql.append("AND DATE_FORMAT(pay_time,'");
        preparSql.append(DATE_FORMAT);
        preparSql.append("') ");
        preparSql.append("BETWEEN :startDate AND :endDate ");
        preparSql.append("GROUP BY DATE_FORMAT(pay_time,'");
        preparSql.append(DATE_FORMAT);
        preparSql.append("') ");
        preparSql.append("ORDER BY pay_time asc");
        return preparSql;
    }

    /**
     * 准备sql 调用统计语句
     *
     * @return
     */
    private StringBuilder invokeCountSql(MonitorEntity monitor, String sStartTime, String sEndTime) {
        StringBuilder preparSql = new StringBuilder();
        String DATE_FORMAT = DateTimeRangeUtil.getDateFormat(sStartTime, sEndTime);
        preparSql.append("SELECT COUNT(ail.id) dayCount,");
        if (sStartTime.length()>10 || sStartTime.equals(sEndTime)) {
            preparSql.append(" DATE_FORMAT(invoke_time,'%H') dayTime ");
        } else {
            preparSql.append(" DATE_FORMAT(invoke_time,'");
            preparSql.append(DATE_FORMAT);
            preparSql.append("') dayTime ");
        }

        //查询调用日志表
        preparSql.append(CustomConstant.query_Invoke_Table);
        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiInfo_Table);
            preparSql.append(CustomConstant.queryInvoke_Api_Where);
            //关联能力中心 及能力表其他信息
            preparSql.append(CustomConstant.rela_ApiInfo_InvokeLog);
        } else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiScene_Table);
            preparSql.append(CustomConstant.queryInvoke_Scene_Where);
            //关联场景表其他信息
            preparSql.append(CustomConstant.rela_ApiScene_InvokeLog);
        }

        //拼接其他查询字段
        preparSql = appendQueryCondition(monitor, preparSql);
        preparSql.append(" AND DATE_FORMAT(invoke_time,'");
        preparSql.append(DATE_FORMAT);
        preparSql.append("') ");
        preparSql.append("BETWEEN :startDate AND :endDate ");
        preparSql.append("GROUP BY DATE_FORMAT(invoke_time,'");
        preparSql.append(DATE_FORMAT);
        preparSql.append("') ");
        preparSql.append("ORDER BY invoke_time asc");
        return preparSql;
    }

    /**
     * 准备sql 订购排名语句
     *
     * @return
     */
    private StringBuilder orderRankSql(MonitorEntity monitor) {
        StringBuilder preparSql = new StringBuilder();

        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.select_Rank_Api);
        } else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.select_Rank_Scene);
        }
        preparSql.append(CustomConstant.query_Order_Table);
        //查询能力中心
        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiInfo_Table);
        }else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiScene_Table);
        }
        preparSql.append(CustomConstant.queryOrder_Public_Where);

        //关联能力表 查询能力中心
        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.rela_ApiInfo_OrderDetail);
        }else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.rela_ApiScene_OrderDetail);
        }

        //拼接查询条件
        preparSql = appendQueryCondition(monitor, preparSql);
        //订单类型
        preparSql.append(CustomConstant.query_DataType);
        return preparSql;
    }

    /**
     * 准备sql 调用排名语句
     *
     * @return
     */
    private StringBuilder invokeRankSql(MonitorEntity monitor) {
        StringBuilder preparSql = new StringBuilder();

        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.select_Rank_Api);
        } else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.select_Rank_Scene);
        }

        //查询调用日志表
        preparSql.append(CustomConstant.query_Invoke_Table);

        if (CustomConstant.API.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiInfo_Table);
            preparSql.append(CustomConstant.queryInvoke_Api_Where);
            //关联能力中心 及能力表其他信息
            preparSql.append(CustomConstant.rela_ApiInfo_InvokeLog);
        } else if (CustomConstant.SCENE.equals(monitor.getDataType())) {
            preparSql.append(CustomConstant.query_ApiScene_Table);
            preparSql.append(CustomConstant.queryInvoke_Scene_Where);
            //关联场景表其他信息
            preparSql.append(CustomConstant.rela_ApiScene_InvokeLog);
        }

        //拼接查询条件
        preparSql = appendQueryCondition(monitor, preparSql);

        return preparSql;
    }

    /**
     * 拼接查询条件
     *
     * @return
     */
    private StringBuilder appendQueryCondition(MonitorEntity monitor, StringBuilder queryCondition) {
        Map<String,Object> filterMap = ResourceParams.getAuthorityUsers();

        if(CustomConstant.FILTER_DATA_TRUE.equals(filterMap.get(CustomConstant.IS_FILTER))){

            if(CustomConstant.API.equals(monitor.getDataType())){
                queryCondition.append(CustomConstant.query_Api_CreateBy);
            }else if(CustomConstant.SCENE.equals(monitor.getDataType())){
                queryCondition.append(CustomConstant.query_Scene_CreateBy);
            }

        }

        //统计的查询条件
        if (CustomConstant.CHART_TYPE_COUNT.equals(monitor.getChartType())) {
            if (StringUtil.isNotEmpty(monitor.getApiGroupId())) {
                queryCondition.append(CustomConstant.query_ApiGroup);
            }
            if (StringUtil.isNotEmpty(monitor.getSceneId())) {
                queryCondition.append(CustomConstant.query_SceneId);
            }
            if (StringUtil.isNotEmpty(monitor.getApiId())) {
                queryCondition.append(CustomConstant.query_ApiId);
            }
            //排名的查询条件
        } else if (CustomConstant.CHART_TYPE_RANK.equals(monitor.getChartType())) {
            if (StringUtil.isNotEmpty(monitor.getApiGroupId())) {
                queryCondition.append(CustomConstant.query_ApiGroup);
            }
        }

        return queryCondition;
    }

    /**
     * 拼接查询参数
     *
     * @return
     */
    private SQLQuery appendQueryParams(MonitorEntity monitor, SQLQuery query) {

        Map<String,Object> filterMap = ResourceParams.getAuthorityUsers();
        if(CustomConstant.FILTER_DATA_TRUE.equals(filterMap.get(CustomConstant.IS_FILTER))){
            query.setParameter("createBy",filterMap.get(CustomConstant.USERNAME));
        }

        //统计的查询参数
        if (CustomConstant.CHART_TYPE_COUNT.equals(monitor.getChartType())) {
            //场景
            if (StringUtil.isNotEmpty(monitor.getSceneId())) {
                String s = StringTransUtil.stringReplace(monitor.getSceneId());
                query.setParameter("sceneId", s);
            }
            //能力
            if (StringUtil.isNotEmpty(monitor.getApiId())) {
                String s = StringTransUtil.stringReplace(monitor.getApiId());
                query.setParameter("apiId", s);
            }
            //能力中心
            if (StringUtil.isNotEmpty(monitor.getApiGroupId())) {
                String s = StringTransUtil.stringReplace(monitor.getApiGroupId());
                query.setParameter("apiGroupId", s);
            }
            //排名的查询参数
        } else if (CustomConstant.CHART_TYPE_RANK.equals(monitor.getChartType())) {
            if (StringUtil.isNotEmpty(monitor.getApiGroupId())) {
                String s = StringTransUtil.stringReplace(monitor.getChartType());
                query.setParameter("apiGroupId", s);
            }
        }
        return query;
    }

    /**
     * 解析场景排名结果
     *
     * @param list
     * @return
     */
    private MonitorEntity analysisSceneRankResult(List<Map<String, Object>> list) {
        MonitorEntity monitor = new MonitorEntity();
        List<String> yAxisData = new ArrayList<>();
        List<Integer> seriesData = new ArrayList<>();
        //把所有的map中的key和value取出来放到一个新的map中
        Map<String, Object> newMap = new HashMap<>();
        list.forEach(p -> {
            yAxisData.add(p.get("name").toString());
            seriesData.add(Integer.parseInt(p.get("rank").toString()));
        });
        monitor.setyAxisData(yAxisData);
        monitor.setSeriesData(seriesData);
        return monitor;
    }

    /**
     * 解析场景统计结果
     *
     * @param list
     * @return
     */
    private MonitorEntity analysisSceneCountResult(List<Map<String, Object>> list, String startDate, String endDate) {
        MonitorEntity monitor = new MonitorEntity();
        List<String> xAxisData = new ArrayList<>();
        List<Integer> seriesData = new ArrayList<>();
        //把所有的map中的key和value取出来放到一个新的map中
        Map<String, Object> newMap = new HashMap<>();
        list.forEach(p -> {
            newMap.put(p.get("dayTime").toString(), p.get("dayCount"));
        });

        List<String> timeScale = DateTimeRangeUtil.getTimeScale(startDate, endDate);
        //如果是查询一天内的数据则 时间刻度拼接后缀
        if (startDate.equals(endDate)) {
            timeScale.forEach(p -> {
                xAxisData.add(p + CustomConstant.TIME_SUFFIX);
            });
        } else {
            xAxisData.addAll(timeScale);
        }
        //遍历新的map获取时间精度和值
        timeScale.forEach(p -> {
            if (StringUtil.isNotEmpty(newMap.get(p))) {
                seriesData.add(Integer.parseInt(newMap.get(p).toString()));
            } else {
                seriesData.add(0);
            }
        });
        monitor.setxAxisData(xAxisData);
        monitor.setSeriesData(seriesData);
        return monitor;
    }

    @Override
    public MonitorEntity sceneOrderCount(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_ORDER);
        monitor.setDataType(CustomConstant.SCENE);
        monitor.setChartType(CustomConstant.CHART_TYPE_COUNT);
        monitor = commonCount(monitor);
        return monitor;
    }

    @Override
    public MonitorEntity sceneOrderRanking(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_ORDER);
        monitor.setDataType(CustomConstant.SCENE);
        monitor.setChartType(CustomConstant.CHART_TYPE_RANK);
        monitor = commonRank(monitor, CustomConstant.TIME_COL_ORDER);
        return monitor;
    }

    @Override
    public MonitorEntity sceneInvokeCount(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_INVOKE);
        monitor.setDataType(CustomConstant.SCENE);
        monitor.setChartType(CustomConstant.CHART_TYPE_COUNT);
        monitor = commonCount(monitor);
        return monitor;
    }

    @Override
    public MonitorEntity sceneInvokeRanking(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_INVOKE);
        monitor.setDataType(CustomConstant.SCENE);
        monitor.setChartType(CustomConstant.CHART_TYPE_RANK);
        monitor = commonRank(monitor, CustomConstant.TIME_COL_INVOKE);
        return monitor;
    }

    @Override
    public MonitorEntity apiOrderCount(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_ORDER);
        monitor.setDataType(CustomConstant.API);
        monitor.setChartType(CustomConstant.CHART_TYPE_COUNT);
        monitor = commonCount(monitor);
        return monitor;
    }

    @Override
    public MonitorEntity apiOrderRanking(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_ORDER);
        monitor.setDataType(CustomConstant.API);
        monitor.setChartType(CustomConstant.CHART_TYPE_RANK);
        monitor = commonRank(monitor, CustomConstant.TIME_COL_ORDER);
        return monitor;
    }

    @Override
    public MonitorEntity apiInvokeCount(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_INVOKE);
        monitor.setDataType(CustomConstant.API);
        monitor.setChartType(CustomConstant.CHART_TYPE_COUNT);
        monitor = commonCount(monitor);
        return monitor;
    }

    @Override
    public MonitorEntity apiInvokeRanking(MonitorEntity monitor) throws Exception {
        monitor.setCountType(CustomConstant.COUNT_TYPE_INVOKE);
        monitor.setDataType(CustomConstant.API);
        monitor.setChartType(CustomConstant.CHART_TYPE_RANK);
        monitor = commonRank(monitor, CustomConstant.TIME_COL_INVOKE);
        return monitor;
    }

    @Override
    public List<ApiInfoEntity> invokeApiDatagrid(String groupId) {
        List<ApiInfoEntity> results = new ArrayList<>();
        Map<String,Object> roleMap = ResourceParams.getAuthorityUsers();
        String username = (String)roleMap.get(CustomConstant.USERNAME);
        String isFilter = (String)roleMap.get(CustomConstant.IS_FILTER);
        String hql;
        StringBuffer sb = new StringBuffer();
        // 增加过滤当前用户
        if(StringUtil.isNotEmpty(groupId)&&isFilter.equals("true") && !groupId.equals("0") ){
            hql ="from ApiInfoEntity where  apiPublishStatus= ?  and groupId = ?  and createBy = ?";
            results = this.findHql(hql,CustomConstant.PUBLIS_PASS,groupId,username);
        }else if(StringUtil.isNotEmpty(groupId) && !groupId.equals("0")){
            hql ="from ApiInfoEntity where  apiPublishStatus= ? and groupId = ? ";
            results = this.findHql(hql,CustomConstant.PUBLIS_PASS,groupId);
        }else if(isFilter.equals("true")){
            hql ="from ApiInfoEntity where  apiPublishStatus= ? and createBy = ?";
            results = this.findHql(hql,CustomConstant.PUBLIS_PASS,username);
        }else{
            hql ="from ApiInfoEntity where  apiPublishStatus= ? ";
            results = this.findHql(hql,CustomConstant.PUBLIS_PASS);
        }
        return results;
    }


}
