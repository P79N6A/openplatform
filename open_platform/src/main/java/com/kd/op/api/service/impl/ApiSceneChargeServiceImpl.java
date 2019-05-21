package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiSceneChargeEntity;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import com.kd.op.api.service.ApiSceneChargeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiSceneChargeService")
@Transactional
public class ApiSceneChargeServiceImpl extends CommonServiceImpl implements ApiSceneChargeServiceI {
    @Autowired
    private SystemService systemService;

    public void save(ApiSceneChargeEntity entity) throws Exception{
        systemService.save(entity);

    }

    @Override
    //保存api信息
    public void addApiSceneCharge (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存服务与计费方式的信息
        String chargeModeIds = SceneTotal.getChargeModeIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (chargeModeIds != null && !chargeModeIds.isEmpty()) {
            String[] ids = chargeModeIds.split(",");
            for (String id : ids) {
                ApiSceneChargeEntity apiSceneCharge=new ApiSceneChargeEntity() ;
                apiSceneCharge.setSceneId(apiScene.getId());
                apiSceneCharge.setSceneName(apiScene.getSceneName());
                apiSceneCharge.setChargeId(id);
                systemService.save(apiSceneCharge);
            }
        }
    }

    @Override
    //更新api信息
    public void updateApiSceneCharge (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存服务与计费方式的信息
        String sql = "delete from api_scene_charge where scene_id='%s'";
        sql = String.format(sql, SceneTotal.getApiScene().getId());
        systemService.executeSql(sql);
        String chargeModeIds = SceneTotal.getChargeModeIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (chargeModeIds != null && !chargeModeIds.isEmpty()) {
            String[] ids = chargeModeIds.split(",");
            for (String id : ids) {
                ApiSceneChargeEntity apiSceneCharge=new ApiSceneChargeEntity() ;
                apiSceneCharge.setSceneId(apiScene.getId());
                apiSceneCharge.setSceneName(apiScene.getSceneName());
                apiSceneCharge.setChargeId(id);
                systemService.save(apiSceneCharge);
            }
        }
    }

    /**
     * 替换sql中的变量
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql,ApiSceneChargeEntity t){
        sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql  = sql.replace("#{scene_id}",String.valueOf(t.getSceneId()));
        sql  = sql.replace("#{scene_name}",String.valueOf(t.getSceneName()));
        sql  = sql.replace("#{charge_id}",String.valueOf(t.getChargeId()));
        sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
        sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
        sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));


        return sql;
    }
}