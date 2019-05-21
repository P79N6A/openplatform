package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiMiddleWareEntity;
import com.kd.op.api.service.ApiMiddleWareService;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("ApiMiddleWareService")
@Transactional
public class ApiMiddleWareServiceImpl extends CommonServiceImpl implements ApiMiddleWareService{
    private static final Logger logger = Logger.getLogger(ApiMonitorServiceImpl.class);
    @Autowired
    private SystemService systemService;

    public void delete(ApiMiddleWareEntity entity) throws Exception{
        super.delete(entity);
    }

    public void save(ApiMiddleWareEntity entity) throws Exception{
        systemService.save(entity);
    }

    public void saveOrUpdate(ApiMiddleWareEntity entity) throws Exception{
        super.saveOrUpdate(entity);
    }

    private Map<String,Object> populationMap(ApiMiddleWareEntity t){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id", t.getId());
        map.put("middle_name", t.getMiddleName());
        map.put("middle_desc", t.getMiddleDesc());
        map.put("open_form", t.getOpenForm());
        map.put("status", t.getStatus());
        return map;
    }

    /**
     * 替换sql中的变量
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql,ApiMiddleWareEntity t){
        sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql  = sql.replace("#{middle_name}",String.valueOf(t.getMiddleName()));
        sql  = sql.replace("#{middle_desc}",String.valueOf(t.getMiddleDesc()));
        sql  = sql.replace("#{open_form}",String.valueOf(t.getOpenForm()));
        sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
        return sql;
    }

    /**
     * 执行JAVA增强
     */
    private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
        if(StringUtil.isNotEmpty(cgJavaValue)){
            Object obj = null;
            try {
                if("class".equals(cgJavaType)){
                    //因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
                    obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                }else if("spring".equals(cgJavaType)){
                    obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
                }
                if(obj instanceof CgformEnhanceJavaInter){
                    CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
                    javaInter.execute("api_middle_ware",data);
                }
            } catch (Exception e) {
                logger.error("e:",e);
                throw new Exception("执行JAVA增强出现异常！");
            }
        }
    }
}
