package com.example.customerRep.dao;

import com.example.customerRep.entity.CustomerEntity;
import com.example.customerRep.tools.CustomerFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerDao {
    public List<Long> save(List<CustomerEntity> entities);

    public List<Long> saveMerge(List<CustomerEntity> entities);
    List getCustomers(CustomerFilter filter);
}
