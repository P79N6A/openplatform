package com.kd.openplatform.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.interceptors.AccessInterceptor;
import com.kd.openplatform.access.service.TokenService;
import com.kd.openplatform.common.exception.ControlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 访问控制模块
 * 控制中心
 *
 * @author Miao
 */

@Component
public class ControlCenter {
	private static final Log log =  LogFactory.getLog(ControlCenter.class);
    @Autowired
    private TokenService tokenService;

    @Autowired
    private StrategyContext strategyContext;

    @Autowired
    private TestStrategyContext testStrategyContext;

    //鉴权、计费、流控等 main方法
    public String checkAndReturn(String data) {
        JSONObject resultMsg = new JSONObject();
        resultMsg.put("status", 500);
        JSONObject param;
        try {
            param = JSON.parseObject(data);
        } catch (Exception e) {
            resultMsg.put("info", "请求参数格式有误");
            return resultMsg.toJSONString();
        }

        //测试分支
        try {
            if(param.get("appId").toString().equals("WebTest") || param.get("appId").toString().equals("ProgramTest")){
                return testStrategyContext.strategyControl(param);
            }
        }catch (ControlException e){
            resultMsg.put("info",e.getMsg());
            return resultMsg.toJSONString();
        }

        log.info(String.format("鉴权、计费、流控等 main方法%s", param));
        return strategyContext.strategyControl(param);
    }


}
