package com.kd.op.api.enums;

public enum RegisterRouteEnum {

    platform("00","平台注册"),
    chelianwang("01","车联网授权"),
    SESS("02","智慧能源授权");

    private String code;
    private String name;

    RegisterRouteEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
