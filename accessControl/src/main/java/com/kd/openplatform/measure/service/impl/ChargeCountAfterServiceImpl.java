package com.kd.openplatform.measure.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.measure.entity.ChargeAfterEntity;
import com.kd.openplatform.measure.service.ChargeAfterServiceI;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("chargeCountAfterService")
public class ChargeCountAfterServiceImpl extends CommonAccessService implements ChargeAfterServiceI{

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void chargeAfter(ChargeAfterEntity chargeAfterEntity, JSONObject param) {
        int count = Integer.parseInt(chargeAfterEntity.getCount());
        count += 1;
        chargeAfterEntity.setCount(Integer.toString(count));
        commonAccessDao.update(chargeAfterEntity);
    }
}
