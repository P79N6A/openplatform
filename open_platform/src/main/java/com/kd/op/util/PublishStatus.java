package com.kd.op.util;

public enum PublishStatus {
    UN_PUBLISH(0),      //未发布
    SUCCESS_PUBLISH(1), //已发布
    FAILURE_PUBLISH(-1); //已作废
    private int value;
    PublishStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
