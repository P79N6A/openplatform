package com.kd.openplatform.charge.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import org.springframework.stereotype.Component;

@Component("chargeAccountService")
public interface ChargeAccountServiceI {
    public void delete(ChargeAccountEntity entity) throws Exception;

    public void save(ChargeAccountEntity entity) throws Exception;

    public void saveOrUpdate(ChargeAccountEntity entity) throws Exception;

    public JSONObject getChargeApp(JSONObject jsonObject);
}
