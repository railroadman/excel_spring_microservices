package com.nayax.intern.microservices.document_manager.service;

import com.nayax.intern.microservices.document_manager.dto.DocumentDto;
import com.nayax.intern.microservices.document_manager.dto.DocumentStatusDto;
import com.nayax.intern.microservices.document_manager.dto.EventDto;
import com.nayax.intern.microservices.document_manager.dataparser.DataParser;
import com.nayax.intern.microservices.document_manager.enums.ContentTypeEnum;
import com.nayax.intern.microservices.document_manager.enums.DataTypeEnum;
import com.nayax.intern.microservices.document_manager.enums.StateTypeEnum;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.reader.DocReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParserService {

    Logger logger = LoggerFactory.getLogger(ParserService.class.getName());

    @Autowired
    NamedParameterJdbcTemplate namedTemplate;
    @Autowired
    private RecieverService receiverService;
    @Autowired
    private List<DataParser> dataParserList;
    @Autowired
    private List<DocReader> docReaderList;

    public DataParser findDataParser(DataTypeEnum dataTypeEnum) {
        for (DataParser dataParser : dataParserList) {
            if (dataParser.getTypeParser() == dataTypeEnum) {
                return dataParser;
            }
        }
        return null;
    }

    public DocReader findDDocReader(ContentTypeEnum contentTypeEnum) {
        for (DocReader docReader : docReaderList) {
            if (docReader.getTypeReder() == contentTypeEnum) {
                return docReader;
            }
        }
        return null;
    }


//    @Scheduled(cron = "* */30 * * * *")

    @Scheduled(fixedRate = 10000)
    public void parseDocuments() throws IOException {
        System.out.println("ParseDocument Started");
        List<DocumentStatusDto> dtoStatuses = new ArrayList<>();
        List<EventDto> eventId = new ArrayList<>();
        List<EventDto> eventDtos = receiverService.getNewEvents();
        InputStream is = null;

        List<Long> docIds = eventDtos.stream().map(EventDto::getDocumentId).collect(Collectors.toList());
            logger.info("Get new events from MS-Receiver:{}",docIds.toString());
        if (docIds.size() ==0){
            return;
        }

        List<DocumentDto> docDtos = receiverService.getDocuments(docIds);
        logger.info("Get documents from Receiver:{}",docDtos.toString());
        for (DocumentDto documentDto : docDtos) {
            is = new ByteArrayInputStream(documentDto.getDocValue());
            DataParser concreteDataParser = findDataParser(DataTypeEnum.valueOf(documentDto.getDataType()));
            logger.info("Found DataParser:{} using document.DataType", concreteDataParser.getClass().getSimpleName());
            DocReader concreteDocReader = findDDocReader(ContentTypeEnum.valueOf(documentDto.getContentType()));
            logger.info("Found DocReader:{} using document.ContentType", concreteDocReader.getClass().getSimpleName());
            try {
                List<DocLine> docLines = concreteDocReader.readFile(is);
                DocumentStatusDto dtoStatus = concreteDataParser.parseData(docLines, documentDto);
                logger.info("Parse document:{} to MS-ItemRepo/CustomerRepo in a cycle", documentDto.getId());
                dtoStatuses.add(dtoStatus);
            } catch (Exception ex){
                DocumentStatusDto dtoStatus = new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, ex.getMessage(), null);
                dtoStatuses.add(dtoStatus);
            }
//                System.out.println("here after parsedocument");
//                System.out.println(dtoStatus.getEventId());

        }


        for (DocumentStatusDto documentStatusDto : dtoStatuses) {
            List<Long> docStatusIds = dtoStatuses.stream().map(DocumentStatusDto::getDocumentId).collect(Collectors.toList());
            //List<Long> eventIds = findEventInBase(docStatusIds);
            Optional<Long> foundEventId = eventDtos.stream().filter(el -> el.getDocumentId() == documentStatusDto.getDocumentId())
                    .map(EventDto::getId).findFirst();
            if (foundEventId.isPresent()) {
                documentStatusDto.setEventId(foundEventId.get());
            } else {
                continue;
            }

        }
        this.receiverService.updateStatus(dtoStatuses);
        logger.info("Update statuses of documents in Receiver MS DB");
    }

   /* public List<Long> findEventInBase(Long documentId) { // List<>
        MapSqlParameterSource params = new MapSqlParameterSource();
        String SELECT_WITH_FILTERS = "select  e.id as idEvent" +
                "                FROM [Event] e where 1=1";
        if (documentId != null) {
            SELECT_WITH_FILTERS += " AND (e.id in (:ids))";
            params.addValue("ids", documentId);
        }
        return namedTemplate.query(SELECT_WITH_FILTERS, params, new BeanPropertyRowMapper<>(Long.class));
    }*/

}


