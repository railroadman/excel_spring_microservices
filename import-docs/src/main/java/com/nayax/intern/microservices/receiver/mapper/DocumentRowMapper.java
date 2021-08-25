package com.nayax.intern.microservices.receiver.mapper;



import com.nayax.intern.microservices.receiver.entity.Document;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document>{

    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException{
        Document document = new Document(rs.getLong("id"),rs.getBytes("docValue"),
                rs.getString("DateType"),rs.getString("ContetntType"),rs.getLong("customerId"));
        return document;
    }

}

