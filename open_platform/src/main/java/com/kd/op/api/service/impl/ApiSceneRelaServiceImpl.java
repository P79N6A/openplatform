package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiSceneRelaEntity;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import com.kd.op.api.service.ApiSceneRelaServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiSceneRelaService")
@Transactional
public class ApiSceneRelaServiceImpl extends CommonServiceImpl implements ApiSceneRelaServiceI {
    @Autowired
    private SystemService systemService;

    public void save(ApiSceneRelaEntity entity) throws Exception{
        systemService.save(entity);

    }
    @Override
    //更新api信息
    public void updateApiSceneRela (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存服务与计费方式的信息
        String sql = "delete from api_scene_rela where scene_id='%s'";
        sql = String.format(sql, SceneTotal.getApiScene().getId());
        systemService.executeSql(sql);
        String apiInfoIds = SceneTotal.getapiInfoIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (apiInfoIds != null && !apiInfoIds.isEmpty()) {
            String[] ids = apiInfoIds.split(",");
            for (String id : ids) {
                ApiSceneRelaEntity apiSceneRela =new ApiSceneRelaEntity() ;
                apiSceneRela.setSceneId(apiScene.getId());
                apiSceneRela.setSceneName(apiScene.getSceneName());
                apiSceneRela.setApiId(id);
                systemService.save(apiSceneRela);
            }
        }
    }
    @Override
    //保存api信息
    public void addApiSceneRela (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存服务与计费方式的信息
        String apiInfoIds = SceneTotal.getapiInfoIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (apiInfoIds != null && !apiInfoIds.isEmpty()) {
            String[] ids = apiInfoIds.split(",");
            for (String id : ids) {
                ApiSceneRelaEntity apiSceneRela = new ApiSceneRelaEntity() ;
                apiSceneRela.setSceneId(apiScene.getId());
                apiSceneRela.setSceneName(apiScene.getSceneName());
                apiSceneRela.setApiId(id);
//                apiSceneRela.setApiName(apiScene);
                systemService.save(apiSceneRela);
            }
        }
    }

    /**
     * 替换sql中的变量
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql,ApiSceneRelaEntity t){
        sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql  = sql.replace("#{scene_id}",String.valueOf(t.getSceneId()));
        sql  = sql.replace("#{scene_name}",String.valueOf(t.getSceneName()));
        sql  = sql.replace("#{api_id}",String.valueOf(t.getApiId()));
        sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
        sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
        sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));


        return sql;
    }
}