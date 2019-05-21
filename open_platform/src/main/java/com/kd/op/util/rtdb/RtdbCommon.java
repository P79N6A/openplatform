package com.kd.op.util.rtdb;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.util.RandomStr;
import org.jeecgframework.web.system.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * redis缓存公共方法类
 */
@Component
public class RtdbCommon {
    private static final Logger logger = Logger.getLogger(RtdbCommon.class);
    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public static String loadAccessToken() {
        String accessToken = null;
        Jedis jedis = RtdbClient.getResource();
        try {
            accessToken = jedis.get("ACCESSTOKEN");
            accessToken = accessToken.replaceAll("\"", "");
        } catch (Exception e) {
            logger.error("获取accessToken失败：" + e.getMessage());
        } finally {
            RtdbClient.returnResource(jedis);
        }
        return accessToken;
    }


    public static void saveTopic() {
        Jedis jedis = RtdbClient.getResource();
        try {
            //清空redis
            jedis.del("TOPICLIST");
            //更新redis记录
            String sql = "select distinct api_class_name topic from api_info where pub_mode=4";
            List<Map<String, Object>> topics = jdbcTemplate.queryForList(sql);
            for (int i = 0; i < topics.size(); i++) {
                if (topics.get(i) != null && topics.get(i).get("topic") != null) {
                    String topic = (String) topics.get(i).get("topic");
                    jedis.lset("TOPICLIST", i, topic);
                }
            }
        } catch (Exception e) {
            logger.error("获取accessToken失败：" + e.getMessage());
        } finally {
            RtdbClient.returnResource(jedis);
        }
    }
}
