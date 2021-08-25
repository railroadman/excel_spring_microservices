package com.nayax.intern.microservices.receiver.mapper;

import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
               @Mapping(target="documentId",source = "entity.documentId")
               @Mapping(target="id",source="entity.id")

               EventDto EventEntitytoEventDto(EventEntity entity);

               @Mapping(target = "customerId", source = "requestDto.customerId")
               @Mapping(target = "id", ignore = true)

    EventEntity RequestDtotoEventEntity(RequestDto requestDto);

    List<EventDto> map(List<EventEntity>  eventEntities);




}
