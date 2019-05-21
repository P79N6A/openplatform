package com.kd.op.util.rtdb;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

/**
 * 校验错误登陆次数
 * @author wtd
 *
 */
public class ValidateErrorLogin {
	private final static Logger logger = Logger.getLogger(ValidateErrorLogin.class);

	//private static int errorTimes = 3;
	//private static int forbiddenTime = 300;
	private static String keyFlag = "_flag";
	private static String listFlag = "_list";
	
	/**
	 * 核查当前错误登陆是否需要需要锁定账号
	 * @param userName
	 * @return
	 */
	public String checkLockLogin(String userName,int errorTimes,int forbiddenTime){
		String time = null;
		Jedis jedis = RtdbClient.getResource();
		//看账号是否连续失败超过一定次数
		String listKey = userName + listFlag;
		try {
			int currentCount = jedis.llen(listKey).intValue();// 当前次数
			if (currentCount < errorTimes - 1) {//再允许连续登陆错误次数-1的范围内
				jedis.lpush(listKey, String.valueOf(System.currentTimeMillis()));// 次数占位
				return time;
			} else {
				long currentTime = System.currentTimeMillis();//当前时间
				long lastestTime = Long.parseLong(jedis.lindex(listKey, currentCount - 1));//表头的调用时间
				//当前登陆是最后一次错误的登陆，然后和上一次的错误登陆相比，如果小于锁定时间，那么就设置禁止标记，并清空list
				if ((currentTime - lastestTime) < forbiddenTime*60* 1000) {
//					jedis.rpop(listKey);
//					jedis.lpush(listKey, String.valueOf(currentTime));
					//账号将被锁定
					jedis.setex(userName + keyFlag, forbiddenTime*60, "1");
					//清空list
					jedis.del(listKey);
					time = forbiddenTime + "分钟";
					return time;
				} else {//否则就替换list中的一个值，使之一直保持在errorTimes-1的数量
					jedis.rpop(listKey);
					jedis.lpush(listKey, String.valueOf(currentTime));
					return time;
				}
			}
		} catch (Exception e) {
			logger.error("checkLockLogin error");
		} finally {
			RtdbClient.returnResource(jedis);
		}
		return time;
	}
	
	/**
	 * 查看当前账号是否允许被登陆
	 * @param jedis
	 * @param userName
	 * @return
	 */
	public static String checkPermite(String userName){
		String time = null;
		Jedis jedis = RtdbClient.getResource();
		String key = userName + keyFlag;
		try {
			if (jedis.exists(key)) {
				Long timeLong = jedis.ttl(key);
				int minute = (int) (timeLong/60);
				int seconds = (int) (timeLong - minute * 60);
				if (minute == 0) {
					time = seconds + "秒";
				}else{
					time = minute + "分" + seconds + "秒";
				}
				return time;
			}
		} catch (Exception e) {
			logger.error("checkPermite error");
		} finally {
			RtdbClient.returnResource(jedis);
		}
		return time;
	}
	
	/**
	 * 如果账号登陆成功，就清空以前的错误登陆信息
	 * @param userName
	 */
	public static void clearErrorLogin(String userName){
		Jedis jedis = RtdbClient.getResource();
		String key = userName + keyFlag;
		String listKey = userName + listFlag;
		try {
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			if (jedis.exists(listKey)) {
				jedis.del(listKey);
			}
		} catch (Exception e) {
			logger.error("clearErrorLogin error");
		} finally {
			RtdbClient.returnResource(jedis);
		}
	}
}
