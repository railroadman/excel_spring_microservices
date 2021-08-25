package com.nayax.intern.microservices.document_manager.enums;

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


