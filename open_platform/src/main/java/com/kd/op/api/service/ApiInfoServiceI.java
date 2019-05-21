package com.kd.op.api.service;

import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.entity.ApiInfoTotalEntity;
import com.kd.op.api.entity.ApiParamEntity;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public interface ApiInfoServiceI extends CommonService {

    public <T> void delete(T entity);

    /**
     * 添加一对多
     */
    public void addMain(ApiInfoEntity apiInfo, String headers, String requests, String returns);

    /**
     * 修改一对多
     */
    public void updateMain(ApiInfoEntity apiInfo, String headers, String requests, String returns);

    public void delMain(ApiInfoEntity apiInfo);

    public Object[] getCurrentUserApiids();

    //根据类名称自动生成HTTP、HSF请求地址
    public ApiInfoEntity generateAddr(ApiInfoEntity apiInfo);

    //根据类名称自动生成webservice标识
    public ApiInfoEntity generateAddrWs(ApiInfoEntity apiInfo);

    //保存api信息
    public void addApi(ApiInfoTotalEntity infoTotal, ApiInfoEntity apiInfo, String headers, String requests, String returns) throws Exception;

    //更新api信息
    public void updateApi(ApiInfoTotalEntity infoTotal, ApiInfoEntity apiInfo, String headers, String requests, String returns) throws Exception;

    //将列表导出成excel
    public void exportExcel(ApiInfoEntity apiInfo, HttpServletResponse response) throws Exception;

    //将excel导入到数据库
    public void importExcel(MultipartFile[] files)throws Exception;

    /**
     * 获取所有的ISV
     * @return
     */
    public List<TSUser> getAllISV();

    /**
     * 根据能力和参数类型获取参数集合
     * @param apiId 能力id
     * @param type 参数类型
     * @return 对应的参数集合
     */
    public List<ApiParamEntity> getByAPiAndType(String apiId,Integer type);

    //根据groupId查询apiInfo
    List<ApiInfoEntity> getApiInfoByGroupId(String groupId);

    void startMQListener(ApiInfoEntity apiInfo);
}
