package com.nayax.intern.microservices.document_manager.dto;

public class EventDto {
    private Long id;
    private Long documentId;


    public EventDto(){}
    public EventDto(Long documentId, Long id, String state) {
        this.documentId = documentId;
        this.id = id;

    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
