package com.example.customerRep.service;

import com.example.customerRep.dao.CustomerDao;
import com.example.customerRep.dto.CustomerDto;
import com.example.customerRep.mapper.CustomerMapper;
import com.example.customerRep.tools.CustomerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerService {
    @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerMapper customerMapper;
    public List<Long> save(List<CustomerDto>customerDtoList){
        return customerDao.save(this.customerMapper.map(customerDtoList));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Long> saveMerge(List<CustomerDto>customerDtoList){
      return   customerDao.saveMerge(this.customerMapper.map(customerDtoList));
    }

    public List<CustomerDto> getCustomers(CustomerFilter filter){
        return this.customerMapper.mapDto(customerDao.getCustomers(filter));
    }
}
