package com.kd.openplatform.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 存放项目全局变量及常量
 */

public class Constants {
    public final static String REDIS_TOKENUSER="TOKENUSER";
    public final static String REDIS_USERTOKEN="USERTOKEN";
    public final static String REDIS_WSINFO="WSINFO";
    public final static String REDIS_RECEIVEINFO="RECEIVEINFO";
    public final static String REDIS_TOPIC="TOPICLIST";

    public static String TOKEN_95598 = "";
    public static String TOKEN_TJ = "";


    public static String tokenName;
    public static String accessUrl;
    public static String accessUserName;
    public static String accessKey;


    public static String url95598;
    public static String appCode95598;
    public static String appSecret95598;
    public static String tjAccount;
    public static String tjPasswd;
    public static String tjTokenUrl;
    public static String tjSsrc;
    public static String tjSkey;

    public void setTjAccount(String tjAccount) {
        this.tjAccount = tjAccount;
    }

    public void setTjPasswd(String tjPasswd) {
        this.tjPasswd = tjPasswd;
    }

    public void setTjTokenUrl(String tjTokenUrl) {
        this.tjTokenUrl = tjTokenUrl;
    }

    public void setTjSsrc(String tjSsrc) {
        this.tjSsrc = tjSsrc;
    }

    public void setTjSkey(String tjSkey) {
        this.tjSkey = tjSkey;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public void setAccessUserName(String accessUserName) {
        this.accessUserName = accessUserName;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }


    public void setUrl95598(String url95598) {
        this.url95598 = url95598;
    }

    public void setAppCode95598(String appCode95598) {
        this.appCode95598 = appCode95598;
    }

    public void setAppSecret95598(String appSecret95598) {
        this.appSecret95598 = appSecret95598;
    }
}
