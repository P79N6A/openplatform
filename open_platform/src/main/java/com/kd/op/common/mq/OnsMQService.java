package com.kd.op.common.mq;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;

@Service("onsMqService")
public class OnsMQService{
	private static final Logger logger = Logger.getLogger(OnsMQService.class);
	
	private static OrderProducer producer = null;
	
    public String sendMQMsg(String msg){
    	String resultStr = "";
    	try {
            Message message = new Message(MQConfigUtils.MQ_TOPIC, "*",msg.getBytes("UTF-8"));
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            String orderId = "sess_" + UUID.randomUUID().toString().replaceAll("-","");
            message.setKey(orderId);
            producer.start();
         // 分区顺序消息中区分不同分区的关键字段，sharding key于普通消息的key是完全不同的概念。
            // 全局顺序消息，该字段可以设置为任意非空字符串。
            String shardingKey = String.valueOf(orderId);
            SendResult sendResult = producer.send(message,shardingKey);
            if (sendResult != null) {
                logger.info("Send mq message success! Topic is:" + MQConfigUtils.MQ_TOPIC + " msgId is: " + sendResult.getMessageId());
                resultStr = sendResult.toString();
            }
//            producer.shutdown();
		} catch (Exception e) {
            logger.error("sendMQMsg error");
		}
    	return resultStr;
    }
}
