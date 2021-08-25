package com.nayax.intern.microservices.document_manager.reader;

import com.nayax.intern.microservices.document_manager.enums.ContentTypeEnum;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public interface DocReader {
     List<DocLine> readFile(InputStream is) throws IOException;
     ContentTypeEnum  getTypeReder();

}
