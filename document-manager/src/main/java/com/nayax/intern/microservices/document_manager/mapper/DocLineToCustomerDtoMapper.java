package com.nayax.intern.microservices.document_manager.mapper;


import com.nayax.intern.microservices.document_manager.dto.CustomerDto;
import com.nayax.intern.microservices.document_manager.dto.DocumentDto;
import com.nayax.intern.microservices.document_manager.dto.ItemDto;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.helper.Helper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = Helper.class)
public interface DocLineToCustomerDtoMapper {

    @Mapping(target = "id", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"id\", Long.class))")
    @Mapping(target = "firstName", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"firstName\", String.class))")
    @Mapping(target = "lastName", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"lastName\", String.class))")
    @Mapping(target = "address", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"address\", String.class))")
    @Mapping(target="customerId", source = "documentDto.customerId")
    CustomerDto docLineToCustomerDto(DocLine docLine,DocumentDto documentDto) throws DocumentFormatException;

    //List<CustomerDto> docLinesToCustomerDtos(List<DocLine> doclines);

}
