package com.kd.op.api.service;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiOrderDetailEntity;
import com.kd.op.api.entity.ApiOrderEntity;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

public interface OrderServiceI extends CommonService {

    //保存订单
    public void saveOrUpdateOrder(ApiOrderEntity apiOrder,HttpServletRequest request) throws Exception;
    //保存订单中的其他配置信息
    void saveOrUpdateOrderConfig(ApiOrderEntity apiOrder,HttpServletRequest request) ;

    //获取订单详情列表返回json格式字符串
    public String getOrderDetailJSON(ApiOrderEntity apiOrder) throws Exception;

    //获取订单详情列表
    public List<ApiOrderDetailEntity> getOrderDetail(ApiOrderEntity apiOrder) throws Exception;

    //获取订单详情列表中的服务或者场景id列表
    public String[] getOrderDetailAPiOrSceneIds(String orderId) throws Exception;

    //场景id的服务id列表
    public String[] getSceneApiIds(String sceneId) throws Exception;

    //获取ISP已选择的场景或服务的计费模型id列表
    public String[] getChargeIdsByApiOrScene(String apiId,String sceneId,String orderId) throws Exception;

    //订单审核
    public void doOrderAudit(ApiOrderEntity apiOrder)throws Exception;


    //获取ISP已选择的计费模型id列表
    public String[] getChargeIdsByAppId(String appId,String orderId) throws Exception;

    public String[] getChargeIdsByAppId(String appId) throws Exception;

    public void saveOrUpdateOrderId(ApiOrderEntity order, HttpServletRequest request) throws Exception;

    public DataGrid queryApiDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid);

    public DataGrid querySceneDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid);

    public DataGrid queryAppDatagird(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid);


    String findRoleById(String id);
}
