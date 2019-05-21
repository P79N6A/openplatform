package com.kd.openplatform.charge.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.access.service.TokenService;
import com.kd.openplatform.alarm.service.AlarmService;
import com.kd.openplatform.charge.service.ChargeAccountServiceI;
import com.kd.openplatform.charge.service.ChargeFactory;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.control.AccessStrategyI;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.alarm.service.SearchMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Component
public class ChargeInterceptor implements AccessStrategyI {
    private static  final Log log =  LogFactory.getLog(ChargeInterceptor.class);
    @Autowired
    private ChargeFactory chargeFactory;

    @Autowired
    private ChargeAccountServiceI chargeAccountService;

    @Autowired
    SearchMethod searchMethod;

    @Autowired
    AlarmService alarmService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ParamService paramService;

    @Override
    public void before(String args) {
        log.info("ChargeInterceptor");
    }

    /**
     * 执行扣费
     * @param param 访问参数
     */
    @Override
    public void after(JSONObject param) {
        try {
            param = paramService.getApiIdByPath(param);
            param = chargeAccountService.getChargeApp(param);
            chargeFactory.charge(param);
        } catch (ControlException e) {
            throw new ControlException(e.getMsg(),e.getCode());
        }
    }

    /**
     * 检查帐户
     * @param param 访问参数
     */
    @Override
    public void around(JSONObject param) {
        boolean status = false;
        try {
            param = paramService.getApiIdByPath(param);
            param = chargeAccountService.getChargeApp(param);
        }catch (ControlException e){
            throw new ControlException(e.getMsg(),e.getCode());
        }
        try {
            status = chargeFactory.checkStatus(param);
        } catch (ControlException e) {
            throw new ControlException(Code.API_FUNDS_INSUFFICIENT_MSG,Code.API_FUNDS_INSUFFICIENT);
        }
        if (!status) {
            String appName = searchMethod.getAppNameByParam(param);
            String apiName = searchMethod.getApiNameByParam(param);
            alarmService.writeDatabase(Code.API_FUNDS_INSUFFICIENT_MSG,"2",apiName,appName);
            throw new ControlException(Code.API_FUNDS_INSUFFICIENT_MSG,Code.API_FUNDS_INSUFFICIENT);
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("ChargeInterceptor");
    }

    @Override
    public void afterThrowing(String args) {
        log.info("ChargeInterceptor");
    }


}
