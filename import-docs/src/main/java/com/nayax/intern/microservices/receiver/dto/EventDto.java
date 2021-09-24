package com.nayax.intern.microservices.receiver.dto;



public class EventDto {

    private long id;
    private long documentId;
    private Integer stateId;


    public EventDto(){};
    public EventDto(long documentId, long id, String state) {
        this.documentId = documentId;
        this.id = id;

    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
