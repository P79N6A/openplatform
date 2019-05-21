package com.kd.openplatform.bean;

import java.util.List;

public class ColumnsTypeBean {
	
	private String id;
	private String key;
	private String type;
	private List<ColumnsTypeBean> beans;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ColumnsTypeBean> getBeans() {
		return beans;
	}
	public void setBeans(List<ColumnsTypeBean> beans) {
		this.beans = beans;
	} 
	

}
