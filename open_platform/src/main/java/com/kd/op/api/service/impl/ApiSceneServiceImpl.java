package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiSceneServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.DateTimeRangeUtil;
import org.apache.commons.validator.Var;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("apiSceneService")
@Transactional
public class ApiSceneServiceImpl extends CommonServiceImpl implements ApiSceneServiceI {

    private static final Logger logger = Logger.getLogger(ApiSceneServiceImpl.class);
    @Autowired
    private SystemService systemService;

    @Autowired
    private CommonService commonService;

    @Override
    //查询是否重名（用于add）
    public BigInteger nameCount(String sceneName) {

        String sqlQuery = "select count(scene_name) from api_scene where scene_name='%s';";
        sqlQuery = String.format(sqlQuery, sceneName);
        List<BigInteger> num = commonService.findListbySql(sqlQuery);
        BigInteger count = num.get(0);
        return count;

    }

    ;

    //查询是否重名（用于edit）
    public List<ApiSceneEntity> findRepeat(String sceneName) {
        String sqlQuery = "select id from api_scene where scene_name='%s';";
        sqlQuery = String.format(sqlQuery, sceneName);
        List<ApiSceneEntity> scenes = commonService.findListbySql(sqlQuery);
        return scenes;
    }

    public void delete(ApiSceneEntity entity) throws Exception {
        super.delete(entity);
    }

    public void save(ApiSceneEntity entity) throws Exception {
        systemService.save(entity);
    }

    public void update(ApiSceneEntity entity) throws Exception {
        if (StringUtil.isNotEmpty(entity.getId())) {
            try {
                ApiSceneEntity temp = findUniqueByProperty(ApiSceneEntity.class, "id", entity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(entity, temp);
                this.saveOrUpdate(temp);
            } catch (Exception e) {
                logger.error("update error");
            }
        } else {
            this.saveOrUpdate(entity);
        }
    }

    public void saveOrUpdate(ApiSceneEntity entity) throws Exception {
        super.saveOrUpdate(entity);

    }

    @Override
    public Boolean idInList(JSONArray array, String[] idList) {
        List<String> tempIdList = Arrays.asList(idList);
        Boolean con = true;
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String api = obj.get("apiId") + "";
            if (!tempIdList.contains(api)) {
                con = false;
                break;
            }
        }
        return con;
    }

    @Override
    //保存api信息
    public void addApi(ApiSceneTotalEntity SceneTotal, ApiSceneEntity apiScene) throws Exception {
        //保存服务信息
        this.save(apiScene);
    }


    /**
     * 替换sql中的变量
     *
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql, ApiSceneEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{scene_name}", String.valueOf(t.getSceneName()));
        sql = sql.replace("#{scene_des}", String.valueOf(t.getSceneDes()));
        sql = sql.replace("#{scene_status}", String.valueOf(t.getSceneStatus()));
        sql = sql.replace("#{scene_audit_status}", String.valueOf(t.getSceneAuditStatus()));
        sql = sql.replace("#{scene_audit_msg}", String.valueOf(t.getSceneAuditMsg()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_time}", String.valueOf(t.getCreateTime()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));

        return sql;
    }



}
