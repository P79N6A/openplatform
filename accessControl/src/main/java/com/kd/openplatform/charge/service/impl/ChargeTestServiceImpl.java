package com.kd.openplatform.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import com.kd.openplatform.charge.service.ChargeTestAccountServiceI;
import com.kd.openplatform.charge.service.ChargeTestServiceI;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component("chargeTestService")
public class ChargeTestServiceImpl implements ChargeTestServiceI {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    ChargeTestAccountServiceI chargeTestAccountService;

    /**
     * 执行扣费
     * @param c 帐户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void charge(ChargeTestAccountEntity c) {
        try {
            Integer newState = Integer.parseInt(c.getRestState()) - 1;
            c.setRestState(String.valueOf(newState));
            chargeTestAccountService.saveOrUpdate(c);
        } catch (Exception e) {
            throw new ControlException(e.getMessage());
        }
    }

    /**
     * 检查余额
     * @param c 帐户
     * @throws ControlException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void checkStatus(ChargeTestAccountEntity c) throws ControlException {
        if (Integer.parseInt(c.getRestState()) > 0) {
            try {
                chargeTestAccountService.saveOrUpdate(c);
            } catch (Exception e) {
                throw new ControlException(e.getMessage());
            }
        } else {
            throw new ControlException(Code.API_TRY_INSUFFICIENT_MSG,Code.API_TRY_INSUFFICIENT);

        }
    }

    /**
     * 检查测试帐户
     * @param param 访问参数
     * @return 测试帐户余额
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ChargeTestAccountEntity getTestAccount(JSONObject param) {
        String hql = "from ChargeTestAccountEntity where apiId = :apiId and userId = :userId and invokeMethod = :appId";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setString("apiId", param.getString("apiId"));
        query.setString("userId", param.getString("userId"));
        query.setString("appId", param.getString("appId"));
        ChargeTestAccountEntity c = (ChargeTestAccountEntity) query.uniqueResult();
        return c;
    }
}
