package com.nayax.intern.microservices.document_manager.dto;

import com.nayax.intern.microservices.document_manager.enums.ResponseStatusEnum;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class ResponseDto<T> {

    private List<T> payload;
    private ResponseStatusEnum status;
    private List<ErrorDto> errors;


    public List<T> getPayload() {
        return payload;
    }

    public void setPayload(List<T> payload) {
        this.payload = payload;
    }

    public ResponseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ResponseStatusEnum status) {
        this.status = status;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
