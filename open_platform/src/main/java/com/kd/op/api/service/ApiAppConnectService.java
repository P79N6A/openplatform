package com.kd.op.api.service;

import com.kd.op.api.entity.ApiAppConnect;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface ApiAppConnectService extends CommonService {

    /**
     * 根据应用id和类型获取应用申请链接数据
     * @param appId 应用id
     * @param type 连接类型
     * @return
     */
    public List<ApiAppConnect> getByAppAndType(String appId, Integer type);
}
