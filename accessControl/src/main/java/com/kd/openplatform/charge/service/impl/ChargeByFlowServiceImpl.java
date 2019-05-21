package com.kd.openplatform.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.utils.SpringContextUtils;
import com.kd.openplatform.alarm.service.AlarmService;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import com.kd.openplatform.charge.service.ChargeAccountServiceI;
import com.kd.openplatform.charge.service.ChargeServiceI;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.alarm.service.SearchMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class ChargeByFlowServiceImpl implements ChargeServiceI {
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private SearchMethod searchMethod;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void charge(JSONObject j) throws ControlException {
        int restState = Integer.parseInt(j.get("restState").toString()) - Integer.parseInt(j.get("flowAmount").toString());
        ChargeAccountEntity c = new ChargeAccountEntity();
        if (restState < 0)
            throw new ControlException(Code.API_FLOW_INSUFFICIENT_MSG,Code.API_FLOW_INSUFFICIENT);
        c.setId(j.get("id").toString());
        c.setTypename(j.get("typename").toString());
        c.setRestState(String.valueOf(restState));
        try {
            ChargeAccountServiceI chargeAccountService = (ChargeAccountServiceI) SpringContextUtils.getBean("chargeAccountService");
            chargeAccountService.saveOrUpdate(c);
        } catch (Exception e) {
            throw new ControlException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean checkStatus(JSONObject j) throws ControlException {

        if (Integer.parseInt(j.get("restState").toString()) > 0) {
            return true;
        } else {
            String appName = searchMethod.getAppNameByParam(j);
            String apiName = searchMethod.getApiNameByParam(j);
            alarmService.writeDatabase(Code.API_FLOW_INSUFFICIENT_MSG,"1",apiName,appName);
            throw new ControlException(Code.API_FLOW_INSUFFICIENT_MSG,Code.API_FLOW_INSUFFICIENT);
        }
    }
}