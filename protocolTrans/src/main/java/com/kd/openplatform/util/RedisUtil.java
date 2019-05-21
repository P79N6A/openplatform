package com.kd.openplatform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Log log = LogFactory.getLog(RedisUtil.class);

    public void deleteRedis() {
        log.info("-----------项目重启，清空redis中webservis相关数据-----------");
        redisTemplate.delete(Constants.REDIS_RECEIVEINFO);
        redisTemplate.delete(Constants.REDIS_WSINFO);
        redisTemplate.delete(Constants.REDIS_TOPIC);
    }
}
