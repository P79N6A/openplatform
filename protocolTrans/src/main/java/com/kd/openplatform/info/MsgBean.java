package com.kd.openplatform.info;

public class MsgBean {

	//互联互通返回参数格式
	private Integer Ret;
	private String Msg;
	private Object Data;


	public Integer getRet() {
		return Ret;
	}

	public void setRet(Integer ret) {
		Ret = ret;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}
}
