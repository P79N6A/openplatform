package com.kd.openplatform.common.exception;

/**
 * @Title 全局异常类
 * @author shinerio
 * @Description
 */
public class ControlException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private int code = 500;
    private String msg;

    public ControlException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ControlException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ControlException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ControlException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
