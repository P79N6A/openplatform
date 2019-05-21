package com.kd.openplatform.charge.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.access.service.TokenService;
import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import com.kd.openplatform.charge.service.ChargeFactory;
import com.kd.openplatform.charge.service.ChargeTestAccountServiceI;
import com.kd.openplatform.charge.service.ChargeTestServiceI;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.control.AccessStrategyI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestChargeInterceptor implements AccessStrategyI {
    private static  final Log log =  LogFactory.getLog(TestChargeInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private ChargeFactory chargeFactory;


    @Autowired
    private ChargeTestServiceI chargeTestService;


    @Autowired
    private ChargeTestAccountServiceI chargeTestAccountService;

    //调用接口前，检查订购状态是否满足要求
    @Override
    public void before(String args) {
        log.info("TestChargeInterceptor");
    }

    /**
     * 执行扣费
     * @param param 访问参数
     */
    @Override
    public void after(JSONObject param) {
        param = paramService.getApiIdByPath(param);
        ChargeTestAccountEntity c = chargeTestService.getTestAccount(param);
        chargeTestService.charge(c);
    }

    /**
     * 检查帐户
     * @param param 访问参数
     */
    @Override
    public void around(JSONObject param) {
        param = paramService.getApiIdByPath(param);
        ChargeTestAccountEntity c  = chargeTestService.getTestAccount(param);
        try {
            if (c == null) {
                c = new ChargeTestAccountEntity();
                c.setRestState("10");
                c.setInvokeMethod(param.get("appId").toString());
                c.setUserId(param.get("userId").toString());
                c.setApiId(param.get("apiId").toString());
            }
            chargeTestService.checkStatus(c);
        } catch (ControlException e) {
            throw new ControlException(e.getMsg());
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("TestChargeInterceptor");
    }

    @Override
    public void afterThrowing(String args) {
        log.info("TestChargeInterceptor");
    }


}
