package com.kd.openplatform.access.service;

import com.alibaba.fastjson.JSONObject;

public interface ParamService {

    public void checkParameter(JSONObject param);

    public void checkTestParameter(JSONObject param);

    public JSONObject getApiIdByPath(JSONObject param);
}
