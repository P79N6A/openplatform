package com.kd.openplatform.common.utils;

import java.util.UUID;

/**
 * @Title uuid生成工具
 * @author shinerio
 * @Description
 */
public class  Utils {
    public static String getUUID32(){
       return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static String getUUID36(){
        return UUID.randomUUID().toString().trim();
    }
}
