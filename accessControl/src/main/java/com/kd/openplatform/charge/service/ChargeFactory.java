package com.kd.openplatform.charge.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.interceptors.AccessInterceptor;
import com.kd.openplatform.common.exception.ControlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component("chargeFactory")
public class ChargeFactory {
    private static final Log log =  LogFactory.getLog(ChargeFactory.class);
    @Autowired
    ChargeAccountServiceI chargeAccountService;

    /**
     * 检查帐户余额
     * @param param 访问参数
     */
    public boolean checkStatus(JSONObject param) throws ControlException {
        boolean status = false;
        Class l = null;
        log.info(String.format("检查帐户余额%s",param));
        try {
        	if(param.get("typename") ==null) {
        		log.info("===============checkStatus=========typename=null");
        		return status;
        	}
            l = Class.forName(param.get("typename").toString());
            Object obj1 = l.newInstance();
            Method m = l.getMethod("checkStatus", param.getClass());
            status = (boolean) m.invoke(obj1, param);
            return status;
        } catch (ControlException e) {
            throw new ControlException(e.getMsg());
        } catch (InstantiationException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ControlException(e.getTargetException().getMessage());
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            //e.getMessage();
            log.error(e.getMessage());
        }
        return status;
    }

    /**
     * 执行扣费
     * @param param 访问参数
     */
    public void charge(JSONObject param) {

        Class l = null;
        try {
            l = Class.forName(param.get("typename").toString());
            Object classObj = l.newInstance();
            Method m = l.getMethod("charge", param.getClass());
            m.invoke(classObj,param);
        } catch (ClassNotFoundException e) {
          //  e.printStackTrace();
            log.error(e.getMessage());
        } catch (ControlException e) {
            throw new ControlException(e.getMsg());
        } catch (IllegalAccessException e) {
          //  e.printStackTrace();
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
           // e.printStackTrace();
            log.error(e.getMessage());
        } catch (InstantiationException e) {
           // e.printStackTrace();
            log.error(e.getMessage());
        } catch (NoSuchMethodException e) {
           // e.printStackTrace();
            log.error(e.getMessage());
        }

    }

}
