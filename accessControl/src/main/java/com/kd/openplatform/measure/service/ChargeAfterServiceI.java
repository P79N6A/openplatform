package com.kd.openplatform.measure.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.measure.entity.ChargeAfterEntity;

public interface ChargeAfterServiceI {
    void chargeAfter(ChargeAfterEntity chargeAfterEntity, JSONObject param);
}
