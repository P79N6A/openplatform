package com.kd.openplatform.charge.service.impl;

import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import com.kd.openplatform.charge.service.ChargeTestAccountServiceI;
import com.kd.openplatform.common.service.CommonAccessService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("chargeTestAccountService")
public class ChargeTestAccountServiceImpl extends CommonAccessService implements ChargeTestAccountServiceI {

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(ChargeTestAccountEntity entity) throws Exception{
        super.delete(entity);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ChargeTestAccountEntity entity) throws Exception{
        super.save(entity);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(ChargeTestAccountEntity entity) throws Exception{
        super.saveOrUpdate(entity);
    }
}
