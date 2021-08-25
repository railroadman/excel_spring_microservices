package com.nayax.intern.microservices.receiver.mapper;

import com.nayax.intern.microservices.receiver.dto.DocumentDto;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {
    @Mapping(target="id",source = "entity.id")
    @Mapping(target = "docValue", source = "entity.docValue")
    @Mapping(target="dataType",source="entity.dataTypeEnum")
    @Mapping(target = "contentType", source="entity.contentTypeEnum")
    @Mapping(target = "customerId", source="entity.customerId")


    DocumentDto DocumentEntityToDocumentDto(Document entity);

    @Mapping(target="dataType", source="requestDto.type")
    @Mapping(target="docValue",source = "requestDto.document")
    @Mapping(target="contentTypeEnum",source = "requestDto.documentType")
    @Mapping(target = "dataTypeEnum", source = "requestDto.type")
    Document RequestDtoToDocumentEntity(RequestDto requestDto);

    List<DocumentDto> map(List<Document>  documents);


}