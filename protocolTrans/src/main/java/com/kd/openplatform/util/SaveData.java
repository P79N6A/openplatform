package com.kd.openplatform.util;

import com.kd.openplatform.accessControl.AccessControl;
import com.kd.openplatform.entity.UserToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaveData {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Log log = LogFactory.getLog(SaveData.class);

    public void saveUserTokenSimple(String userName, String authToken) {
        try {
            log.info("保存authToken----------------:");
            log.info("userName---------" + userName);
            log.info("token---------" + authToken);
            UserToken userToken = new UserToken();
            userToken.setUserId(userName);
            userToken.setToken(authToken);
            if (userToken.getUserId() != null && !userToken.getUserId().isEmpty()) {
                String redisToken = (String) redisTemplate.boundHashOps(Constants.REDIS_USERTOKEN).get(userToken.getUserId());
                if (redisToken != null && !redisToken.isEmpty()) {
                    redisTemplate.boundHashOps(Constants.REDIS_TOKENUSER).delete(redisToken);
                }
            }
            redisTemplate.boundHashOps(Constants.REDIS_USERTOKEN).put(userToken.getUserId(), userToken.getToken());
            redisTemplate.boundHashOps(Constants.REDIS_TOKENUSER).put(userToken.getToken(), userToken.getUserId());
            log.info("------token save success-------");
        } catch (DataAccessException e) {
            log.info("token save err", e);
        }
    }
}
