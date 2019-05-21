package com.kd.op.api.service.impl;


import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiParamModelServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("apiParamModelService")
@Transactional
public class ApiParamModelServiceImpl extends CommonServiceImpl implements ApiParamModelServiceI {

    @Autowired
    private SystemService systemService;


    @Override
    public void delApiParamModel(ApiParamModelEntity apiParamModel) throws Exception {
            apiParamModel = get(ApiParamModelEntity.class,apiParamModel.getId());
            apiParamModel.setModelStatus(-1);
            this.saveOrUpdate(apiParamModel);
    }

    @Override
    public void addApiParamModel(ApiParamModelEntity apiParamModel) throws Exception {
        //保存主表信息
//        apiParamModel.setModelStatus(0);
        apiParamModel.setModelStatus(1);
        this.save(apiParamModel);
        //为关联表增加主表id
        if(apiParamModel.getParamModelDetails()!=null && apiParamModel.getParamModelDetails().size()>0){
            apiParamModel.getParamModelDetails().forEach(p->{
                p.setModelId(apiParamModel.getId());
            });

            //保存关联表信息
            this.batchSave(apiParamModel.getParamModelDetails());
        }

    }

    @Override
    public void updateApiParamModel(ApiParamModelEntity apiParamModel) throws Exception {
        //修改主表信息
        ApiParamModelEntity apiParamModelOld = this.get(ApiParamModelEntity.class,apiParamModel.getId());
        MyBeanUtils.copyBeanNotNull2Bean(apiParamModel,apiParamModelOld);
        this.saveOrUpdate(apiParamModelOld);
        //修改关联表信息 先删除历史数据
        List<ApiParamModelDetailEntity> apiParamModelDetails = this.findByProperty(ApiParamModelDetailEntity.class,"modelId",apiParamModel.getId());
        this.deleteAllEntitie(apiParamModelDetails);
        apiParamModel.getParamModelDetails().forEach(p->{
            p.setModelId(apiParamModel.getId());
        });
        this.batchSave(apiParamModel.getParamModelDetails());
    }

    @Override
    public ApiParamModelEntity getApiParamModel(ApiParamModelEntity apiParamModel) throws Exception {
        apiParamModel = this.getEntity(ApiParamModelEntity.class, apiParamModel.getId());
        //填充详细信息
        List<ApiParamModelDetailEntity> apiParamModelDetails = this.findByProperty(ApiParamModelDetailEntity.class,"modelId",apiParamModel.getId());
        apiParamModel.setParamModelDetails(apiParamModelDetails);
        return apiParamModel;
    }
}