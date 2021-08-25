package com.nayax.intern.microservices.receiver.mapper;

import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventPartRowMapper implements RowMapper<EventEntity> {

    @Override
    public EventEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventEntity entity = new EventEntity();
        entity.setId(rs.getLong("idEvent"));
        entity.setDocumentId(rs.getLong("documentId"));
        return entity;
    }
}
