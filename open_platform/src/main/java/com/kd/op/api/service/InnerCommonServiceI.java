package com.kd.op.api.service;

import com.kd.op.api.entity.ApiInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.util.Arrays;
import java.util.List;

/**
 * 公共接口类，目的是处理那些在Service层抽出来的公共方法在事务上失效的情况
 */
public interface InnerCommonServiceI extends CommonService {

   /**
     * 添加服务的基本信息和参数信息
     * @param apiInfo 服务对象
     * @param headers 请求头参数
     * @param requests 请求参数
     * @param returns 相应参数
     */
    public void addMain(ApiInfoEntity apiInfo, String headers, String requests, String returns);

    /**
     * 添加服务的基本信息和参数信息
     * @param apiInfo 服务对象
     * @param headers 请求头参数
     * @param requests 请求参数
     * @param returns 相应参数
     */
    public void updateMain(ApiInfoEntity apiInfo, String headers, String requests, String returns);

}
