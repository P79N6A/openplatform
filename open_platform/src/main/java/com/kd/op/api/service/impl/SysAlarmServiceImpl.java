package com.kd.op.api.service.impl;


import com.kd.op.api.service.SysAlarmServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SysAlarmService")
@Transactional
public class SysAlarmServiceImpl extends CommonServiceImpl implements SysAlarmServiceI {
}
