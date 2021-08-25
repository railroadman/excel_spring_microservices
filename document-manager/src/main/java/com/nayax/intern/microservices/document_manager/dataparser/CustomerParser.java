package com.nayax.intern.microservices.document_manager.dataparser;

import com.nayax.intern.microservices.document_manager.dto.*;
import com.nayax.intern.microservices.document_manager.enums.DataTypeEnum;
import com.nayax.intern.microservices.document_manager.enums.StateTypeEnum;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.facade.CustomerFacade;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.mapper.DocLineToCustomerDtoMapper;
import com.nayax.intern.microservices.document_manager.webclient.ReceiverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerParser implements DataParser {

    @Autowired
    private ReceiverClient receiverClient;

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private DocLineToCustomerDtoMapper docLineToCustomerDtoMapper;

    private final DataTypeEnum CUSTOMERTYPE = DataTypeEnum.CUSTOMER;


    @Override
    public DocumentStatusDto parseData(List<DocLine> docLines, DocumentDto documentDto) {
        try {
            List<CustomerDto> customerDtos = new ArrayList<>();
            //List<CustomerDto> customerDtos = docLineToCustomerDtoMapper.docLinesToCustomerDtos(docLines,documentDto);
            for (DocLine docline : docLines){
                customerDtos.add(docLineToCustomerDtoMapper.docLineToCustomerDto(docline,documentDto));
            }

            ResponseDto responseDto = customerFacade.sendCustomerDtos(customerDtos);

            if (responseDto.getErrors().size() > 0) {
                List<ErrorDto> errors = responseDto.getErrors();
                String messages = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining("::"));
                return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, messages, null);
            } else {
                return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.SAVED, responseDto.getPayload().toString(), null);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
            return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, ex.getMessage(), null);
        } catch (Exception ex) {
            return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, ex.getMessage(), null);
        }
    }


    @Override
    public DataTypeEnum getTypeParser() {

        return CUSTOMERTYPE;
    }


}
