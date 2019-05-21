package com.kd.openplatform.common.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class MapCache {
    private      ConcurrentHashMap<String, Object> getApiIdByPath = new ConcurrentHashMap<String, Object>();
    private      ConcurrentHashMap<String, Object> getChargeApp = new ConcurrentHashMap<String, Object>();
    private      ConcurrentHashMap<String, Object> getAppNameByAppId = new ConcurrentHashMap<String, Object>();
    private      ConcurrentHashMap<String, Object> getApiNameByApiId = new ConcurrentHashMap<String, Object>();

    public ConcurrentHashMap<String, Object> getGetApiNameByApiId() {
        return getApiNameByApiId;
    }

    public void setGetApiNameByApiId(ConcurrentHashMap<String, Object> getApiNameByApiId) {
        this.getApiNameByApiId = getApiNameByApiId;
    }



    public ConcurrentHashMap<String, Object> getGetAppNameByAppId() {
        return getAppNameByAppId;
    }

    public void setGetAppNameByAppId(ConcurrentHashMap<String, Object> getAppNameByAppId) {
        this.getAppNameByAppId = getAppNameByAppId;
    }

    public ConcurrentHashMap<String, Object> getGetChargeApp() {
        return getChargeApp;
    }

    public void setGetChargeApp(ConcurrentHashMap<String, Object> getChargeApp) {
        this.getChargeApp = getChargeApp;
    }



    public ConcurrentHashMap<String, Object> getGetApiIdByPath() {
        return getApiIdByPath;
    }

    public void setGetApiIdByPath(ConcurrentHashMap<String, Object> getApiIdByPath) {
        this.getApiIdByPath = getApiIdByPath;
    }



}
