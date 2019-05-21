package com.kd.op.common.mq;

import java.util.Properties;
import java.util.ResourceBundle;

import com.aliyun.openservices.ons.api.PropertyKeyConst;

/**
 * Ali MQ Configuration utils
 * 
 * @author LQ
 *
 */
public class MQConfigUtils {

	public static final Properties MQ_PRODUCER_CONFIG = new Properties();
	public static final String MQ_TOPIC;

	static {
		ResourceBundle resource = ResourceBundle.getBundle("mq");
		MQ_PRODUCER_CONFIG.put(PropertyKeyConst.AccessKey, resource.getString("access_key"));
		MQ_PRODUCER_CONFIG.put(PropertyKeyConst.SecretKey, resource.getString("secret_key"));
		MQ_PRODUCER_CONFIG.put(PropertyKeyConst.ONSAddr, resource.getString("ons_addr"));
		MQ_PRODUCER_CONFIG.put(PropertyKeyConst.ProducerId, resource.getString("producer_id"));
		MQ_PRODUCER_CONFIG.put("ORDER_TOPIC", resource.getString("topic"));
		
		MQ_TOPIC = resource.getString("topic");
	}

}
