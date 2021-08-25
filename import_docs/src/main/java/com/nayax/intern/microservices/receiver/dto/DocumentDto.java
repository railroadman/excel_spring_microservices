package com.nayax.intern.microservices.receiver.dto;

import com.nayax.intern.microservices.receiver.enums.ContentTypeEnum;
import com.nayax.intern.microservices.receiver.enums.DataTypeEnum;

public class DocumentDto {
    private long id;
    private byte[] docValue;
    public String dataType;
    public String contentType;
    private Long customerId;


    public DocumentDto(long id, byte[] docValue, String dataType, String contentType,Long customerId) {
        this.id = id;
        this.docValue = docValue;
        this.dataType = dataType;
        this.contentType = contentType;
        this.customerId = customerId;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getDocValue() {
        return docValue;
    }

    public void setDocValue(byte[] docValue) {
        this.docValue = docValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

