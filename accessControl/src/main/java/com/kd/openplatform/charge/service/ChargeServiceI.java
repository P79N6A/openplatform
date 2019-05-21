package com.kd.openplatform.charge.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import org.springframework.stereotype.Component;

@Component("chargeService")
public interface ChargeServiceI {
    public void charge(JSONObject j);

    public boolean checkStatus(JSONObject j) throws Exception;
}
