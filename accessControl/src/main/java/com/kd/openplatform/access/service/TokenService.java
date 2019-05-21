package com.kd.openplatform.access.service;

import com.alibaba.fastjson.JSONObject;


public interface TokenService {

    public JSONObject getUserIdByToken(JSONObject param);
    public JSONObject getUserIdByAppId(JSONObject param);
    /*public JSONObject getTypenameByApiAppReal(JSONObject param);*/
}
