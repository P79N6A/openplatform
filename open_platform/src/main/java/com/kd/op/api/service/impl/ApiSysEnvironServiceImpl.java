package com.kd.op.api.service.impl;

import com.kd.op.api.service.ApiSysEnvironService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiSysEnvironService")
@Transactional
public class ApiSysEnvironServiceImpl extends CommonServiceImpl implements ApiSysEnvironService {
}
