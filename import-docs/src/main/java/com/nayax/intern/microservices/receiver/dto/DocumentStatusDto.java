package com.nayax.intern.microservices.receiver.dto;

import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;

import java.util.List;

public class DocumentStatusDto {

    private Long documentId;
    private StateTypeEnum status;
    private String message;
    private Long eventId;

    public DocumentStatusDto(Long documentId, StateTypeEnum status, String message, Long eventId) {
        this.documentId = documentId;
        this.status = status;
        this.message = message;
        if (eventId != null) {
            this.eventId = eventId;
        }
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public StateTypeEnum getStatus() {
        return status;
    }

    public void setStatus(StateTypeEnum status) {
        this.status = status;
    }

    public String getMessages() {
        return message;
    }

    public void setMessages(String message) {
        this.message = message;
    }
}
