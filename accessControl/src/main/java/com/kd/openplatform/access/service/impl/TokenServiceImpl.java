package com.kd.openplatform.access.service.impl;

import com.kd.openplatform.access.entity.ApiAppEntity;
import com.kd.openplatform.access.enums.RegisterRouteEnum;
import com.kd.openplatform.common.dao.CommonAccessDao;
import com.kd.openplatform.common.utils.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.internal.PersisterClassResolverInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.entity.UserTokenEntity;
import com.kd.openplatform.access.service.TokenService;
import com.kd.openplatform.charge.entity.ChargeAccountEntity;
import com.kd.openplatform.common.exception.Code;
import com.kd.openplatform.common.exception.ControlException;
import com.kd.openplatform.common.service.CommonAccessService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.SQLTransactionRollbackException;
import java.util.List;
import java.util.Map;


@Component
public class TokenServiceImpl extends CommonAccessService implements TokenService {
    private static final Log log = LogFactory.getLog(TokenServiceImpl.class);

//    @Autowired
//    private SessionFactory sessionFactory;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 检查token是否合合法
     *
     * @param param
     * @return 含有UserId的JsonObject
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONObject getUserIdByToken(JSONObject param) {
        log.info("check token param -------" + param);
        UserTokenEntity userTokenEntity = new UserTokenEntity();
//        UserTokenEntity userTokenEntity = null;
        try {
            log.info("Constants.REDIS_TOKENUSER==========" + Constants.REDIS_TOKENUSER);
            Jedis jedis = jedisPool.getResource();
            Boolean hexists = jedis.hexists(Constants.REDIS_TOKENUSER, param.getString(Constants.TOKENNAME));
            if (hexists) {
                log.info("param.getString(Constants.TOKENNAME)==========" + param.getString(Constants.TOKENNAME));
                List<String> userIds = jedis.hmget(Constants.REDIS_TOKENUSER, param.getString(Constants.TOKENNAME));
                userTokenEntity.setToken(param.getString(Constants.TOKENNAME));
                userTokenEntity.setUserId(userIds.get(0));
//                 userTokenEntity = (UserTokenEntity) commonAccessDao.queryUniqueByProperty(UserTokenEntity.class, Constants.TOKENNAME, param.get(Constants.TOKENNAME).toString());
                log.info("---------" + userTokenEntity + "---------");
                jedis.close();
            } else {
                log.info("find token error -----");
                jedis.close();
                throw new ControlException(Code.API_UNKNOWN_TOKEN_MSG, Code.API_UNKNOWN_TOKEN);
            }
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
        if (userTokenEntity.getUserId() != null) {
            log.info("-------userTokenEntity.getUserId()--------" + userTokenEntity.getUserId());
            log.info("userId::::" + userTokenEntity.getUserId());
            isAuthor(userTokenEntity.getUserId().replace("\"",""));
            param.put("userId", userTokenEntity.getUserId().replace("\"", ""));
            log.info(String.format("检查token是否合合法:%s", param));
            return param;
        } else {
            log.info("can not find this token--------------" + param);
            throw new ControlException(Code.API_TOKEN_CANNOTFIND_MSG, Code.API_TOKEN_CANNOTFIND);
        }
    }

    /**
     * 通过appId查询UserId
     *
     * @param param
     * @return 含有UserId的JsonObject
     */
    @Override
    public JSONObject getUserIdByAppId(JSONObject param) {
        if (param != null) {
            Object appId = param.get("appId");
            if (appId != null) {
                //通过appId查询ApiApp实体
                log.info("commonAccessDao===" + commonAccessDao);
                ApiAppEntity apiAppEntity = (ApiAppEntity) commonAccessDao.getCurrentSession().get(ApiAppEntity.class, appId.toString());
                if (apiAppEntity != null) {
                    //通过ApiApp实体获取userId并添加至param
                    String userId = apiAppEntity.getCreateBy();
                    isAuthor(userId.replace("\"",""));
                    param.put("userId", userId);
                }
            }
        }
        return param;
    }

    /**
     * @return void
     * @Author zyz
     * @Description 验证该账号是否在能力开放平台授权
     * @Date 2019/3/19
     * @Param [userName]
     */
    private void isAuthor(String userName) {
//        Session session = sessionFactory.getCurrentSession();
//        Criteria criteria = session.createCriteria(TsBaseUserEntity.class).add(Restrictions.eq("username", userName));
        String sql = "select register_type,status,username from t_s_base_user where username=?";
//        session.createQuery(sql)
//        List<TsBaseUserEntity> list = criteria.list();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{userName});
        log.info("list=====" + list.size());
        String username = null;
        Integer status = 1;
        if (list != null) {
            for (Map<String, Object> map : list) {
                if (map.get("register_type") != null && map.get("register_type").equals(RegisterRouteEnum.chelianwang.getCode())) {
                    username = (String) map.get("username");
                    status = (Integer) map.get("status");
                    break;
                }
            }
        }
        if (username == null) {
            throw new ControlException(Code.API_ACCOUNT_NOT_AUTHOR_MSG, Code.API_ACCOUNT_NOT_AUTHOR);
        }
        if (status != 1) {
            throw new ControlException(Code.API_ACCOUNT_NOT_ACTIVE_MSG, Code.API_ACCOUNT_NOT_ACTIVE);
        }
    }

	/*@Override
	public JSONObject getTypenameByApiAppReal(JSONObject param) {
		ChargeAccountEntity chargeAccountEntity = new ChargeAccountEntity();
		chargeAccountEntity.setTypename("com.kd.openplatform.charge.service.impl.ChargeByQueryServiceImpl");
		chargeAccountEntity.setId("5");
		chargeAccountEntity.setRestState("10");
		param.put("typename", chargeAccountEntity.getTypename());
		param.put("chargeAccountId", chargeAccountEntity.getId());
		param.put("restState", chargeAccountEntity.getRestState());
		return param;
		ChargeAccountEntity chargeAccountEntity = (ChargeAccountEntity) commonAccessDao.queryUniqueByProperty(ChargeAccountEntity.class, "token", param.get("token").toString());
        if (chargeAccountEntity != null) {
            param.put("typename", chargeAccountEntity.getTypename());
            log.info(String.format("检查token是否合合法:%s", param));
            return param;
        } else {
            throw new ControlException(Code.API_UNKNOWN_TOKEN_MSG, Code.API_UNKNOWN_TOKEN);
        }
	}*/
}
