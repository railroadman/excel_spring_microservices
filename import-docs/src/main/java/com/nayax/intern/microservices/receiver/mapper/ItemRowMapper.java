package com.nayax.intern.microservices.receiver.mapper;



import com.nayax.intern.microservices.receiver.entity.ItemEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ItemRowMapper implements RowMapper<ItemEntity> {

    @Override
    public ItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(rs.getLong("id"));
        itemEntity.setCode(rs.getString("code"));
        itemEntity.setDescription(rs.getString("description"));
        itemEntity.setIsDeleted(rs.getInt("isDeleted"));
        itemEntity.setCustomerId(rs.getLong("customerId"));

        return itemEntity;
    }
}


