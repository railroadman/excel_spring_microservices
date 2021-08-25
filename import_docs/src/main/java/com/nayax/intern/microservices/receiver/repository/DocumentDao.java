package com.nayax.intern.microservices.receiver.repository;

import com.nayax.intern.microservices.receiver.entity.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentDao {
    public List<Document> getDocumentByIds(List<Long> id);
    public Long save(Document document);
}
