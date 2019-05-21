package com.kd.openplatform.exception;
/**
 * 自定义异常消息
 * @author Vivi
 * @date 2017年5月2日-上午10:58:50
 */
public class HlhtException extends Exception{
	private static final long serialVersionUID = 1L;
	private String exceptionMsg;//错误信息
	private int exceptionCode;//错误码
	
	public HlhtException(){
		super();
	}
	
	public HlhtException(String msg){
		super(msg);
		exceptionMsg=msg;
	}
	/**
	 * @param exceptionMsg 异常消息
	 * @param exceptionCode 异常码
	 */
	public HlhtException(String exceptionMsg, int exceptionCode) {
		super();
		this.exceptionMsg = exceptionMsg;
		this.exceptionCode = exceptionCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public int getExceptionCode() {
		return exceptionCode;
	}
	
	@Override
	public String toString() {
		return "HlhtException [exceptionMsg=" + exceptionMsg + ", exceptionCode=" + exceptionCode + "]";
	}
}
