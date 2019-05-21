package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.api.entity.ApiSceneResourceEntity;
import com.kd.op.api.entity.ApiSceneTotalEntity;
import com.kd.op.api.service.ApiSceneResourceServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

@Service("apiSceneResourceService")
@Transactional
public class ApiSceneResourceServiceImpl extends CommonServiceImpl implements ApiSceneResourceServiceI {
    @Autowired
    private SystemService systemService;

    public void save(ApiSceneResourceEntity entity) throws Exception{
        systemService.save(entity);

    }

    @Override
    //保存api信息
    public void addApiSceneResource (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存服务与资源的信息
        String resourceAccessIds = SceneTotal.getResourceAccessIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (resourceAccessIds != null && !resourceAccessIds.isEmpty()) {
            String[] ids = resourceAccessIds.split(",");
            for (String id : ids) {
                ApiSceneResourceEntity apiSceneResource=new ApiSceneResourceEntity() ;
                apiSceneResource.setSceneId(apiScene.getId());
                apiSceneResource.setSceneName(apiScene.getSceneName());
                apiSceneResource.setResourceId(id);
                systemService.save(apiSceneResource);
            }
        }
    }

    @Override
    //更新api信息
    public void updateApiSceneResource (ApiSceneTotalEntity SceneTotal) throws Exception {
        //保存数据资源信息
        String sqlResource = "delete from api_scene_resource where scene_id='%s'";
        sqlResource = String.format(sqlResource, SceneTotal.getApiScene().getId());
        systemService.executeSql(sqlResource);
        String recourceAccessIds = SceneTotal.getResourceAccessIds();
        ApiSceneEntity apiScene=SceneTotal.getApiScene();
        if (recourceAccessIds != null && !recourceAccessIds.isEmpty()) {
            String[] ids = recourceAccessIds.split(",");
            for (String id : ids) {
                ApiSceneResourceEntity apiSceneResource=new ApiSceneResourceEntity() ;
                apiSceneResource.setSceneId(apiScene.getId());
                apiSceneResource.setSceneName(apiScene.getSceneName());
                apiSceneResource.setResourceId(id);
                systemService.save(apiSceneResource);
            }
        }
    }
    /**
     * 替换sql中的变量
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql,ApiSceneResourceEntity t){
        sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql  = sql.replace("#{scene_id}",String.valueOf(t.getSceneId()));
        sql  = sql.replace("#{scene_name}",String.valueOf(t.getSceneName()));
        sql  = sql.replace("#{resource_id}",String.valueOf(t.getResourceId()));
        sql  = sql.replace("#{resource_name}",String.valueOf(t.getResourceName()));
        sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
        sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
        sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));


        return sql;
    }
}
