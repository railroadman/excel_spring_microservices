package com.nayax.intern.microservices.document_manager.reader;

import com.nayax.intern.microservices.document_manager.enums.ContentTypeEnum;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.helper.XLSHelper;
import org.apache.poi.util.DocumentFormatException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class XLSReader implements DocReader{
    @Override
    public List<DocLine> readFile(InputStream is) throws IOException, DocumentFormatException {
    try {
           List<DocLine> listDocLine = XLSHelper.readFiles(is);
           return listDocLine;
        } catch (Exception ex) {
            throw new DocumentFormatException(ex.getMessage());
       }

    }

    @Override
    public ContentTypeEnum getTypeReder() {
        return ContentTypeEnum.XLS;
    }
}
