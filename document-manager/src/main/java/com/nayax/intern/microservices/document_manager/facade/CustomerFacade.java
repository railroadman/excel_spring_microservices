package com.nayax.intern.microservices.document_manager.facade;

import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.dto.CustomerDto;
import com.nayax.intern.microservices.document_manager.webclient.CustomerSenderClient;
import com.nayax.intern.microservices.document_manager.webclient.ReceiverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFacade {

    @Autowired
    private CustomerSenderClient customerSenderClient;
    @Autowired
    private ReceiverClient receiverClient;


    public ResponseDto sendCustomerDtos(List<CustomerDto> customerDtos){
        return customerSenderClient.sendCustomerDtos(customerDtos);
    }
}
