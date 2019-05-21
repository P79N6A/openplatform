package com.kd.openplatform.resource.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.control.AccessStrategyI;
import com.kd.openplatform.resource.serivce.ResourceServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Title 资源访问控制
 * @author shinerio
 * @Description
 */
@Component
public class ResourceInterceptor implements AccessStrategyI{
    private static  final Log log =  LogFactory.getLog(ResourceInterceptor.class);

    @Autowired
    private ResourceServiceI resourceService;

    @Override
    public void before(String args) {
        log.info("ResourceInterceptor");
    }

    @Override
    public void after(JSONObject object) throws ControlException {
        log.info("ResourceInterceptor");

    }

    /**
     * 资源访问控制，参数中的resourceId与实际resouceId进行对比
     * @param object
     * @throws Exception 抛出异常，表示资源验证不通过，阻止调用hsf
     */
    @Override
    public void around(JSONObject object) throws Exception {
        String apiAppRelaId = object.getString("apiAppRelaId");
        String resourceId = object.getString("resourceId");
        if(!resourceService.isResourceValid(apiAppRelaId, resourceId)){
            throw new ControlException(Code.API_RESOURCE_AUTHOR_FAIL_MSG, Code.API_RESOURCE_AUTHOR_FAIL);
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("ResourceInterceptor");

    }

    @Override
    public void afterThrowing(String args) {
        log.info("ResourceInterceptor");

    }
}
