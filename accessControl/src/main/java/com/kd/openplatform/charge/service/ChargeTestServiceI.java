package com.kd.openplatform.charge.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import com.kd.openplatform.common.exception.ControlException;


public interface ChargeTestServiceI {
    public void charge(ChargeTestAccountEntity c);

    public void checkStatus(ChargeTestAccountEntity c) throws ControlException;

    public ChargeTestAccountEntity getTestAccount(JSONObject param);
}
