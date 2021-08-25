package com.nayax.intern.microservices.receiver.repository;

import com.nayax.intern.microservices.receiver.dto.DocumentStatusDto;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.mapper.DocumentRowMapper;
import com.nayax.intern.microservices.receiver.mapper.EventPartRowMapper;
import com.nayax.intern.microservices.receiver.mapper.EventRowMapper;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class EventJDBCTemplate implements EventDao {

    @Autowired
    NamedParameterJdbcTemplate namedTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Long saveEvent(EventEntity eventEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        OffsetDateTime date = OffsetDateTime.now();
        System.out.println(eventEntity.getDocumentId());
        final String INSERT_EVENT = "INSERT INTO Event WITH(XLOCK,ROWLOCK) (stateId,customerId,documentId,updateData) VALUES(?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_EVENT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, eventEntity.getState());
            ps.setLong(2, eventEntity.getCustomerId());
            ps.setLong(3, eventEntity.getDocumentId());
            ps.setObject(4, date);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();

    }

    @Override
    public List<EventEntity> getEventsWithParams(EventFilter filter) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String SELECT_WITH_FILTERS = "select  e.id as idEvent,e.documentId as documentId" +
                "                FROM [Event] e where 1=1  AND(:isDeleted IS NULL OR e.isDeleted=:isDeleted) AND (:state IS NULL OR e.stateid=:state)";
        if (filter.getIds() != null) {
            SELECT_WITH_FILTERS += " AND (e.id in (:ids))";
            params.addValue("ids", filter.getIds());
        }

        params.addValue("isDeleted", filter.getIsDeleted() == null ? filter.getIsDeleted() : filter.getIsDeleted());
        params.addValue("state", filter.getState() == null ? filter.getState() : filter.getState().getNumVal());

        List<EventEntity> result = namedTemplate.query(SELECT_WITH_FILTERS, params, new EventPartRowMapper());
        return result;
    }


    @Override
    public Long deleteEvent(long id) {
        final String UPDATE_STATUS_DELETE = "UPDATE [dbo].[Event] SET [isDeleted] = 1  WHERE id =?";
        jdbcTemplate.update(UPDATE_STATUS_DELETE, id);
        return id;
    }

    @Override
    public int updateStatusEvent(List<Long> id, StateTypeEnum status) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        OffsetDateTime date = OffsetDateTime.now();
        final String UPDATE_STATUS = "UPDATE [dbo].[Event] SET [stateId]=:status," +
                "                       version = version + 1  WHERE id in (:id)";
        params.addValue("id", id);
        params.addValue("status", status.getNumVal());
        return namedTemplate.update(UPDATE_STATUS, params);


    }

    @Override
    public void saveDocumentStatus(List<DocumentStatusDto> documentStatusDtos) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String INSERT_DOCUMENT = "INSERT INTO [DocumentStatusDto] WITH(XLOCK,ROWLOCK) (documentId,eventId,errorMessage) VALUES(?,?,?)";
        for (DocumentStatusDto documentStatusDto : documentStatusDtos) {

            try {
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_DOCUMENT,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, documentStatusDto.getDocumentId());
                    ps.setLong(2, documentStatusDto.getEventId());
                    ps.setString(3, documentStatusDto.getMessages());
                    return ps;
                }, keyHolder);
            } catch ( Exception ex){
                System.out.println(ex.getMessage());
                System.out.println(ex.getStackTrace());

            }

            }

    }


}

