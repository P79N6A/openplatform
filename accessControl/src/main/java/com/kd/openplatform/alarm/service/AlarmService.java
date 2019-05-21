package com.kd.openplatform.alarm.service;

import com.kd.openplatform.alarm.entity.SysAlarmEntity;
import com.kd.openplatform.alarm.service.impl.SysAlarmServiceImpl;
import com.kd.openplatform.charge.service.ChargeFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("alarmService")
public class AlarmService {
    private static final Logger logger = Logger.getLogger(AlarmService.class);

    @Autowired
    SysAlarmServiceI sysAlarmService;

    /**
     * 将告警信息写入数据库
     * @param message 告警信息
     * @param typeName 告警类型，1是熔断告警，2是余量告警
     * @param apiName API接口名称
     * @param appName APP应用名称
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void writeDatabase(String message,String typeName,String apiName,String appName){
        SysAlarmEntity sysAlarm = new SysAlarmEntity();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = date.format(new Date());
        sysAlarm.setTypeName(typeName);
        sysAlarm.setMessage(message);
        sysAlarm.setTime(datetime);
        sysAlarm.setApiName(apiName);
        sysAlarm.setAppName(appName);
        try{
            sysAlarmService.save(sysAlarm);
        }catch(Exception e){
           // e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

}
