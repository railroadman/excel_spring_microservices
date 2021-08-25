package com.nayax.intern.microservices.document_manager.enums;

public enum LineTypeEnum {
    HEADER(0),
    TITLES(1),
    LINE(2);

    private int numVal;

    LineTypeEnum(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
