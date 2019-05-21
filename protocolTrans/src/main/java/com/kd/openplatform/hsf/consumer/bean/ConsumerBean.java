package com.kd.openplatform.hsf.consumer.bean;

import java.util.List;

import com.kd.openplatform.bean.ColumnsTypeBean;

/**
 * @author zhangsg
 * 消费者bean属性
 */
public class ConsumerBean {
	
	private String consumerId;//id
	private String consumerClass;
	private String consumerGroup;
	private String consumerVersion;
	private String httpRestfulAddr;
	private String hsfMethod;
	private String interfaceType;//接口类型
	private String apiName; 
	
	private List<ColumnsTypeBean> colList;
	
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerClass() {
		return consumerClass;
	}
	public void setConsumerClass(String consumerClass) {
		this.consumerClass = consumerClass;
	}
	public String getConsumerGroup() {
		return consumerGroup;
	}
	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}
	public String getConsumerVersion() {
		return consumerVersion;
	}
	public void setConsumerVersion(String consumerVersion) {
		this.consumerVersion = consumerVersion;
	}
	public String getHttpRestfulAddr() {
		return httpRestfulAddr;
	}
	public void setHttpRestfulAddr(String httpRestfulAddr) {
		this.httpRestfulAddr = httpRestfulAddr;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getHsfMethod() {
		return hsfMethod;
	}
	public void setHsfMethod(String hsfMethod) {
		this.hsfMethod = hsfMethod;
	}
	public List<ColumnsTypeBean> getColList() {
		return colList;
	}
	public void setColList(List<ColumnsTypeBean> colList) {
		this.colList = colList;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
}
