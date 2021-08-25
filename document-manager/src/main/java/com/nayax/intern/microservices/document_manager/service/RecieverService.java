package com.nayax.intern.microservices.document_manager.service;

import com.nayax.intern.microservices.document_manager.dto.*;
import com.nayax.intern.microservices.document_manager.enums.ResponseStatusEnum;
import com.nayax.intern.microservices.document_manager.dataparser.DataParser;
import com.nayax.intern.microservices.document_manager.webclient.ReceiverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class RecieverService {


    @Autowired
    private List<DataParser> dataParserList;

    public RecieverService() {
    }

    @Autowired
    private ReceiverClient receiverClient;

    @Qualifier
    public List<EventDto> getNewEvents() {
        ResponseDto<EventDto> responseDto = this.receiverClient.getNewEvents();
        List<EventDto> dtos = responseDto.getPayload();
        return dtos;
    }

    public List<DocumentDto> getDocuments(List<Long> ids) throws IOException {
        if (ids.size() == 0){
            return null;
        }
        String str_ids = ids.stream().map(Object::toString).collect(Collectors.joining(","));

        ResponseDto<DocumentDto> responseDto = this.receiverClient.getDocuments(str_ids);
        List<DocumentDto> dtos = responseDto.getPayload();
        return dtos;

    }

    public ResponseDto updateStatus(List<DocumentStatusDto> documentStatusDto){//  List<Long> event_ids,
        try {
            return receiverClient.updateStatus(documentStatusDto); //event_ids,
        }
        catch (Exception ex){
            List<Long> event_ids = new ArrayList<>();
            ex.getStackTrace();
            ResponseDto responseDto =  new ResponseDto();
            event_ids = documentStatusDto.stream().map(DocumentStatusDto::getEventId).collect(Collectors.toList());
            responseDto.setPayload(event_ids);
            responseDto.setErrors(List.of(new ErrorDto(ex.getMessage(),"333","PARSER")));
            responseDto.setStatus(ResponseStatusEnum.FAILED);
            return responseDto;
        }



    }

}
