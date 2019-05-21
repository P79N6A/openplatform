package com.kd.openplatform.common.exception;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Title 错误码和错误信息定义
 * @author shinerio
 * @Description
 */
public class Code{

    //============================访问控制异常====================
    public static final int API_UNKNOWN_TOKEN = 2001; //token未知
    public static final String API_UNKNOWN_TOKEN_MSG = "token不合法";
    public static final int API_TOKEN_CANNOTFIND =2000;
    public static final String API_TOKEN_CANNOTFIND_MSG ="token未找到";
    public static final int API_PARAM_ERROE = 2002; //token未知
    public static final String API_PARAM_ERROE_MSG = "参数不合法";
    public static final int API_PATH_ERROR = 2003; //token未知
    public static final String API_PATH_ERROR_MSG = "api路径有误";
    public static final int API_EXPIRED_TOKEN = 2004; //token未知
    public static final String API_EXPIRED_TOKEN_MSG = "token已失效，请重新刷新token";
    public static final int API_OVER_FLOW = 3001;  // API调用流量控制异常
    public static final String API_OVER_FLOW_MSG = "访问频率过高";
    public static final int API_FUNDS_INSUFFICIENT = 3002; //API调用账户余额不足
    public static final String API_FUNDS_INSUFFICIENT_MSG = "API账户余额不足";
    public static final int API_TRY_INSUFFICIENT = 3303;  //API测试次数不足
    public static final String API_TRY_INSUFFICIENT_MSG = "API测试剩余次数不足";
    public static final int API_RESOURCE_AUTHOR_FAIL = 3004; //API调用资源鉴权失败
    public static final String API_RESOURCE_AUTHOR_FAIL_MSG = "API资源访问权限不足"; //API调用资源鉴权失败
    public static final int API_ACCOUNT_ERROR = 3005;
    public static final String API_ACCOUNT_ERROR_MSG = "API账户异常";
    public static final int API_UNORDERED_SERVICE = 3006;
    public static final String API_UNORDERED_SERVICE_MSG = "没有订购该接口";
    public static final int API_FLOW_INSUFFICIENT = 3007;
    public static final String API_FLOW_INSUFFICIENT_MSG = "流量超出限额";
    public static final int API_SERVICE_EXPIRED = 3008;
    public static final String API_SERVICE_EXPIRED_MSG = "服务订购已经到期";
    public static final int API_REMAIN_INSUFFICIENT = 3309;
    public static final String API_REMAIN_INSUFFICIENT_MSG = "API剩余次数不足";
    public static final int API_OBSOLETE_TOKEN = 3310;
    public static final String API_OBSOLETE_TOKEN_MSG = "未登录或token已过期";
    public static final int SYSTEM_EXCEPTION = 3311;
    public static final String SYSTEM_EXCEPTION_MSG = "系统异常";

    public static final int API_ACCOUNT_NOT_AUTHOR = 3312;
    public static final String API_ACCOUNT_NOT_AUTHOR_MSG = "当前账号没有被授权！";

    public static final int API_ACCOUNT_NOT_ACTIVE = 3313;
    public static final String API_ACCOUNT_NOT_ACTIVE_MSG = "当前授权账号尚未激活！";
}
