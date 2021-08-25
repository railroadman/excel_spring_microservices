package com.example.customerRep.controller;

import com.example.customerRep.dto.CustomerDto;
import com.example.customerRep.dto.ResponseDto;
import com.example.customerRep.entity.CustomerEntity;
import com.example.customerRep.service.CustomerService;
import com.example.customerRep.tools.CustomerFilter;
import com.example.customerRep.utils.MergeBuilder;
import com.example.customerRep.wrapper.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody List<CustomerDto> customerDtos) {
        ResponseWrapper<Long> wrapper = new ResponseWrapper<>();
        List<Long> ids = customerService.save(customerDtos);
        return wrapper.wrapToResponse(() -> wrapper.wrap(ids, null), null);


    }

    @PostMapping("/saveMerge")
    public ResponseEntity<ResponseDto> saveMerge(@RequestBody List<CustomerDto> customerDtos) {
        ResponseWrapper<Long> wrapper = new ResponseWrapper<>();
        List<Long> ids = customerService.saveMerge(customerDtos);
        return wrapper.wrapToResponse(() -> wrapper.wrap(ids, null), null);


    }

    @GetMapping()
    public ResponseEntity<ResponseDto> getWithParametr(CustomerFilter filter) {
        logger.info("Get Events with filter IDS:{} ISDELETED:{}  FIRSTNAME:{}, LASTNAME:{},  CODE:{}", filter.getIds(), filter.getIsDeleted(),
                filter.getFirstName(), filter.getLastName(), filter.getCode());
        List<CustomerDto> dtos = customerService.getCustomers(filter);
        ResponseWrapper<CustomerDto> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(dtos, null), null);

    }

  /*GetMapping  @GetMapping("/testbuilder")
    public ResponseEntity<String> testBuilder() {
        MergeBuilder builder = new MergeBuilder.Builder("test")
                .asSource("customer")
                .on("code", "code")
                .onCondition("description","=","description")
                .whenMatched("AND","code","<>","code")
                .whenMatched("OR","description","<>","description")
                .matchedThenUpdate()
                .matchedThenUpdateCondition(List.of("code","description","titles"))
                .whenNotMatched()
                .insertFields(List.of("code","description","titles"),List.of("code","description","titles"),"SOURCE.")
                .build();
        String out = builder.toString();
        return new ResponseEntity<String>(out, HttpStatus.OK);package
    }*/

}
