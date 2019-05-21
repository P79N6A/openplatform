package com.kd.op.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiResourceAccessEntity;
import com.kd.op.api.entity.ApiResourceControlEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;
import java.util.Map;

public interface ApiResourceServiceI extends CommonService {
    public <T> void delete(T entity);
    public void delMain (ApiResourceAccessEntity resourceInfo);

    /**
     * 默认按钮-sql增强-新增操作
     * @param id
     * @return
     */
    public boolean doAddSql(ApiResourceAccessEntity t);
    /**
     * 默认按钮-sql增强-更新操作
     * @param id
     * @return
     */
    public boolean doUpdateSql(ApiResourceAccessEntity t);
    /**
     * 默认按钮-sql增强-删除操作
     * @param id
     * @return
     */
    public boolean doDelSql(ApiResourceAccessEntity t);


    /**
     * 根据ouCode获取对应的商户数据，如果为空就查询所有的商户数据
     * @param ouCodes ouCode集合
     * @return
     */
    public List<Map<String,Object>> getCorps(List<String> ouCodes);

    //查询当前场景或服务是否需要资源控制
    public Integer getResourceCtrlSts(String type,String id)throws Exception;

    /**
     * 查询资源控制范围及选中项
     * @param type 类型 api 或者 scene
     * @param id  api的id 或者 scene的id
     * @param level 1:服务或者场景选择资源范围，2：应用订购服务或者场景时选择资源范围
     * @param  orderId 订单id
     * @return 树的全部节点 和选中节点
     * @throws Exception
     */
    public JSONObject getCorpTree(String type, String id,String level,String orderId)throws Exception;

    /**
     * admin保存资源控制
     * @param drArr
     * @param orderId
     */
    void saveResourceControl(JSONArray drArr, String orderId);

    /**
     * 查看资源控制
     * @param apiId
     * @param orderId
     * @return
     */
    List<ApiResourceControlEntity> findResourceControls(String apiId, String orderId);

    Map<Object,Object> findApiResourceControl(String id, String orderId);
    public JSONArray findApiResourceControlNew(String id, String orderId);
}
