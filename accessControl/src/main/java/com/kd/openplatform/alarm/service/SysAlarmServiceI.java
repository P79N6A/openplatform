package com.kd.openplatform.alarm.service;

import com.kd.openplatform.alarm.entity.SysAlarmEntity;

public interface SysAlarmServiceI {

    public void delete(SysAlarmEntity entity);

    public void save(SysAlarmEntity entity);

    public void saveOrUpdate(SysAlarmEntity entity);
}
