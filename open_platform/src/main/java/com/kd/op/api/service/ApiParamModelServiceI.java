package com.kd.op.api.service;
import com.kd.op.api.entity.ApiOrderDetailEntity;
import com.kd.op.api.entity.ApiOrderEntity;
import com.kd.op.api.entity.ApiParamModelEntity;
import org.jeecgframework.core.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ApiParamModelServiceI extends CommonService {

    //删除参数模型及其关联详情
    public void delApiParamModel(ApiParamModelEntity apiParamModel)throws Exception;

    //添加参数模型及其关联关系
    public void  addApiParamModel(ApiParamModelEntity apiParamModel)throws Exception;

    //更新参数模型及其关联关系
    public void updateApiParamModel(ApiParamModelEntity apiParamModel)throws Exception;

    //获取带有详细信息的参数模型
    public ApiParamModelEntity getApiParamModel(ApiParamModelEntity apiParamModel)throws Exception;
}
