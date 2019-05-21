package com.kd.openplatform.access.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.entity.ApiInfoEntity;
import com.kd.openplatform.access.service.ParamService;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.common.utils.Constants;
import com.kd.openplatform.common.utils.MapCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ParamServiceImpl extends CommonAccessService implements ParamService {
    private static final Log log = LogFactory.getLog(ParamServiceImpl.class);
    /**
     * 检查参数
     *
     * @param param 访问参数
     */
    @Autowired
    MapCache mapCache;

    @Override
    public void checkParameter(JSONObject param) {
        if (!(param.containsKey("appId") && param.containsKey("path")  && param.containsKey(Constants.TOKENNAME)))
            throw new ControlException(Code.API_PARAM_ERROE_MSG,Code.API_PARAM_ERROE);
    }

    @Override
    public void checkTestParameter(JSONObject param) {
        log.info("ParamServiceImpl");
    }

    /**
     * 根据路径获取Id
     *
     * @param param 访问参数
     * @return 含有ApiId的JsonObject
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONObject getApiIdByPath(JSONObject param) {
        ApiInfoEntity a = null;
        a = (ApiInfoEntity) mapCache.getGetApiIdByPath().get(param.getString("path"));
        if (a == null) {
            a = (ApiInfoEntity) commonAccessDao.queryUniqueByProperty(ApiInfoEntity.class, "reqAddrHttp", param.getString("path"));
            mapCache.getGetApiIdByPath().put(param.getString("path"), a);
        }
        if (a == null) {
            throw new ControlException(Code.API_PATH_ERROR_MSG,Code.API_PATH_ERROR);
        }
        String apiId = a.getId();
        param.put("apiId", apiId);
        log.info("getApiIdByPath-------------------"+param);
        return param;
    }
}