package com.nayax.intern.microservices.receiver.service;

import com.nayax.intern.microservices.receiver.dto.DocumentDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.mapper.DocumentMapper;
import com.nayax.intern.microservices.receiver.repository.DocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentDao documentDao;
    @Autowired
    DocumentMapper documentMapper;
    public List<DocumentDto> getDocumentByIds(List<Long> id){

        return this.documentMapper.map(documentDao.getDocumentByIds(id));

    }
 @Transactional
    public Long saveDocumment(RequestDto requestDto){
        return  documentDao.save(this.documentMapper.RequestDtoToDocumentEntity(requestDto));
    }
}
