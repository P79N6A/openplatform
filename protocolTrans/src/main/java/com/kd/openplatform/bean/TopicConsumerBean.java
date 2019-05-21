package com.kd.openplatform.bean;

import com.aliyun.openservices.ons.api.Consumer;

public class TopicConsumerBean {
    String topic = null;
    Consumer consumer = null;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
