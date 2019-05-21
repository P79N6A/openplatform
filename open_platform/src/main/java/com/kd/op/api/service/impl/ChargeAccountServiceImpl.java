package com.kd.op.api.service.impl;

import com.kd.op.api.service.ChargeAccountService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ChargeAccountService")
@Transactional
public class ChargeAccountServiceImpl extends CommonServiceImpl implements ChargeAccountService{
}
