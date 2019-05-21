package com.kd.openplatform.flow.service;

import com.kd.openplatform.charge.entity.ChargeTestAccountEntity;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.flow.entity.ApiFlowModeEntity;
import com.kd.openplatform.flow.entity.ApiFlowModeRelaEntity;
import com.kd.openplatform.measure.service.ChargeAfterFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Component("redisFlowLimiter")
public class RedisFlowLimiter extends CommonAccessService{
    private static final Logger logger = Logger.getLogger(RedisFlowLimiter.class);
    @Autowired
    private JedisPool jedisPool;


    private static final String LUA_NUMBER_SCRIPT = " local current; "
            + " current = redis.call('incr',KEYS[1]); "
            + " if tonumber(current) == 1 then "
            + " 	redis.call('expire',KEYS[1],ARGV[1]); "
            + "     return 1; "
            + " else"
            + " 	if tonumber(current) <= tonumber(ARGV[2]) then "
            + "     	return 1; "
            + "		else "
            + "			return -1; "
            + "     end "
            + " end ";

    private static final String LUA_FLOW_SCRIPT = " local current; "
            + "local exist; "
            + "exist = redis.call('exists',KEYS[1]); "
            + "if tonumber(exist) == 0 then "
            + " 	redis.call('set',KEYS[1],0); "
            + " 	redis.call('expire',KEYS[1],ARGV[2]); "
            + " end "
            + " current = redis.call('INCRBY',KEYS[1],ARGV[1]); "
            + " if tonumber(current) <= tonumber(ARGV[3]) then "
            + " 	return 1; "
            + "	else "
            + "		return -1; "
            + " end ";

    private static final int PERIOD_SECOND_TTL = 2;
    private static final int PERIOD_MINUTE_TTL = 60 + 10;
    private static final int PERIOD_HOUR_TTL = 3600 + 10;
    private static final int PERIOD_DAY_TTL = 3600 * 24 + 10;

