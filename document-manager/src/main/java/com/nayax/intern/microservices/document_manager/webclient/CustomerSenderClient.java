package com.nayax.intern.microservices.document_manager.webclient;

import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "customerSenderClient", url = "${nayax.microservices.customerRepository.url}")
public interface CustomerSenderClient  {

    @RequestMapping(method = RequestMethod.POST, value = "/customer/saveMerge")
    ResponseDto sendCustomerDtos(List<CustomerDto> customerDtos);







}
