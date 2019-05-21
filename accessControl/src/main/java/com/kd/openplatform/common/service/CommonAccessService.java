package com.kd.openplatform.common.service;

import com.kd.openplatform.common.dao.CommonAccessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Title 公共service类，被具体业务逻辑类继承，业务上请加事务注解！
 * @author shinerio
 * @Description
 */
@Service("commonAccessService")
public class CommonAccessService<T>{
    @Autowired
    protected CommonAccessDao commonAccessDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Object o){
        commonAccessDao.save(o);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Object o){
        commonAccessDao.update(o);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Object o){
       commonAccessDao.delete(o);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Object queryUniqueByProperty(Class<T> entity, Map<String, Object> propertyMap){
        return commonAccessDao.queryUniqueByProperty(entity, propertyMap);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Object queryUniqueByProperty(Class<T> entity, String property, Object value){
        return commonAccessDao.queryUniqueByProperty(entity, property, value);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Object> queryListByProperty(Class<T> entity, Map<String, Object> propertyMap){
        return commonAccessDao.queryListByProperty(entity, propertyMap);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Object> queryListByProperty(Class<T> entity, String property, Object value){
        return commonAccessDao.queryListByProperty(entity, property, value);
    }

    public void saveOrUpdate(Object o){
        commonAccessDao.saveOrUpdate(o);
    }
}
