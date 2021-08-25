package com.nayax.intern.microservices.document_manager.repository;

import com.nayax.intern.microservices.document_manager.dto.DocumentDto;

import java.util.List;

public interface ParserDao {

    public List<DocumentDto> getDocuments();

    public List<DocumentDto> getDocuments(String status);

    DocumentDto getDocument(int id);

}
