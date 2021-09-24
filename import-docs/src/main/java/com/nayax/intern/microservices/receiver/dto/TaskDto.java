package com.nayax.intern.microservices.receiver.dto;

public class TaskDto {
    private Long event_id;
    private Long document_id;
    public String dataType;
    public String contentType;
    private Long customerId;
    private Integer stateId;

    public TaskDto(Long event_id, Long document_id, String dataType, String contentType, Long customerId, Integer stateId) {
        this.event_id = event_id;
        this.document_id = document_id;
        this.dataType = dataType;
        this.contentType = contentType;
        this.customerId = customerId;
        this.stateId = stateId;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getDocument_id() {
        return document_id;
    }

    public void setDocument_id(Long document_id) {
        this.document_id = document_id;
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

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
}
