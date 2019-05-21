package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiInvokeLogEntity;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.service.HplushomeService;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.Status;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class HplushomeServiceImpl implements HplushomeService {
    @Autowired
    private SystemService systemService;

    /**
     * @return java.lang.Long
     * @Author zyz
     * @Description 查询能力总数
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Long getApiCountData() {
        String sql = "select count(id) from api_info";
        Long apiCountData = systemService.getCountForJdbc(sql);
        return apiCountData;
    }

    /**
     * @return java.lang.Long
     * @Author zyz
     * @Description 查询订阅能力总数量
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Long getApiOrderCountData() {
        Long apiOrderCount = 0l;
        String sql1 = "select aod.scene_id from api_order ao,api_order_detail aod " +
                "where ao.id=aod.order_id and ao.pay_status=? ";
        List<Map<String, Object>> list = systemService.findForJdbc(sql1, CustomConstant.PAY_HASPAY);
        for (Map<String, Object> map : list) {
            if (map.get("scene_id") == null) {
                apiOrderCount++;
            } else {
                String sceneId = (String) map.get("scene_id");
                String[] params = {sceneId};
                String sql2 = "select count(api_id) from api_scene_rela where scene_id=?";
                Long sceneOrderCount = systemService.getCountForJdbcParam(sql2, params);
                apiOrderCount += sceneOrderCount;
            }
        }

        return apiOrderCount;
    }

    /**
     * @return java.lang.Long
     * @Author zyz
     * @Description 查询总应用个数
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Long getAppCountData() {
        String sql = "select count(1) from api_app ";
        Long appCount = systemService.getCountForJdbc(sql);
        return appCount;
    }

    /**
     * @return java.lang.Double
     * @Author zyz
     * @Description 查询能力调用总流量
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Double getApiProductFlowData() {
        double flowSize = 0;
        String sql = "select sum(response_flow_size) as flow from api_invoke_log where invoke_openplatform_result=0";
        List<Map<String, Object>> flowMapList = systemService.findForJdbc(sql);
        for (Map<String, Object> map : flowMapList) {
            flowSize += (Double) map.get("flow");
        }
        double flow = Math.round(flowSize);
        return flow;
    }

    /**
     * @return java.lang.Long
     * @Author zyz
     * @Description 查询能力调用总个数
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Long getApiProductCountData() {
        String sql = "select count(1) from api_invoke_log ";
        Long apiProductCount = systemService.getCountForJdbc(sql);
        return apiProductCount;
    }

    /**
     * @return java.lang.Double
     * @Author zyz
     * @Description 查询总交易金额
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public BigDecimal getMoneyData() {
        BigDecimal money = null;
        String sql = "select sum(money) sum from api_order";
        List<Map<String, Object>> moneys = systemService.findForJdbc(sql);
        for (Map<String, Object> map : moneys) {
            if (map.get("sum") != null) {
                money = new BigDecimal((double) map.get("sum"));
            }
        }
        return money;
    }

    /**
     * @return java.util.Map
     * @Author zyz
     * @Description 根据时间查询能力调用流量
     * @Date 2019/3/1
     * @Param []
     */
    @Override
    public Map getFlowByTime(String startDate, String endDate) {
        String sql = "SELECT DATE_FORMAT(invoke_time,'%Y-%m') AS date, response_flow_size AS flow FROM api_invoke_log WHERE " +
                "invoke_time between ? and ? and invoke_openplatform_result= 0 ";
        List<Map<String, Object>> list = systemService.findForJdbc(sql, startDate, endDate);
        HashMap<String, Double> map = new HashMap<>();
        for (Map<String, Object> m : list) {
            if (m.get("date") != null) {
                String date = (String) m.get("date");
                if (map.get(date) == null) {
                    if (m.get("flow") != null) {
                        map.put(date, (Double) m.get("flow"));
                    } else {
                        map.put(date, 0d);
                    }
                } else {
                    Double flow = map.get(date);
                    if (m.get("flow") != null) {
                        flow += (Double) m.get("flow");
                    }
                    map.put(date, flow);
                }
            }
        }
        return map;
    }

    /*
     * @Author zyz
     * @Description 根据时间查询能力调用数量
     * @Date  2019/3/5
     * @Param [startDate, endDate]
     * @return  java.util.Map
     */
    @Override
    public Map getCountData(String startDate, String endDate) {
        String sql = "SELECT DATE_FORMAT(invoke_time,'%Y-%m') AS date, count(1) AS count FROM api_invoke_log WHERE " +
                "invoke_time between ? and ?  group by date";
        List<Map<String, Object>> list = systemService.findForJdbc(sql, startDate, endDate);
        Map countMap = getMapByListForMonitor(list);
        return countMap;
    }

    /*
     * @Author zyz
     * @Description 根据时间查询能力调用异常数量
     * @Date  2019/3/5
     * @Param [startDate, endDate]
     * @return  java.util.Map
     */
    @Override
    public Map getExceptionData(String startDate, String endDate) {
        String sql = "SELECT DATE_FORMAT(invoke_time,'%Y-%m') AS date, count(1) AS count FROM api_invoke_log WHERE " +
                "invoke_time between ? and ? and invoke_openplatform_result != 0 group by date";
        List<Map<String, Object>> list = systemService.findForJdbc(sql, startDate, endDate);
        Map exceptionMap = getMapByListForMonitor(list);
        return exceptionMap;
    }

    /*
     * @Author zyz
     * @Description 根据时间查询能力注册数量
     * @Date  2019/3/5
     * @Param [startDate, endDate]
     * @return  java.util.Map
     */
    @Override
    public Map getApiCountByTime(String startDate, String endDate) {
        String sql = "select DATE_FORMAT(create_date,'%Y-%m') AS date, count(1) AS count from api_info " +
                "where create_date between ? and ? and api_audit_status=? group by date";
        List<Map<String, Object>> list = systemService.findForJdbc(sql, startDate, endDate, CustomConstant.AUDIT_PASS);
        Map apiCountMap = getMapByListForMonitor(list);
        return apiCountMap;
    }

    /*
     * @Author zyz
     * @Description 根据时间查询能力订阅数量
     * @Date  2019/3/5
     * @Param [startDate, endDate]
     * @return  java.util.Map
     */
    @Override
    public Map getOrderCountBytime(String startDate, String endDate) {
        //查询所有订单中的数据 若为能力订单，直接统计数量，若为场景订单，记录场景id
        String sql1 = "select aod.scene_id, DATE_FORMAT(ao.create_date,'%Y-%m') AS date from api_order ao,api_order_detail aod " +
                "where ao.id=aod.order_id and ao.pay_status=? and ao.create_date between ? and ?";
        List<Map<String, Object>> list = systemService.findForJdbc(sql1, CustomConstant.PAY_HASPAY, startDate, endDate);
        HashMap<String, Long> orderCountMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            if (map.get("scene_id") == null) {
                if (map.get("date") != null && orderCountMap.get(map.get("date")) == null) {
                    orderCountMap.put((String) map.get("date"), 1l);
                }else if (map.get("date") != null && orderCountMap.get(map.get("date")) != null)
                    orderCountMap.put((String) map.get("date"), orderCountMap.get(map.get("date")) + 1);
            } else {
                String sceneId = (String) map.get("scene_id");
                String[] params = {sceneId};
                //查询场景订单中的能力数量
                String sql2 = "SELECT COUNT(api_id) FROM api_scene_rela WHERE scene_id=?";
                Long sceneOrderCount = systemService.getCountForJdbcParam(sql2, params);
                if (map.get("date") != null && orderCountMap.get(map.get("date")) == null) {
                    orderCountMap.put((String) map.get("date"), sceneOrderCount);
                }else if (map.get("date") != null && orderCountMap.get(map.get("date")) != null) {
                    orderCountMap.put((String) map.get("date"), orderCountMap.get(map.get("date")) + sceneOrderCount);
                }
            }
        }

        return orderCountMap;
    }

    /*
     * @Author zyz
     * @Description 根据时间和角色查询用户数量
     * @Date  2019/3/5
     * @Param [startDate, endDate]
     * @return  java.util.Map
     */
    @Override
    public Map getUserCountByTimeAndRoleCode(String startDate, String endDate, String roleCode) {
        String sql = "SELECT DATE_FORMAT(u.create_date,'%Y-%m') AS DATE, COUNT(bu.username) AS COUNT " +
                "FROM t_s_user u, t_s_base_user bu, t_s_role_user ru,t_s_role r " +
                "WHERE u.create_date BETWEEN ? AND ? " +
                "AND ru.userid=u.id AND ru.roleid=r.id AND r.rolecode= ? AND ru.userid=bu.ID GROUP BY date";
        List<Map<String, Object>> list = systemService.findForJdbc(sql, startDate, endDate, roleCode);
        Map monitorMap = getMapByListForMonitor(list);
        return monitorMap;
    }

    /*
     * @Author zyz
     * @Description 查询访问流量排名前五的api
     * @Date  2019/3/6
     * @Param []
     * @return  java.util.List<java.lang.String>
     */
    @Override
    public Map<String, Double> getApiNameRankByFlow() {
        String sql = "SELECT SUM(ail.response_flow_size) flow,ai.api_name apiName " +
                "FROM api_invoke_log ail, api_info ai " +
                "WHERE ail.api_id=ai.id " +
                "GROUP BY ail.api_id ORDER BY flow DESC LIMIT 0,5";
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        for (Map<String, Object> m : list) {
            if (m.get("apiName") != null && m.get("flow") != null) {
                map.put((String) m.get("apiName"), (double) Math.round((Double) m.get("flow")));
            } else if (m.get("apiName") != null) {
                map.put((String) m.get("apiName"), null);
            }
        }

        return map;
    }

    /**
     * @return java.util.Map<java.lang.String   ,   java.lang.Long>
     * @Author zyz
     * @Description 查询能力调用排行前5名
     * @Date 2019/3/6
     * @Param []
     */
    @Override
    public Map<String, Long> getApiNameRankByCount() {
        String sql = "SELECT COUNT(ail.api_id) COUNT,ai.api_name apiName " +
                "FROM api_invoke_log ail, api_info ai " +
                "WHERE ail.api_id=ai.id " +
                "GROUP BY ail.api_id ORDER BY COUNT DESC LIMIT 0,5";
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        LinkedHashMap<String, Long> map = new LinkedHashMap<>();
        for (Map<String, Object> m : list) {
            if (m.get("apiName") != null && m.get("count") != null) {
                map.put((String) m.get("apiName"), (Long) m.get("count"));
            } else if (m.get("apiName") != null) {
                map.put((String) m.get("apiName"), null);
            }
        }

        return map;
    }

    /**
     * @return java.lang.Long
     * @Author zyz
     * @Description 根据能力、应用状态统计数量
     * @Date 2019/3/6
     * @Param [status]
     */
    @Override
    public Long getApiAppCountByStatus(String type, String stausType, Object... status) {
        String sql = "";
        if (StringUtils.isNotEmpty(type) && type.equals("api")) {
            sql += "select count(id) count from api_info where 1=1 ";
            if (StringUtils.isNotEmpty(stausType) && stausType.equals("normal")) {
                sql += "and api_run_status=? and api_status=? and api_publish_status=? ";
            }
            if (StringUtils.isNotEmpty(stausType) && stausType.equals("stop")) {
                sql += "and api_publish_status != ? and (api_run_status=? or api_status=? or api_publish_status=?) ";
            }
            if (StringUtils.isNotEmpty(stausType) && stausType.equals("cancel")) {
                sql += "and api_publish_status=? ";
            }
        } else {
            sql += "select count(id) count from api_app where 1=1 ";
            if (StringUtils.isNotEmpty(stausType) && stausType.equals("stop")) {
                sql += "and publish_status =? or publish_status is null";
            } else {
                sql += "and publish_status=? ";
            }
        }
        Long count = systemService.getCountForJdbcParam(sql, status);
        return count;
    }


    /*
     * @Author zyz
     * @Description 将数据库查询的list数据处理成最终要返回页面的map数据
     * @Date  2019/3/5
     * @Param [list]
     * @return  java.util.Map
     */
    private Map getMapByListForMonitor(List<Map<String, Object>> list) {
        HashMap<String, Long> dataMap = new HashMap<>();
        for (Map<String, Object> map : list) {
            if (map.get("date") != null) {
                String date = (String) map.get("date");
                if (map.get("count") != null) {
                    dataMap.put(date, (Long) map.get("count"));
                } else {
                    dataMap.put(date, 0l);
                }
            }

        }
        return dataMap;
    }

}
