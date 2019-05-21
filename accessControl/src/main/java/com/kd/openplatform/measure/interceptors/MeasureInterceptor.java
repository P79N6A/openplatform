package com.kd.openplatform.measure.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.control.AccessStrategyI;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.measure.entity.ChargeAfterEntity;
import com.kd.openplatform.measure.service.ChargeAfterFactory;
import com.kd.openplatform.measure.service.ChargeAfterServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.util.List;

@Component
public class MeasureInterceptor implements AccessStrategyI {
    private static  final Log log =  LogFactory.getLog(MeasureInterceptor.class);

    @Autowired
    private ChargeAfterFactory chargeAfterFactory;
    @Autowired
    private CommonAccessService commonAccessService;
    @Override
    public void before(String args) {
        log.info("MeasureInterceptor");
    }

    @Override
    public void after(JSONObject param) {
        log.info("MeasureInterceptor");

    }

    /**
     * 后计费模块
     * @param param
     * @throws ControlException
     */
    @Override
    public void around(JSONObject param) throws ControlException {
        String app_api_rela_id = param.getString("apiAppRelaId");
        if (app_api_rela_id==null){
            return;
        }
        List<ChargeAfterEntity> chargeAfterEntities =
                commonAccessService.queryListByProperty(ChargeAfterEntity.class, "apiAppRelaId", app_api_rela_id);
        for(ChargeAfterEntity chargeAfterEntity:chargeAfterEntities){
            ChargeAfterServiceI chargeAfterServiceI = chargeAfterFactory.getChargeService(chargeAfterEntity.getTypename());
            chargeAfterServiceI.chargeAfter(chargeAfterEntity, param);
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("MeasureInterceptor");
    }

    @Override
    public void afterThrowing(String args) {
        log.info("MeasureInterceptor");
    }
}