    /**
     * 根据apiid和appid两个限制，将流控策略写入Redis中
     * @param keyName apiid和appid组合的key值
     * @param strategy 流量控制策略
     */
    public void writeStrategy2Redis(String keyName, Integer[] strategy){
        if (jedisPool != null) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                int maxUnit = 1;
                for(int i = 0; i < (strategy.length / 3); i++){
                    if(strategy[i *3 + 2] > maxUnit)
                        maxUnit = strategy[i *3 + 2];
                }
                jedis.set(keyName, strategyInteger2String(strategy), "NX", "EX", getExpire(maxUnit));

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    /**
     *从Redis中获得流控策略，如果Redis中没有该流控策略，根据apiid获得流控策略，并将其写入Redis
     * @param apiId
     * @param appId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String getStrategyFromRedis(String apiId, String appId){
        if (jedisPool != null) {
            String strategy = "";
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                strategy = jedis.get(apiId + appId);
                if(strategy == null){
                    ApiFlowModeRelaEntity apiFlowModeRelaEntity = (ApiFlowModeRelaEntity) commonAccessDao.queryUniqueByProperty(ApiFlowModeRelaEntity.class,"apiId",apiId);
                    String flowCtrlMode = apiFlowModeRelaEntity.getFlowCtrlModes();
                    Integer[] reFlowModes = new Integer[3];
                    ApiFlowModeEntity flowMode = (ApiFlowModeEntity) commonAccessDao.queryUniqueByProperty(ApiFlowModeEntity.class,"id",flowCtrlMode);
                    reFlowModes[0] = flowMode.getType();
                    reFlowModes[1] = flowMode.getMaxNum();
                    reFlowModes[2] = flowMode.getUnit();
                    strategy = strategyInteger2String(reFlowModes);
                    writeStrategy2Redis(apiId + appId, reFlowModes);
                }

            } catch (Exception e){
                //e.printStackTrace();
                logger.error(e.getMessage());
            }finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return strategy;
        }
        return "";
    }

    /**
     * 按次进行流量控制
     * @param keyPrefix apiid和appid构成流控策略的key值
     * @param unit 单位，1是秒，2是分，3是时，4是天
     * @param permitsPerUnit 每单位时间限制的次数
     * @return
     */
    public boolean acquireForNumbers(String keyPrefix, int unit, int permitsPerUnit){
        boolean rtv = false;
        if (jedisPool != null) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                String keyName = getKeyName(jedis, keyPrefix, unit);
                List<String> keys = new ArrayList<String>();
                keys.add(keyName);
                List<String> argvs = new ArrayList<String>();
                argvs.add(String.valueOf(getExpire(unit)));
                argvs.add(String.valueOf(permitsPerUnit));
                Long val = (Long)jedis.eval(LUA_NUMBER_SCRIPT, keys, argvs);
                rtv = (val > 0);

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return rtv;
    }

    /**
     * 按流量计算进行流量控制
     * @param keyPrefix apiid和appid构成流控策略的key值
     * @param unit  单位，5是秒，6是分，7是时，8是天
     * @param permitsPerUnit 每单位时间限制的流量大小
     * @param curAccessFlow  当前接入需要的流量大小
     * @return
     */
    public boolean acquireForFlow(String keyPrefix, int unit, int permitsPerUnit, int curAccessFlow){
        boolean rtv = false;
        if (jedisPool != null) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                String keyName = getKeyName(jedis, keyPrefix, unit);
                List<String> keys = new ArrayList<String>();
                keys.add(keyName);
                List<String> argvs = new ArrayList<String>();
                argvs.add(String.valueOf(curAccessFlow));
                argvs.add(String.valueOf(getExpire(unit)));
                argvs.add(String.valueOf(permitsPerUnit));
                Long val = (Long)jedis.eval(LUA_FLOW_SCRIPT, keys, argvs);
                rtv = (val > 0);

            } finally {
                if (jedis != null) {
                    jedis.close();

                }
            }
        }
        return rtv;
    }

    /**
     * 根据appid和apiid的key值获得当前策略的时间戳
     * @param jedis
     * @param keyPrefix
     * @param unit
     * @return
     */
    private String getKeyName(Jedis jedis, String keyPrefix, int unit) {
        String keyName  = "";
        if(1 == unit||unit==5){
            keyName  = keyPrefix + ":" + jedis.time().get(0);
        }else if(2 == unit||unit==6){
            keyName  = keyPrefix + ":" + String.valueOf(Long.parseLong(jedis.time().get(0)) / 60);
        }else if(3 == unit||unit==7){
            keyName  = keyPrefix + ":" + String.valueOf(Long.parseLong(jedis.time().get(0)) / 3600);
        }
        else if(4 == unit||unit==8){
            keyName  = keyPrefix + ":" + String.valueOf(Long.parseLong(jedis.time().get(0)) / (3600 * 24));
        }
        jedis.close();
        return keyName;
    }


    /**
     * 转换流控策略的格式
     * @param src
     * @return
     */
    private String strategyInteger2String(Integer[] src){
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < src.length; i++) {
            str.append(src[i]);
            str.append(",");
        }
        return  str.substring(0, str.length()-1);
    }

    /**
     * 根据流控单位获得该策略的到期时间
     * @param unit 1、5是秒，2、6是分，3、7是时，4、8是天
     * @return
     */
    private int getExpire(int unit) {
        int expire = 0;
        if(1 == unit||unit==5){
            expire = PERIOD_SECOND_TTL;
        }else if(2 == unit||unit==6){
            expire = PERIOD_MINUTE_TTL;
        }else if(3 == unit||unit==7){
            expire = PERIOD_HOUR_TTL;
        }else if(4 == unit||unit==8){
            expire = PERIOD_DAY_TTL;
        }else {
            throw new IllegalArgumentException("flow control : unknown time unit" + unit);
        }
        return expire;
    }

}

