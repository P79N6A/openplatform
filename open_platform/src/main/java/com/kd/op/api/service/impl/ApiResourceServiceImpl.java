package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiResourceServiceI;
import com.kd.op.common.CustomConstant;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.*;

@Service("apiResourceServiceI")
@Transactional
public class ApiResourceServiceImpl extends CommonServiceImpl implements ApiResourceServiceI {
    @Autowired
    private SystemService systemService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((ApiResourceAccessEntity) entity);
    }

    public void delMain(ApiResourceAccessEntity resourceInfo) {
        //删除主表信息
        this.delete(resourceInfo);
        //获取参数
        Object id0 = resourceInfo.getId();
        //删除-接口参数
        String hql0 = "from ApiParamEntity where 1 = 1 AND aPI_ID = ? ";
        List<ApiResourceAccessEntity> apiParamOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(apiParamOldList);
    }


    /**
     * 默认按钮-sql增强-新增操作
     *
     * @return
     */
    public boolean doAddSql(ApiResourceAccessEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     *
     * @return
     */
    public boolean doUpdateSql(ApiResourceAccessEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     *
     * @return
     */
    public boolean doDelSql(ApiResourceAccessEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @return
     */
    public String replaceVal(String sql, ApiResourceAccessEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{resource_name}", String.valueOf(t.getResourceName()));
        sql = sql.replace("#{resource_desc}", String.valueOf(t.getResourceDesc()));
        sql = sql.replace("#{resource_place}", String.valueOf(t.getResourcePlace()));
        sql = sql.replace("#{resource_sort}", String.valueOf(t.getResourceSort()));
        sql = sql.replace("#{resource_status}", String.valueOf(t.getResourceStatus()));
        sql = sql.replace("#{api_sort}", String.valueOf(t.getApiSort()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        //sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public List<Map<String, Object>> getCorps(List<String> ouCodes) {
        String sql = "select ou_code,parent_ou_code,ou_name from sys_org where ou_code like '3%'";
        if (ouCodes != null && !ouCodes.isEmpty()) {
            sql += " and ou_code in (:ouCodes)";
        }
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (ouCodes != null && !ouCodes.isEmpty()) {
            query.setParameterList("ouCodes", ouCodes.toArray());
        }
        return query.list();
    }

    @Override
    public Integer getResourceCtrlSts(String type, String id) throws Exception {
        if (StringUtil.isNotEmpty(type) && StringUtil.isNotEmpty(id)) {
            if (CustomConstant.API.equals(type)) {
                ApiInfoEntity apiInfo = systemService.get(ApiInfoEntity.class, id);
                if (apiInfo != null) {
                    return apiInfo.getResourceCtrl();
                }
            }
        }

        return 0;
    }

    @Override
    public JSONObject getCorpTree(String type, String id, String level, String orderId) throws Exception {
        //构建树
        JSONObject result = new JSONObject();
        //组织机构id
        List<String> ouCodes = new ArrayList<>();
        //全部节点
        List<Map<String, Object>> allNodes = new ArrayList<>();
        //已选中节点
        List<String> checkedNodes = new ArrayList<>();

        //ISP服务或场景选择计费模型
        if (StringUtil.isNotEmpty(level) && CustomConstant.LEVEL_PUB.equals(level)) {
            if (StringUtil.isNotEmpty(type)) {
                //全部节点有组织机构确定
                if (CustomConstant.API.equals(type)) {//获取服务已经选择的资源范围，用于回显
                    List<ApiResourceAccessRelaEntity> resourceRels = systemService.findByProperty(ApiResourceAccessRelaEntity.class, "apiId", id);
                    for (ApiResourceAccessRelaEntity rela : resourceRels) {
                        checkedNodes.add(rela.getResourceId());
                    }
                } else if (CustomConstant.SCENE.equals(type)) {//获取场景已经选择的资源范围，用于回显
                    List<ApiSceneResourceEntity> resourceRels = systemService.findByProperty(ApiSceneResourceEntity.class, "sceneId", id);
                    for (ApiSceneResourceEntity rela : resourceRels) {
                        checkedNodes.add(rela.getResourceId());
                    }
                }
            }
            //ISV端订购服务或者场景
        } else if (StringUtil.isNotEmpty(level) && CustomConstant.LEVEL_ORDER.equals(level)) {

            if (StringUtil.isNotEmpty(type)) {
                //防止为空加载全部
                ouCodes.add("xxxxxxx");
                //如果有orderId只显示订购完成的
                if (StringUtil.isNotEmpty(orderId)) {
                    //查询服务的计费模型
                    String queryOrderApiCharges = "FROM ApiOrderDetailEntity WHERE orderId = ? ";
                    if (CustomConstant.API.equals(type)) {
                        queryOrderApiCharges += " AND apiId = ? ";
                    } else if (CustomConstant.SCENE.equals(type)) {
                        queryOrderApiCharges += " AND sceneId = ? ";
                    }
                    List<ApiOrderDetailEntity> apiChargeModeRelas = systemService.findHql(queryOrderApiCharges, orderId, id);
                    if (apiChargeModeRelas != null && apiChargeModeRelas.size() > 0) {
                        apiChargeModeRelas.forEach(p -> {
                            ouCodes.addAll(Arrays.asList(p.getResourceId().split(",")));
                        });
                    }

                    //资源选择范围为该服务或者场景的ISP端确定的资源范围
                } else if (CustomConstant.API.equals(type)) {
                    //获取服务已经订购的资源范围
                    List<ApiResourceAccessRelaEntity> resourceRels = systemService.findByProperty(ApiResourceAccessRelaEntity.class, "apiId", id);
                    for (ApiResourceAccessRelaEntity rela : resourceRels) {
                        ouCodes.add(rela.getResourceId());
                    }
                } else if (CustomConstant.SCENE.equals(type)) {

                    //获取场景已经订购的资源范围
                    List<ApiSceneResourceEntity> resourceRels = systemService.findByProperty(ApiSceneResourceEntity.class, "sceneId", id);
                    for (ApiSceneResourceEntity rela : resourceRels) {
                        ouCodes.add(rela.getResourceId());
                    }
                }
            }
        }
        allNodes = this.getCorps(ouCodes);
        JSONArray nodes = handleCorps(allNodes);
        result.put("nodes", nodes);
        result.put("checked", checkedNodes);
        return result;
    }

    /**
     * admin保存资源控制
     *
     * @param drArr
     * @param orderId
     */
    @Override
    public void saveResourceControl(JSONArray drArr, String orderId) {
        //获取appId
        String sql = "select app_id from api_order where id = :oId";
        Query query = getSession().createSQLQuery(sql).setParameter("oId", orderId);
        String appId = query.list().get(0).toString();
        TSUser sessionUser = ResourceUtil.getSessionUser();
        String parentId = null;
        String paramNameEnd = null;
        String dataType = null;
        //获取当前用户名
        String userName = sessionUser.getUserName();
        for (Object object : drArr) {

            //前端数组转换成map
            String obj = JSONArray.toJSONString(object);
            Map<String, String> map = JSON.parseObject(obj, Map.class);
            parentId = map.get("parentId");
            paramNameEnd = map.get("paramName");
            dataType = map.get("dataType");

          if (!dataType.equals("3")){
              while (parentId != null) {
                  //根据id查询是否有父级存在
                  String parentHql = "from ApiParamEntity where id =?";
                  List<ApiParamEntity> apiParamEntities = systemService.findHql(parentHql, parentId);
                  for(ApiParamEntity apiParamEntity : apiParamEntities){
                      if (apiParamEntity.getParamName() != null) {
                          paramNameEnd = apiParamEntity.getParamName() + ":" + paramNameEnd;
                      }
                      parentId = apiParamEntity.getParentId();

                  }


              }

              String selectHql = "from ApiResourceControlEntity where apiId=? and appId=? and resourceParamName =?";
              List<Object> apiRCList = systemService.findHql(selectHql, map.get("id"), appId, paramNameEnd);
              if (apiRCList != null && apiRCList.size() > 0) {
                  String updateSql = "update api_resource_control set resource_param_value =:rpv,update_by=:ub,update_time=:ut where api_id=:apiId and app_id=:appId and resource_param_name=:rpn";
                  getSession().createSQLQuery(updateSql).setParameter("rpv", map.get("value")).setParameter("ub", userName).setParameter("ut", new Date()).setParameter("apiId", map.get("id")).setParameter("appId", appId).setParameter("rpn", paramNameEnd).executeUpdate();
              } else {
                  //表中没数据插入
                  List<ApiResourceControlEntity> entityList = new ArrayList<>();
                  ApiResourceControlEntity apiResourceControlEntity = new ApiResourceControlEntity();
                  apiResourceControlEntity.setApiId(map.get("id"));
                  apiResourceControlEntity.setAppId(appId);
                  apiResourceControlEntity.setUpdateBy(userName);
                  apiResourceControlEntity.setUpdateTime(new Date());
                  apiResourceControlEntity.setResourceParamName(paramNameEnd);
                  apiResourceControlEntity.setResourceParamValue(map.get("value"));
                  entityList.add(apiResourceControlEntity);
                  systemService.batchSave(entityList);

              }
          }


        }

    }


    /*@Override
    public void saveResourceControl(JSONArray drArr, String orderId) {
        //获取appId
        String sql = "select app_id from api_order where id = :oId";
        Query query = getSession().createSQLQuery(sql).setParameter("oId", orderId);
        String appId = query.list().get(0).toString();
        TSUser sessionUser = ResourceUtil.getSessionUser();
        //获取当前用户名
        String userName = sessionUser.getUserName();
        //前端数组转换成json串
        String s = drArr.toString().replace("[", "{").replace("=", "\":\"").replace("]", "}");
        //前端数组转换成map
        Map<String,String> map = JSON.parseObject(s,Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String selectHql = "from ApiResourceControlEntity where apiId=? and appId=? and resourceParamName =?";
            if (!entry.getKey().equalsIgnoreCase("id")){
                //表中有数据就更改表
                List<Object> apiRCList = systemService.findHql(selectHql, map.get("id"), appId, entry.getKey());
                if (apiRCList!=null && apiRCList.size()>0){
                    String updateSql = "update api_resource_control set resource_param_value =:rpv,update_by=:ub,update_time=:ut where api_id=:apiId and app_id=:appId and resource_param_name=:rpn";
                    getSession().createSQLQuery(updateSql).setParameter("rpv", entry.getValue()).setParameter("ub", userName).setParameter("ut", new Date()).setParameter("apiId", map.get("id")).setParameter("appId", appId).setParameter("rpn", entry.getKey()).executeUpdate();
                }else{
                    //表中没数据插入
                    List<ApiResourceControlEntity> entityList = new ArrayList<>();
                    ApiResourceControlEntity apiResourceControlEntity = new ApiResourceControlEntity();
                    if (entry.getKey()!="id"){
                        apiResourceControlEntity.setApiId(map.get("id"));
                        apiResourceControlEntity.setAppId(appId);
                        apiResourceControlEntity.setUpdateBy(userName);
                        apiResourceControlEntity.setUpdateTime(new Date());
                        apiResourceControlEntity.setResourceParamName(entry.getKey());
                        apiResourceControlEntity.setResourceParamValue(entry.getValue());
                        entityList.add(apiResourceControlEntity);
                    }
                    systemService.batchSave(entityList);

                }
            }
        }
    }
*/
    @Override
    public List<ApiResourceControlEntity> findResourceControls(String apiId, String orderId) {

        String appIdSql = "select app_id from api_order where id = :id";
        Query query = getSession().createSQLQuery(appIdSql).setParameter("id", orderId);
        String appId = query.list().get(0).toString();

        String queryApiResourceControl = "FROM ApiResourceControlEntity WHERE apiId = ? AND appId = ?";
        List<ApiResourceControlEntity> result = systemService.findHql(queryApiResourceControl, apiId, appId);
        return result;
    }

    @Override
    public Map<Object, Object> findApiResourceControl(String id, String orderId) {
        String hql = "from ApiParamEntity where apiId=? and isSource=?";
        List<ApiParamEntity> list = systemService.findHql(hql, id, CustomConstant.IS_RESOURCE);
        HashMap<Object, Object> paramMap = new HashMap<>();
        //获取appId
        String sql = "select app_id from api_order where id = :oId";
        Query query = getSession().createSQLQuery(sql).setParameter("oId", orderId);
        String appId = query.list().get(0).toString();

        String resourceHql = "from ApiResourceControlEntity where apiId=? and resourceParamName=? and appId=?";
        for (ApiParamEntity entity : list) {
            List<ApiResourceControlEntity> result = systemService.findHql(resourceHql, id, entity.getParamName(), appId);
            if (result.size() == 0 || result.isEmpty()) {
                paramMap.put(entity.getParamName(), "");
            } else {
                for (ApiResourceControlEntity apiResourceControlEntity : result) {
                    paramMap.put(entity.getParamName(), apiResourceControlEntity.getResourceParamValue());
                }
            }
        }


        return paramMap;
    }

    @Override
    public JSONArray findApiResourceControlNew(String id, String orderId) {
        JSONArray jsonArray = new JSONArray();
        String parentId = null;
        String apiResourceControlValue = null;
        //获取可资源控制参数
        String hql = "from ApiParamEntity where apiId=? and isSource=?";
        List<ApiParamEntity> list = systemService.findHql(hql, id, CustomConstant.IS_RESOURCE);
        HashMap<String, Object> paramMap = new HashMap<>();
        //获取appId
        String sql = "select app_id from api_order where id = :oId";
        Query query = getSession().createSQLQuery(sql).setParameter("oId", orderId);
        String appId = query.list().get(0).toString();

        for (ApiParamEntity entity : list) {
            JSONObject json = new JSONObject();
            String visibleState = null;
            parentId = entity.getParentId();
            json.put("value", "");
            //根据参数类型标记是否可见
            if (entity.getDataType() == 3) {
                visibleState = "none";
            } else {
                visibleState = "block";
                //获取已配置资源控制信息
                String selectHql = "from ApiResourceControlEntity where apiId=? and appId=? ";
                List<ApiResourceControlEntity> apiResourceControlEntityList = systemService.findHql(selectHql, entity.getId(), appId);
                if (apiResourceControlEntityList.size() > 0) {
                    for (ApiResourceControlEntity apiResourceControlEntity : apiResourceControlEntityList) {
                        apiResourceControlValue = apiResourceControlEntity.getResourceParamValue();
                        json.put("value", apiResourceControlValue);
                    }
                }
            }
            json.put("id", entity.getId());
            json.put("paramName", entity.getParamName());
            json.put("parentId", entity.getParentId());
            json.put("dataType", entity.getDataType());
            json.put("visibleState", visibleState);
            jsonArray.add(json);

            while (parentId != null) {
                JSONObject parentJson = new JSONObject();
                //根据parentId获取父级
                String parentHql = "from ApiParamEntity where id =?";
                List<ApiParamEntity> apiParamEntities = systemService.findHql(parentHql, parentId);

                for (ApiParamEntity apiParamEntity : apiParamEntities) {
                    parentJson.put("value","");
                    if (apiParamEntity.getDataType() == 3) {
                        visibleState = "none";
                    } else {
                        visibleState = "block";
                        String selectHql = "from ApiResourceControlEntity where apiId=? and appId=?";
                        List<ApiResourceControlEntity> apiResourceControlEntityList = systemService.findHql(selectHql, apiParamEntity.getApiId(), appId);
                        if (apiResourceControlEntityList.size() > 0) {
                            for (ApiResourceControlEntity apiResourceControlEntity : apiResourceControlEntityList) {
                                apiResourceControlValue = apiResourceControlEntity.getResourceParamValue();
                                parentJson.put("value", apiResourceControlValue);
                            }
                        }
                    }
                    parentJson.put("id", apiParamEntity.getId());
                    parentJson.put("paramName", apiParamEntity.getParamName());
                    parentJson.put("parentId", apiParamEntity.getParentId());
                    parentJson.put("dataType", apiParamEntity.getDataType());
                    parentJson.put("visibleState", visibleState);
                    if(!jsonArray.contains(parentJson)){
                        jsonArray.add(parentJson);
                    }
                    parentId = apiParamEntity.getParentId();
                }


            }
        }
        return jsonArray;
    }

       /* String visibleState = null;
        String resourceHql = "from ApiResourceControlEntity where apiId=? and resourceParamName=? and appId=?";
        for (ApiParamEntity entity : list) {
            if(entity.getDataType()==3){
                visibleState = "none";
            }else {
                visibleState = "block";
            }
            List<ApiResourceControlEntity> result = systemService.findHql(resourceHql, id, entity.getParamName(),appId);
            if (result.size()==0 || result.isEmpty()){
                JSONObject json = new JSONObject();
                json.put("id", entity.getId());
                json.put("paramName", entity.getParamName());
                json.put("parentId", entity.getParentId());
                json.put("paramVisible", entity.getparamVisible());
                json.put("paramEncrypt", entity.getParamEncrypt());
                json.put("isSource", entity.getIsSource());
                json.put("paramDesc", entity.getParamDesc());
                json.put("dataType", entity.getDataType());
                json.put("value", "");
                json.put("visibleState",visibleState);
                jsonArray.add(json);
            }else{
                for (ApiResourceControlEntity apiResourceControlEntity : result) {
                    JSONObject json = new JSONObject();
                    json.put("id", entity.getId());
                    json.put("paramName", apiResourceControlEntity.getResourceParamName());
                    json.put("parentId", entity.getParentId());
                    json.put("paramVisible", entity.getparamVisible());
                    json.put("paramEncrypt", entity.getParamEncrypt());
                    json.put("isSource", entity.getIsSource());
                    json.put("paramDesc", entity.getParamDesc());
                    json.put("dataType", entity.getDataType());
                    json.put("Value", apiResourceControlEntity.getResourceParamValue());
                    json.put("visibleState",visibleState);
                    jsonArray.add(json);
                }
            }
        }
        return jsonArray;*/


    /**
     * 将组织机构数据转换为树节点数据
     * @param list
     * @return
     */
    public JSONArray handleCorps(List<Map<String,Object>> list){
        JSONArray array = new JSONArray();
        for(Map<String,Object> map:list){
            JSONObject obj = new JSONObject();
            obj.put("id",map.get("ou_code") + "");
            obj.put("name",map.get("ou_name") + "");
            obj.put("pId",map.get("parent_ou_code") + "");
            array.add(obj);
        }
        return array;
    }

}
