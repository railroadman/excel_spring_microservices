package com.nayax.intern.microservices.receiver.enums;

public enum ResponseStatusEnum {
    FAILED(500),
    SUCCESS(200);

    private int numVal;

    ResponseStatusEnum(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

}
