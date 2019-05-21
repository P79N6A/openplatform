package com.kd.openplatform.measure.service;
import com.kd.openplatform.common.utils.SpringContextUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("chargeAfterFactory")
public class ChargeAfterFactory {

    private static final Logger logger = Logger.getLogger(ChargeAfterFactory.class);

    public ChargeAfterServiceI getChargeService(String beanName){
        return (ChargeAfterServiceI) SpringContextUtils.getBean(beanName);
    }
}
