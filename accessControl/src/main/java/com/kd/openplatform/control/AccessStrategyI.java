package com.kd.openplatform.control;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.exception.ControlException;

public interface AccessStrategyI {
    public void before(String args);

    public void after(JSONObject object) throws ControlException;

    public void around(JSONObject object) throws Exception;

    public void afterReturning(String args);

    public void afterThrowing(String args);
}
