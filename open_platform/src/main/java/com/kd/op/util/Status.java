package com.kd.op.util;

public enum Status {
    WORKING_AREA(0),  //暂存
    UN_AUDIT(1),      //待审核
    SUCCESS_AUDIT(2), //审核通过
    FAILURE_AUDIT(3); //审核失败
    private int value;
    Status(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
