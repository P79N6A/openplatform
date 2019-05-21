package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiAppChargeEntity;
import com.kd.op.api.entity.ApiAppConnect;
import com.kd.op.api.entity.ApiAppKeysEntity;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.util.StringTransUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kd.op.api.entity.ApiAppEntity;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.io.Serializable;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.service.SystemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("apiAppService")
@Transactional
public class ApiAppServiceImpl extends CommonServiceImpl implements ApiAppServiceI {

    @Autowired
    private SystemService systemService;

    @Autowired
    private CommonService commonService;

    public void delete(ApiAppEntity entity) throws Exception {
        super.delete(entity);
    }

    public void save(ApiAppEntity entity) throws Exception {
        systemService.save(entity);
    }

    public void saveOrUpdate(ApiAppEntity entity) throws Exception {
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
    //查询是否重名
    public BigInteger nameCount(String appName) {

        String sqlQuery = "select count(app_name) from api_app where app_name='%s';";
        sqlQuery = String.format(sqlQuery, appName);
        List<BigInteger> num = commonService.findListbySql(sqlQuery);
        BigInteger count = num.get(0);
        return count;

    }

    ;

    private Map<String, Object> populationMap(ApiAppEntity t) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", t.getId());
        map.put("app_name", t.getAppName());
        map.put("app_id", t.getAppId());
        map.put("app_desc", t.getAppDesc());
        map.put("app_version", t.getAppVersion());
//		map.put("app_role_id", t.getAppRoleId());
//		map.put("app_role_name", t.getAppRoleName());
        map.put("create_name", t.getCreateName());
        map.put("create_by", t.getCreateBy());
        map.put("create_date", t.getCreateDate());
        map.put("update_name", t.getUpdateName());
        map.put("update_by", t.getUpdateBy());
        map.put("update_date", t.getUpdateDate());
        return map;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql, ApiAppEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{app_name}", String.valueOf(t.getAppName()));
        sql = sql.replace("#{app_id}", String.valueOf(t.getAppId()));
        sql = sql.replace("#{app_desc}", String.valueOf(t.getAppDesc()));
        sql = sql.replace("#{app_version}", String.valueOf(t.getAppVersion()));
// 		sql  = sql.replace("#{app_role_id}",String.valueOf(t.getAppRoleId()));
// 		sql  = sql.replace("#{app_role_name}",String.valueOf(t.getAppRoleName()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 执行JAVA增强
     */
    private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data) throws Exception {
        if (StringUtil.isNotEmpty(cgJavaValue)) {
            Object obj = null;
            try {
                if ("class".equals(cgJavaType)) {
                    //因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
                    obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                } else if ("spring".equals(cgJavaType)) {
                    obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
                }
                if (obj instanceof CgformEnhanceJavaInter) {
                    CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
                    javaInter.execute("api_app", data);
                }
            } catch (Exception e) {
                throw new Exception("执行JAVA增强出现异常！");
            }
        }
    }

    @Override
    public Object[] getCurrentUserAppIds() {
        List<String> appIds = new ArrayList<>();
        appIds.add("xxxxxx");

        TSUser user = ResourceUtil.getSessionUser();
        String hql = "FROM ApiAppEntity aa WHERE aa.createBy = ? ";
        List<ApiAppEntity> appEntities = systemService.findHql(hql, user.getUserName());
        appEntities.forEach(p -> {
            appIds.add(p.getId());
        });

        return appIds.toArray();
    }

    @Override
    public ApiAppEntity getApiAppEntityById(String id) {
        ApiAppEntity apiAppEntity = systemService.get(ApiAppEntity.class, id);
        return apiAppEntity;
    }

    @Override
    public List<ApiAppEntity> getPublishApiAppList(ApiAppEntity apiApp, HttpServletRequest request) {
        String hql = "from ApiAppEntity app,ApiAppConnect appc where app.createBy =? and app.publishStatus !=-1 and appc.auditStatus=2 and app.id=appc.appId";
//        String type = request.getParameter("type");
        String userName = ResourceUtil.getSessionUser().getUserName();
        List<Object[]> list = null;
        ArrayList<ApiAppEntity> apiAppList = new ArrayList<>();
        if (StringUtil.isNotEmpty(apiApp.getAppName())) {
            //模糊查询
            String s = StringTransUtil.stringReplace(apiApp.getAppName());
            hql += " and app.appName like ?";
//            if (type != null && type.equals("isv")) {
            list = commonService.findHql(hql, userName, "%" + s + "%");
            for (Object[] objects : list) {
                apiAppList.add((ApiAppEntity) objects[0]);
            }
            return apiAppList;
        }
//        }
//        if (type != null && type.equals("isv")) {
        list = commonService.findHql(hql, userName);
//        }

        for (Object[] objects : list) {
            apiAppList.add((ApiAppEntity) objects[0]);
        }
        return apiAppList;
    }

    @Override
    public ApiAppKeysEntity getApiAppKeys(ApiAppEntity apiAppEntity) {
        ApiAppKeysEntity apiAppKeys = new ApiAppKeysEntity();
        apiAppKeys.setAppId(apiAppEntity.getId());
        apiAppKeys.setAppName(apiAppEntity.getAppName());
        String uuid = UUID.randomUUID().toString();
        String passKey = uuid.replace("-", "").substring(0,16);
        apiAppKeys.setPassKey(passKey);
        String uuid2 = UUID.randomUUID().toString();
        String ivStr = uuid2.replace("-", "").substring(0,16);
        apiAppKeys.setIvStr(ivStr);
        return apiAppKeys;
    }

    @Override
    public ApiAppKeysEntity getApiAppKeysEntityByAppId(String appId) {
        String hql="FROM ApiAppKeysEntity WHERE appId=?";
        List<ApiAppKeysEntity> apiAppKeysList = systemService.findHql(hql, appId);
        if (apiAppKeysList != null && apiAppKeysList.size() > 0) {
            return apiAppKeysList.get(0);
        } else {
            return null;
        }
    }
}