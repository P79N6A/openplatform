package com.kd.openplatform.charge.service;

import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import org.springframework.stereotype.Component;


public interface ChargeTestAccountServiceI {
    public void delete(ChargeTestAccountEntity entity) throws Exception;

    public void save(ChargeTestAccountEntity entity) throws Exception;

    public void saveOrUpdate(ChargeTestAccountEntity entity) throws Exception;

}
