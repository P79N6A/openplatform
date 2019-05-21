package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiAppConnect;
import com.kd.op.api.service.ApiAppConnectService;
import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("apiAppConnectService")
@Transactional
public class ApiAppConnectServiceImpl extends CommonServiceImpl implements ApiAppConnectService {
    @Override
    public List<ApiAppConnect> getByAppAndType(String appId, Integer type) {
        String hql = "from ApiAppConnect where appId=:appId and type=:type and deleteFlag = 0";
        Query query = getSession().createQuery(hql);
        query.setParameter("appId",appId);
        query.setParameter("type",type);
        return query.list();
    }
}
