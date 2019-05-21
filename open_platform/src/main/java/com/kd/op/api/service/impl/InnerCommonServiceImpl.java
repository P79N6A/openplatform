package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.service.InnerCommonServiceI;
import com.kd.op.util.ResourceParams;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("innerCommonService")
@Transactional
public class InnerCommonServiceImpl extends CommonServiceImpl implements InnerCommonServiceI{
    private static final Logger logger = Logger.getLogger(InnerCommonServiceImpl.class);
    @Autowired
    private SystemService systemService;

    @Override
    @Transactional
    public void addMain(ApiInfoEntity apiInfo, String headers, String requests, String returns) {
        apiInfo.setApiAuditStatus(0);
        apiInfo.setApiPublishStatus(0);
        apiInfo.setApiStatus(0);
        apiInfo.setApiVisibleStatus(0);
        apiInfo.setApiRunStatus(0);
        TSUser currentUser = ResourceUtil.getSessionUserName();

        //保存主信息
        systemService.save(apiInfo);

        /**保存-接口参数*/
//        JSONArray headesArray = JSONArray.parseArray(headers);
//        saveParams(apiInfo, headesArray, 2);
//
//        JSONArray requestArray = JSONArray.parseArray(requests);
//        saveParams(apiInfo, requestArray, 0);
//
//        JSONArray returnArray = JSONArray.parseArray(returns);
//        saveParams(apiInfo, returnArray, 1);
    }

    @Override
    @Transactional
    public void updateMain(ApiInfoEntity apiInfo, String headers, String requests, String returns) {
        //保存主表信息
        if (StringUtil.isNotEmpty(apiInfo.getId())) {
            try {
                ApiInfoEntity temp = findUniqueByProperty(ApiInfoEntity.class, "id", apiInfo.getId());
                MyBeanUtils.copyBeanNotNull2Bean(apiInfo, temp);
                temp.setApiVisibleStatus(0);
                temp.setApiAuditStatus(0);
                temp.setApiRunStatus(0);
                temp.setApiPublishStatus(0);
                temp.setApiStatus(0);
                systemService.saveOrUpdate(temp);
            } catch (Exception e) {
                logger.error("updateMain update apiInfo error");
            }
        } else {
            systemService.saveOrUpdate(apiInfo);
        }
        //===================================================================================
        //更新参数表
        //首先删除原来的参数信息
        String sql = "delete from api_param where api_id='%s'";
        sql = String.format(sql, apiInfo.getId());
        systemService.executeSql(sql, new HashMap<>());
        //然后将新的参数信息插入表中
        JSONArray headesArray = JSONArray.parseArray(headers);
//        saveParams(apiInfo, headesArray, 2);
//
//        JSONArray requestArray = JSONArray.parseArray(requests);
//        saveParams(apiInfo, requestArray, 0);
//
//        JSONArray returnArray = JSONArray.parseArray(returns);
//        saveParams(apiInfo, returnArray, 1);
    }

}
