package com.kd.openplatform.common.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Title 公共dao类，被具体业务逻辑类继承
 * @author shinerio
 * @Description
 */
@Component("commonAccessDao")
public class CommonAccessDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Object o){
        Session session = getCurrentSession();
        session.save(o);
    }

    public void update(Object o){
        Session session  = getCurrentSession();
        session.update(o);
    }

    public void delete(Object o){
        Session session = getCurrentSession();
        session.delete(o);
    }

    public Object queryUniqueByProperty(Class<T> entity, Map<String, Object> propertyMap){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(entity).add(Restrictions.allEq(propertyMap));
        return  criteria.uniqueResult();
    }

    public Object queryUniqueByProperty(Class<T> entity, String property, Object value){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(entity).add(Restrictions.eq(property, value));
        return  criteria.uniqueResult();
    }
    public List<Object> queryListByProperty(Class<T> entity, Map<String, Object> propertyMap){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(entity).add(Restrictions.allEq(propertyMap));
        return  criteria.list();
    }

    public List<Object> queryListByProperty(Class<T> entity, String property, Object value){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(entity).add(Restrictions.eq(property, value));
        return  criteria.list();
    }

    public void saveOrUpdate(Object o){
        Session session = getCurrentSession();
        session.saveOrUpdate(o);
    }
}
