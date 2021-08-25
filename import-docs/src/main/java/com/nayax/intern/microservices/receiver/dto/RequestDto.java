package com.nayax.intern.microservices.receiver.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestDto {




    @NotNull
    private Long customerId;
    @NotNull
    @NotEmpty
    @NotBlank
    private String type;
    @NotNull
    private byte[] document;
    private Long documentId;
    @NotNull
    private String documentType;

    public RequestDto(Long customerId, String type, byte[] document, String documentType) {

        this.customerId = customerId;
        this.type = type;
        this.document = document;
        this.documentType = documentType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}


