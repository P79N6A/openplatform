package com.kd.openplatform.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.alarm.service.AlarmService;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import com.kd.openplatform.charge.service.ChargeAccountServiceI;
import com.kd.openplatform.charge.service.ChargeServiceI;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.utils.SpringContextUtils;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.alarm.service.SearchMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("chargeByQueryServiceImpl")
public class ChargeByQueryServiceImpl implements ChargeServiceI {
    private static final Logger logger = Logger.getLogger(ChargeByQueryServiceImpl.class);

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private SearchMethod searchMethod;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void charge(JSONObject j) {
        ChargeAccountEntity c = new ChargeAccountEntity();
        c.setId(j.get("id").toString());
        c.setTypename(j.get("typename").toString());
        c.setApiAppRelaId(j.get("apiAppRelaId").toString());
        c.setRestState(String.valueOf(Integer.parseInt(j.get("restState").toString())-1));
        try {
            ChargeAccountServiceI chargeAccountService = (ChargeAccountServiceI) SpringContextUtils.getBean("chargeAccountService");
            chargeAccountService.saveOrUpdate(c);
        } catch (Exception e) {
            logger.info("Exception:" + e.getMessage());
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean checkStatus(JSONObject j) throws ControlException{
        if (Integer.parseInt(j.get("restState").toString())>0){
            return true;
        }else {
            String apiName = searchMethod.getApiNameByParam(j);
            String appName = searchMethod.getAppNameByParam(j);
            alarmService.writeDatabase(Code.API_REMAIN_INSUFFICIENT_MSG,"1",apiName,appName);
            throw new ControlException(Code.API_REMAIN_INSUFFICIENT_MSG,Code.API_REMAIN_INSUFFICIENT);
        }
    }
}
