package com.kd.op.common.mq;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @Description: 
 *
 * @author LQ
 *
 * @date 2018年5月29日
 */
public class Notification implements Serializable{
	/** Serial Version UID */
	private static final long serialVersionUID = 5279802769961587692L;

	/*
	 * 通知类型：
	 * 1.主题规则变更通知。
	 * 2.数据库配置变更通知。
	 * 3.任务操作通知。
	 */
	@JSONField(name="NOTIFY_TYPE")
	private int notifyType;
	
	/*
	 * 操作类型：
	 * 1.当notifyType=1时，该字段空置。
	 * 2.当notifyType=2时，0代表值为SQL，1代表新增，2代表修改，3代表删除。
	 * 3.当notifyType=3时，1代表执行任务，2代表停止任务，3代表删除任务。
	 */
	@JSONField(name="OPERATE_TYPE")
	private int operateType;
	
	
	/*
	 * 通知内容:
	 * 1.当notifyType=1时，该字段空置。
	 * 2.当notifyType=2, operateType=0时，该字段内容为要执行的SQL语句。
	 * 3.当notifyType=2, operateType≠0时，该字段内容为jsonArray，每个元素为变更实体对象的json字符串。
	 * 4.当notifyType=3时，该字段内容为taskID。
	 * 
	 */
	@JSONField(name="CONTENT")
	private String content;
	
	
	/*
	 * 扩展字段:
	 * 仅当notifyType=2, operateType=1,2,3时可用，该字段内容为实体对象的标识，如Topic, Tag等
	 * 
	 */
	@JSONField(name="EXTEND")
	private String extend;

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
}
