package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiChargeMode;
import com.kd.op.api.service.ApiChargeModeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("chargeModeService")
@Transactional
public class ApiChargeModeServiceImpl extends CommonServiceImpl implements ApiChargeModeServiceI {
}
