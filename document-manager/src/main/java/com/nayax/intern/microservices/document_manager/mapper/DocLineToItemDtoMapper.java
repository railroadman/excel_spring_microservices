package com.nayax.intern.microservices.document_manager.mapper;

import com.nayax.intern.microservices.document_manager.dto.DocumentDto;
import com.nayax.intern.microservices.document_manager.dto.ItemDto;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.helper.Helper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",uses = Helper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocLineToItemDtoMapper {



    @Mapping(target = "id", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"id\", Long.class))")
//    @Mapping(target = "name", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"name\", String.class))")
    @Mapping(target = "code", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"code\", String.class))")
    @Mapping(target = "description", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"description\", String.class))")
    @Mapping(target = "isDeleted", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"isDeleted\", Integer.class))")
    @Mapping(target="customerId", source = "documentDto.customerId")
//    @Mapping(target = "creationDate", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"creationDate\", java.util.Date.class))")
//    @Mapping(target = "price", expression = "java(com.nayax.intern.microservices.document_manager.helper.Helper.toField(docLine.getCells(), \"price\", Double.class))")

    ItemDto docLineToItemDto(DocLine docLine, DocumentDto documentDto) throws DocumentFormatException;

    //List<ItemDto> docLinesToItemDtos(List<DocLine> doclines);

}
