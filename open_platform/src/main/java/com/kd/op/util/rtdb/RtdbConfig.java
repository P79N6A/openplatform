package com.kd.op.util.rtdb;

import com.kd.op.common.ObjectUtil;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 
 * @author lq
 * 
 * redis配置读取工具类
 *
 */
public class RtdbConfig {
	private static final Logger logger = Logger.getLogger(RtdbConfig.class);
	
	// Fields
	private static String host;
	private static Integer port;
	private static Integer maxActive;
	private static Integer maxIdle;
	private static Boolean testOnBorrow;
	private static Boolean testOnReturn;
	private static Long maxWait;
	private static Integer timeout;
	private static Integer pipelineSize;
	private static String sentinels;
	private static String master_name;
	private static String password;
	
	// Init
	static {
		ResourceBundle resource = ResourceBundle.getBundle("rtdbsource");
		host = resource.getString("host");
		port = Integer.valueOf(resource.getString("port"));
		maxActive = Integer.valueOf(resource.getString("max_active"));
		maxIdle = Integer.valueOf(resource.getString("max_idle"));
		testOnBorrow = Boolean.valueOf(resource.getString("test_on_borrow"));
		testOnReturn = Boolean.valueOf(resource.getString("test_on_return"));
		maxWait = Long.valueOf(resource.getString("max_wait"));
		timeout = Integer.valueOf(resource.getString("timeout"));
		pipelineSize = Integer.valueOf(resource.getString("pipeline_size"));
		try {
			sentinels = resource.getString("sentinels");
			master_name = resource.getString("master_name");
		} catch (Exception e) {
			logger.error("RtdbConfig:sentinels get error"+e.getMessage());
		}
		try {
			password = resource.getString("password");
		} catch (Exception e) {
			logger.error("RtdbConfig:password get error"+e.getMessage());
		}
	}
	
	// Property Accessor
	/**
	 * Get Redis Server`s Host IP Address
	 * 
	 * @return host ip address, default 127.0.0.1
	 */
	protected static String getHost() {
		return host == null ? "127.0.0.1":host;
	}

	/**
	 * Get Redis Server`s Port Number
	 * 
	 * @return port number, default 6379
	 */
	protected static int getPort() {
		return port == null ? 6379:port;
	}

	/**
	 * Get Redis Server`s Max Active Connection Number
	 * 
	 * @return max active connection number, default 1024
	 */
	protected static int getMaxActive() {
		return maxActive == null ? 1024 : maxActive;
	}

	/**
	 * Get Redis Server`s Max Idle Connection Number
	 * 
	 * @return max idle connection number, default 200
	 */
	protected static int getMaxIdle() {
		return maxIdle == null ? 200 : maxIdle;
	}

	/**
	 * Get Redis Server`s Mode of TestOnBorrow
	 * 
	 * @return mode of testonborrow, default true
	 */
	protected static boolean getTestOnBorrow() {
		return testOnBorrow == null ? true : testOnBorrow;
	}

	/**
	 * Get Redis Server`s Mode of TestOnReturn
	 * 
	 * @return mode of testonreturn, default true
	 */
	protected static boolean getTestOnReturn() {
		return testOnReturn == null ? true : testOnReturn;
	}

	/**
	 * Get Redis Server`s Max Waiting Time
	 * 
	 * @return max waiting time, default 10000ms
	 */
	protected static long getMaxWait() {
		return maxWait == null ? 10000 : maxWait;
	}

	/**
	 * Get Redis Server`s Timeout Config
	 * 
	 * @return timeout config, default 10000
	 */
	protected static int getTimeout() {
		return timeout == null ? 10000 : timeout;
	}

	/**
	 * Get Redis Pipeline Size
	 * 
	 * @return
	 */
	public static int getPipelineSize() {
		return pipelineSize == null ? 3000 : pipelineSize;
	}
	
	/**
	 * Get Redis Sentinels
	 * @return
	 */
	public static Set<String> getSentinels(){
		Set<String> sentinelsSet = new HashSet<String>();
		if (sentinels != null) {
			String[] hostAndPorts = sentinels.split(",");
			for (String hostAndPort : hostAndPorts) {
				sentinelsSet.add(hostAndPort);
			}
		}
		return sentinelsSet;
	}
	
	/**
	 * Get Redis Master Name
	 * @return
	 */
	public static String getMasterName(){
		if (master_name == null) {
			return "mymaster";
		}else {
			return master_name;
		}
	}
	
	/**
	 * @return
	 */
	public static String getPassword(){
		return password.isEmpty()?null:password;
	}
}