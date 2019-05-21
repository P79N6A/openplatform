package com.kd.openplatform.charge.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import com.kd.openplatform.charge.service.ChargeAccountServiceI;
import com.kd.openplatform.common.entity.ApiAppRelaEntity;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.common.utils.MapCache;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("chargeAccountService")
public class ChargeAccountServiceImpl extends CommonAccessService implements ChargeAccountServiceI {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MapCache mapCache;
    public void delete(ChargeAccountEntity entity) throws Exception {
        super.delete(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ChargeAccountEntity entity) throws Exception {
        super.save(entity);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(ChargeAccountEntity entity) throws Exception {
        super.saveOrUpdate(entity);
    }

    /**
     * 判断是订购的是场景还是服务，并返回帐户
     * @param param 访问参数
     * @return JsonObject ChargeAccountEntity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONObject getChargeApp(JSONObject param) {

//        String hql = "select scene, api from ApiSceneRelaEntity as scene, ApiAppRelaEntity as api where api.appId=:appId and " +
//                "api.userId = :userId and (api.apiId=:apiId or api.sceneId = scene.sceneId) and scene.apiId =:apiId";
    	 String sql = "select api.* from api_app_rela api left join api_scene_rela scene on api.scene_id = scene.scene_id"+
    			 		" where api.user_id = ? and api.app_id =? and (scene.api_id = ? or api.api_id=?)";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setString(0, param.get("userId").toString());
        query.setString(1, param.get("appId").toString());
        query.setString(2, param.get("apiId").toString());
        query.setString(3, param.get("apiId").toString());
        Object object = query.uniqueResult();
        if (object != null) {
            JSONArray jsonArray = JSONObject.parseArray(JSONObject.toJSONString(object));
            String id = jsonArray.getString(0);
            List<ChargeAccountEntity> c = null;
              c =  (List<ChargeAccountEntity>)mapCache.getGetChargeApp().get(id);
            if(c==null){
              c=  (List<ChargeAccountEntity>) queryListByProperty(ChargeAccountEntity.class, "apiAppRelaId", id);
              mapCache.getGetChargeApp().put(id,c);
            }
            param.put("sceneId", jsonArray.get(7));
            param.put("apiAppRelaId", id);

            if (c == null || c.isEmpty()) {
                throw new ControlException(Code.API_UNORDERED_SERVICE_MSG, Code.API_UNORDERED_SERVICE);
            } else {
                return (JSONObject) JSONObject.toJSON(c.get(0));
            }
        } else {
            throw new ControlException(Code.API_UNORDERED_SERVICE_MSG, Code.API_UNORDERED_SERVICE);
        }
    }
}
	