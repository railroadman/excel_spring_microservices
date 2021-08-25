package com.nayax.intern.microservices.receiver.enums;

public enum StateTypeEnum {
    ERROR(0),
    NEW(1),
    PARSED(2),
    SAVED(3);
    private int numVal;

    StateTypeEnum(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
