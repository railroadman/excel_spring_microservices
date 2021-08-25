package com.example.customerRep.mapper;

import com.example.customerRep.entity.CustomerEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerRowMapper implements RowMapper<CustomerEntity> {

    @Override
    public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(rs.getLong("id"));
        customerEntity.setCode(rs.getString("code"));
        customerEntity.setFirstName(rs.getString("firstname"));
        customerEntity.setLastName(rs.getString("lastname"));
        customerEntity.setIsDeleted(rs.getInt("isDeleted"));
        customerEntity.setCustomerId(rs.getLong("customerId"));

        return customerEntity;
    }
}

