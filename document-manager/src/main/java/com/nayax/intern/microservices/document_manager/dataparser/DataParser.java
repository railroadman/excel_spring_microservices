package com.nayax.intern.microservices.document_manager.dataparser;

import com.nayax.intern.microservices.document_manager.dto.DocumentDto;

import com.nayax.intern.microservices.document_manager.dto.DocumentStatusDto;
import com.nayax.intern.microservices.document_manager.enums.DataTypeEnum;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface DataParser {

    DocumentStatusDto parseData(List<DocLine> doclines, DocumentDto documentDto) ;
    DataTypeEnum getTypeParser();

}
