package com.kd.op.api.service;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiAppEntity;

import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.MonitorEntity;
import org.jeecgframework.core.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ApiMonitorService  extends CommonService {

    /**
     * 统计当天实时的接口调用统计
     * @return
     */
    public Map<Integer,Integer> countInDay();

    /**
     * 统计当天实时的对应分组的接口调用统计
     * @param groupId
     * @return
     */
    public Map<Integer,Integer> countInDayByGroup(String groupId);

    /**
     * 统计一个时间段内的应用交易的数量
     * @param start 开始时间 yyyy-MM-dd
     * @param end 结束时间 yyyy-MM-dd
     * @param appId 接口id
     * @return
     */
    public Map<String,Map<String,Object>> countAppBetweenTimes(String start, String end, String appId) throws Exception;

    /**
     * 统计一个时间段内的接口调用情况，并根据应用进行分类
     * @param start 开始时间 yyyy-MM-dd HH:mm:ss
     * @param end 结束时间 yyyy-MM-dd HH:mm:ss
     * @param appId 接口id
     * @return
     */
    public List<Map<String, Object>> countAppLog(String start, String end, String appId);
    
    /**
     * 根据不同范围的数据查询服务的调用信息统计
     * @param start 开始时间 yyyy-MM-dd HH:mm:ss
     * @param end 结束时间 yyyy-MM-dd HH:mm:ss
     * @param nodeType 范围类型 app,group,api
     * @param nodeId 范围类型对应数据的id
     * @param appId 应用id
     * @return
     */
    public Map<String,Map<String,Object>> countBetweenTimeByType(String start, String end, String nodeType,String nodeId,String appId);

    /**
     * 根据不同范围的数据查询应用的交易排名信息
     * @param start
     * @param end
     * @return
     */
    List<Map<String, String>> appOrderSortBetweenTimes(String start, String end);
    /**
     * 根据不同范围的数据查询能力的交易排名信息
     * @param start
     * @param end
     * @return
     */
    List<Map<String, String>> apiInvokeSortBetweenTimes(String start, String end);

    //场景交易统计
    public MonitorEntity sceneOrderCount(MonitorEntity monitor)throws Exception;

    //场景交易排名
    public MonitorEntity sceneOrderRanking(MonitorEntity monitor)throws Exception;

    //场景调用统计
    public MonitorEntity sceneInvokeCount(MonitorEntity monitor)throws Exception;

    //场景调用排名
    public MonitorEntity sceneInvokeRanking(MonitorEntity monitor)throws Exception;

    //能力交易统计
    public MonitorEntity apiOrderCount(MonitorEntity monitor)throws Exception;

    //能力交易排名
    public MonitorEntity apiOrderRanking(MonitorEntity monitor)throws Exception;

    //能力调用统计
    public MonitorEntity apiInvokeCount(MonitorEntity monitor)throws Exception;

    //能力调用排名
    public MonitorEntity apiInvokeRanking(MonitorEntity monitor)throws Exception;


    public List<ApiInfoEntity> invokeApiDatagrid(String groupId);
}
