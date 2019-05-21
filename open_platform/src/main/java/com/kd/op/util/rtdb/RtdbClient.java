package com.kd.op.util.rtdb;

import java.util.Set;


import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

/**
 * redis sentinel client
 * 
 * @author lq
 *
 */
public class RtdbClient {
	private final static Logger logger = Logger.getLogger(RtdbClient.class);
	private static JedisPoolConfig poolConfig;
	private static Set<String> sentinels = RtdbConfig.getSentinels();
	private static Pool<Jedis> pool;

	static {
		poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(RtdbConfig.getMaxIdle());
		poolConfig.setMaxWaitMillis(RtdbConfig.getMaxWait());
		poolConfig.setMaxTotal(RtdbConfig.getMaxActive());
		poolConfig.setTestOnBorrow(RtdbConfig.getTestOnBorrow());
		poolConfig.setTestOnReturn(RtdbConfig.getTestOnReturn());
		initPool();
	}

	private static void initPool() {
		try {
			// 优先采用哨兵监控模式
			if (!sentinels.isEmpty()) {
//				pool = new JedisSentinelPool(RtdbConfig.getMasterName(), sentinels, poolConfig, RtdbConfig.getTimeout());
				pool = new JedisSentinelPool(RtdbConfig.getMasterName(), 
						sentinels, poolConfig, RtdbConfig.getTimeout(), RtdbConfig.getPassword());
			} else {
				pool = new JedisPool(poolConfig, RtdbConfig.getHost(), RtdbConfig.getPort(), RtdbConfig.getTimeout(), null,
						Protocol.DEFAULT_DATABASE, null);
//				pool = new JedisPool(poolConfig, RtdbConfig.getHost(), RtdbConfig.getPort(), RtdbConfig.getTimeout(), RtdbConfig.getPassword(),
//						Protocol.DEFAULT_DATABASE, null);
			}
		} catch (Exception e) {
			logger.error("initPool error");
		}
	}

	/**
	 * init sentinel pool
	 * 
	 * @return
	 */
	private static Pool<Jedis> getPool() {
		if (pool != null) {
			return pool;
		}

		initPool();

		return pool;
	}

	public static Jedis getResource() {
		return (Jedis) getPool().getResource();
	}

	public static void returnResource(Jedis resource) {
		if (getPool() != null && resource != null) {
			resource.close();
		}
	}
}
