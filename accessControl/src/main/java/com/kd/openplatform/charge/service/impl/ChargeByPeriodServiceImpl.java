package com.kd.openplatform.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.alarm.service.AlarmService;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import com.kd.openplatform.charge.service.ChargeServiceI;
import com.kd.openplatform.alarm.service.SearchMethod;
import com.kd.openplatform.common.exception.Code;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChargeByPeriodServiceImpl implements ChargeServiceI {
    private static  final Log log =  LogFactory.getLog(ChargeByPeriodServiceImpl.class);

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private SearchMethod searchMethod;

    @Override
    public void charge(JSONObject j) {
        log.info("ChargeByPeriodServiceImpl");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean checkStatus(JSONObject j) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expireDate = dateFormat.parse(j.get("restState").toString());
        Date today = new Date();
        if (today.before(expireDate) || DateUtils.isSameDay(expireDate, today)) {
            return true;
        } else {
            String apiName = searchMethod.getApiNameByParam(j);
            String appName = searchMethod.getAppNameByParam(j);
            alarmService.writeDatabase(Code.API_SERVICE_EXPIRED_MSG,"2",apiName,appName);
            throw new Exception(Code.API_SERVICE_EXPIRED_MSG);
        }
    }
}