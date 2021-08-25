package com.nayax.intern.microservices.receiver.mapper;

import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import org.springframework.jdbc.core.RowMapper;
import org.w3c.dom.events.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<EventEntity> {

    @Override
    public EventEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(rs.getLong("idEvent"));
        eventEntity.setCustomerId(rs.getLong("customerId"));
        eventEntity.setState(rs.getString("StateEvent"));
        eventEntity.setDelete(rs.getBoolean("isDeleted"));
        eventEntity.setDocumentId(rs.getLong("idDocument"));
        return eventEntity;
    }
}
