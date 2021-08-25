package com.example.customerRep.mapper;


import com.example.customerRep.dto.CustomerDto;
import com.example.customerRep.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public  interface CustomerMapper {

    @Mapping(target = "code", expression = "java(customerDto.getCode().isEmpty() ? null : customerDto.getCode())")
    @Mapping(target = "firstName", expression = "java(customerDto.getFirstName().isEmpty() ? null : customerDto.getFirstName())")
    @Mapping(target = "lastName", expression = "java(customerDto.getLastName().isEmpty() ? null : customerDto.getLastName())")
    CustomerEntity customerDtoToCustomerEntity(CustomerDto customerDto);
    List<CustomerEntity> map (List<CustomerDto> customerDtos);

    CustomerDto customerEntityToDto(CustomerEntity entity);
    List<CustomerDto> mapDto (List<CustomerEntity> entities);
}
