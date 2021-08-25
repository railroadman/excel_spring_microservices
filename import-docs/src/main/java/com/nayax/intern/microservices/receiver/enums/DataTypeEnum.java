package com.nayax.intern.microservices.receiver.enums;

public enum DataTypeEnum {
    CUSTOMER(1),
    ITEM(2);

    private int numVal;

    DataTypeEnum(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }


}


