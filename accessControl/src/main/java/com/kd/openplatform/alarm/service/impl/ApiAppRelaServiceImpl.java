package com.kd.openplatform.alarm.service.impl;


import com.kd.openplatform.common.entity.ApiAppRelaEntity;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.alarm.service.ApiAppRelaServiceI;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("apiAppRelaService")
public class ApiAppRelaServiceImpl extends CommonAccessService implements ApiAppRelaServiceI {

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(ApiAppRelaEntity entity) throws Exception{
        super.delete(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ApiAppRelaEntity entity) throws Exception{
        super.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(ApiAppRelaEntity entity) throws Exception{
        super.saveOrUpdate(entity);
    }
}
