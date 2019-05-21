package com.kd.openplatform.alarm.service.impl;

import com.kd.openplatform.alarm.entity.SysAlarmEntity;
import com.kd.openplatform.alarm.service.SysAlarmServiceI;
import com.kd.openplatform.common.service.CommonAccessService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("sysAlarmService")
public class SysAlarmServiceImpl extends CommonAccessService implements SysAlarmServiceI {

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(SysAlarmEntity entity) {
        super.delete(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(SysAlarmEntity entity) {
        super.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveOrUpdate(SysAlarmEntity entity){
        super.saveOrUpdate(entity);
    }
}
