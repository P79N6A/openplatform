package com.kd.openplatform.access.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.access.service.TokenService;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.common.utils.Constants;
import com.kd.openplatform.control.AccessStrategyI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sunbox.api.model.ResponseVo;
import sunbox.gateway.api.model.system.OperatorItemModel;
import sunbox.gateway.api.service.system.SysSupportApiService;


@Component
public class AccessInterceptor implements AccessStrategyI {
    private static final Log log = LogFactory.getLog(AccessInterceptor.class);
    @Autowired
    private ParamService paramService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysSupportApiService sysSupportApiService;

    @Autowired
    private JedisPool jedisPool;

    //调用接口前检查
    @Override
    public void before(String args) {
        log.info("AccessInterceptor");
    }


    @Override
    public void after(JSONObject object) {
        log.info("income accessInterceptor after");
        try {
            log.info("AccessInterceptor--after--%s" + object);
            paramService.checkParameter(object);
            //解析token判断token是否为null
            Object autherToken = null;
            if (object != null) {
                autherToken = object.get(Constants.TOKENNAME);
            }
            //如果token为null通过appId获取userId
            if (autherToken == null) {
                tokenService.getUserIdByAppId(object);
            } else {
                tokenService.getUserIdByToken(object);
            }
//            tokenService.getTypenameByApiAppReal(object);
            log.info("AccessInterceptor--after--%s" + object);
        } catch (ControlException e) {
            log.info("AccessInterceptor--after--出错" + object);
            log.info("AccessInterceptor--after--出错", e);
            throw e;
        }
    }


    @Override
    public void around(JSONObject object) {
        try {
            paramService.checkParameter(object);
            //解析token判断token是否为null
            Object autheToken = null;
            if (object != null) {
                autheToken = object.get(Constants.TOKENNAME);
                //去redis获取accessToken
                Jedis jedis = jedisPool.getResource();
                String accesstoken = jedis.get("ACCESSTOKEN").replace("\"", "");
                log.info("验证token时获取的accessToken==" + accesstoken);
                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                OperatorItemModel operatorItemModel = new OperatorItemModel();
                if (jsonObject != null) {
                    String token = jsonObject.getString(Constants.TOKENNAME);
                    operatorItemModel.setToken(token);
                    operatorItemModel.setAccessToken(accesstoken);
                }
                log.info("验证token时传给中台的参数==" + operatorItemModel.toString());
                //检查token
                ResponseVo responseVo = sysSupportApiService.checkToken(operatorItemModel);
                log.info("中台的返回值为：" + JSONObject.toJSONString(responseVo));
                JSONObject responseVoJson = (JSONObject) JSONObject.toJSON(responseVo);
                if (responseVoJson != null) {
                    Object statusObj = responseVoJson.get("status");
                    if (statusObj != null) {
                        Integer status = Integer.parseInt(statusObj.toString());
                        if (status != 0) {
                            jedis.close();
                            throw new ControlException(Code.API_OBSOLETE_TOKEN_MSG, Code.API_OBSOLETE_TOKEN);
                        }
                    } else {
                        jedis.close();
                        throw new ControlException(Code.SYSTEM_EXCEPTION_MSG, Code.SYSTEM_EXCEPTION);
                    }
                } else {
                    jedis.close();
                    throw new ControlException(Code.SYSTEM_EXCEPTION_MSG, Code.SYSTEM_EXCEPTION);
                }
            }
            if (autheToken == null) {
                //没有token时通过userId获取appid 暂时用不到 程序无法进入此处
                tokenService.getUserIdByAppId(object);
            } else {
                tokenService.getUserIdByToken(object);
            }
            log.info("AccessInter ceptor--around--" + object);
        } catch (ControlException e) {
            log.info("AccessInterceptor--around--出错");
            throw e;
        }
    }

    @Override
    public void afterReturning(String args) {
        log.info("AccessInterceptor");

    }

    @Override
    public void afterThrowing(String args) {
        log.info("AccessInterceptor");

    }


}
