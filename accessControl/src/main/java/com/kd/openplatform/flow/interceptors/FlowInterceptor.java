package com.kd.openplatform.flow.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.alarm.service.AlarmService;
import com.kd.openplatform.alarm.service.SysAlarmServiceI;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.control.AccessStrategyI;
import com.kd.openplatform.common.exception.ControlException;

import com.kd.openplatform.alarm.service.SearchMethod;
import com.kd.openplatform.flow.service.FlowServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Component
public class FlowInterceptor implements AccessStrategyI {

    private static  final Log log =  LogFactory.getLog(FlowInterceptor.class);

    @Autowired
    private FlowServiceI flowService;
    @Autowired
    private ParamService paramService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private SearchMethod searchMethod;


    @Override
    public void before(String args){
        log.info("FlowInterceptor");
    }


    @Override
    public void after(JSONObject param){
        log.info("FlowInterceptor");
    }

    @Override
    public void around(JSONObject param) throws ControlException {
        Boolean status = false;
        param = paramService.getApiIdByPath(param);
        String appId = param.get("appId").toString();
        String apiId = param.get("apiId").toString();
        status = flowService.checkNumandFlow(apiId,appId);
        if(!status){
            String apiName = searchMethod.getApiNameByApiId(apiId);
            String appName = searchMethod.getAppNameByAppId(appId);
            alarmService.writeDatabase(Code.API_OVER_FLOW_MSG,"1",apiName,appName);
            throw new ControlException(Code.API_OVER_FLOW_MSG,Code.API_OVER_FLOW);
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("FlowInterceptor");
    }

    @Override
    public void afterThrowing(String args) {
        log.info("FlowInterceptor");
    }

}
