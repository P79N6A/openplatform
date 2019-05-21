package com.kd.openplatform.flow.service.impl;

import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.flow.service.FlowServiceI;
import com.kd.openplatform.flow.service.RedisFlowLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("flowService")
public class FlowServiceImpl implements FlowServiceI {

    @Autowired
    RedisFlowLimiter redisFlowLimiter;

    /**
     * ����apiid��appid��Redis��ȡ�����ز��ԣ��Խӿڵ��ô����������ƣ�type=1��ʾ���μ��㡣
     * @param apiId
     * @param appId
     * @return
     */
    @Override
    public boolean checkNumandFlow(String apiId, String appId){
        String strategy = redisFlowLimiter.getStrategyFromRedis(apiId, appId);
        Boolean success = true;
        int type = Integer.valueOf(strategy.split(",")[0]);
        int max = Integer.valueOf(strategy.split(",")[1]);
        int unit = Integer.valueOf(strategy.split(",")[2]);
        if (type == 1){
            Boolean successForNum =  redisFlowLimiter.acquireForNumbers(apiId + appId + "Num" + unit, unit, max);
            if(!successForNum) success = false;
        }
        if(!success){
            return false;
        }
        return true;
    }
}
