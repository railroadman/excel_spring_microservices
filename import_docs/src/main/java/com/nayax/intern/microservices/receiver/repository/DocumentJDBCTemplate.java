package com.nayax.intern.microservices.receiver.repository;

import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.mapper.DocumentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DocumentJDBCTemplate implements DocumentDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate parameterJdbcTemplate;
    @Override
    public List<Document> getDocumentByIds(List<Long> id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        final String SQL = "  select d.id,d.docValue,dt.type AS DateType,ct.type AS ContetntType,e.customerId AS customerId from Document d\n" +
                "join DataType dt on dt.id = d.idDataType\n" +
                "join ContentType ct  on ct.id = d.idContentType\n" +
                "join Event e ON d.id=e.documentId "+
                "where d.id in (:id) ";
        params.addValue("id",id);
        List<Document> documents=parameterJdbcTemplate.query(SQL,params, new DocumentRowMapper());
        return documents;

    }

    @Override
    public Long save(Document document) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String INSERT_DOCUMENT = "INSERT INTO [Document] WITH(XLOCK,ROWLOCK) (idContentType,docValue,idDataType) VALUES(?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_DOCUMENT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, document.getContentType());
            ps.setBytes(2, document.getDocValue());
            ps.setInt(3, document.getDataType());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();

    }
}
