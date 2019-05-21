package com.kd.openplatform.common.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * 定义公用常量及变量
 */
public class Constants {
    public final static String REDIS_TOKENUSER="TOKENUSER";
    public final static String  REDIS_SOURCE_CONTROL="SOURCECONTROL";
    public final static String  REDIS_PARAM_INFO="PARAMINFO";

    public static String TOKENNAME;
    public void setTOKENNAME(String TOKENNAME) {
        this.TOKENNAME = TOKENNAME;
    }

}
