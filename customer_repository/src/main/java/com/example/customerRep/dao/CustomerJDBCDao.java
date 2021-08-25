package com.example.customerRep.dao;

import com.example.customerRep.entity.CustomerEntity;
import com.example.customerRep.mapper.CustomerRowMapper;
import com.example.customerRep.tools.CustomerFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerJDBCDao implements CustomerDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedTemplate;


    @Override
    public List<Long> save(List<CustomerEntity> entities) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String INSERT_CUSTOMERS = "INSERT INTO  [Customer]  (code,isDeleted,firstName,lastName)\n" +
                "     VALUES (?,?,?,?)  ";
        List<Long> ids = new ArrayList<>();
        for (CustomerEntity entity : entities) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_CUSTOMERS, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getCode());
                ps.setInt(2, entity.getIsDeleted());
                ps.setString(3, entity.getFirstName());
                ps.setString(4, entity.getLastName());
                return ps;
            }, keyHolder);
            ids.add((long) keyHolder.getKey().intValue());
        }

        return ids;
    }

    @Override

    public List<Long> saveMerge(List<CustomerEntity> entities) {
/*        jdbcTemplate.execute("drop table IF EXISTS Customer_tmp;" +
                "               Select * into Customer_tmp  from  Customer where id=0 where id=0 union all select * from Customer where 1<>1");*/


        jdbcTemplate.execute("CREATE TABLE #Customer_tmp(" +
                "[id] [bigint] NULL," +
                "[code] [nvarchar](1500) NULL,\n" +
                "[isDeleted] [bigint] NULL,\n" +
                "\t[firstName] [nvarchar](1050) NULL,\n" +
                "\t[lastName] [nvarchar](1050) NULL," +
                "\t[customerId] [bigint] NOT NULL)");

        jdbcTemplate.execute("CREATE UNIQUE INDEX IX_Customer_tmp ON [#Customer_tmp] ([id]) WHERE [id] IS NOT NULL");


        final SimpleJdbcInsert statement = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("[#Customer_tmp]")
                .usingColumns("id", "code", "isDeleted", "firstName", "lastName","customerId");


        int[] results = statement.executeBatch(SqlParameterSourceUtils.createBatch(entities));
        System.out.println(Arrays.toString(results));
        List<Long> newIds = new ArrayList<>();
        String sql =
                "MERGE Customer AS TARGET\n" +
                        "USING #Customer_tmp AS SOURCE \n" +
                        "ON (TARGET.id = SOURCE.id and TARGET.customerId = SOURCE.customerId) \n" +
                        "WHEN MATCHED AND (SOURCE.firstName IS NULL OR TARGET.firstname <> SOURCE.firstName) OR (SOURCE.lastName IS NULL OR TARGET.lastName <> SOURCE.lastName) OR (SOURCE.code IS NULL OR TARGET.code <> SOURCE.code) \n" +
                        "THEN UPDATE SET TARGET.firstName = ISNULL(SOURCE.firstName,TARGET.firstName), TARGET.lastName = ISNULL(SOURCE.lastName,TARGET.lastName), TARGET.code = ISNULL(SOURCE.code,TARGET.code) \n" +
                        "WHEN NOT MATCHED BY TARGET \n" +
                        "THEN INSERT (code, isDeleted, firstName,lastName,customerId) VALUES (SOURCE.code, SOURCE.isDeleted, SOURCE.firstName,SOURCE.lastName,SOURCE.customerId)\n" +
                        "OUTPUT $action, \n" +
                        "INSERTED.id AS NewId, " +
                        "INSERTED.code AS SourceCode, \n" +
                        "INSERTED.firstName AS SourcefirstName, \n" +
                        "INSERTED.lastname AS SourcelastName; \n" +
                        " \n";


        List<Long> new_ids = jdbcTemplate.query(sql, (ResultSet rs) -> {
            List<Long> Ids = new ArrayList<>();
            String action = "";
            while (rs.next()) {
                action = rs.getString("$action");
                if (action.equals("INSERT")) {
                    Ids.add(rs.getLong("NewId"));
                }
            }
            return Ids;
        });

        jdbcTemplate.execute("DROP TABLE #Customer_tmp");
        return new_ids;
    }


    @Override
    public List<CustomerEntity> getCustomers(CustomerFilter filter) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        String SELECT_WITH_FILTERS = "select  * from Customer c" +
                "                 where 1=1  AND(:isDeleted IS NULL OR c.isDeleted=:isDeleted) AND (:code IS NULL OR c.code=:code)" +
                " AND (:firstname IS NULL OR c.firstname=:firstname) AND (:lastname IS NULL OR c.lastname=:lastname)";
        if (filter.getIds() != null) {
            SELECT_WITH_FILTERS += " AND (c.id in (:ids))";
            params.addValue("ids", filter.getIds());
        }
        params.addValue("firstname", filter.getFirstName() == null ? filter.getFirstName() : filter.getFirstName());
        params.addValue("lastname", filter.getLastName() == null ? filter.getLastName() : filter.getLastName());
        params.addValue("isDeleted", filter.getIsDeleted() == null ? filter.getIsDeleted() : filter.getIsDeleted());
        params.addValue("code", filter.getCode() == null ? filter.getCode() : filter.getCode());

        List<CustomerEntity> result = namedTemplate.query(SELECT_WITH_FILTERS, params, new CustomerRowMapper());
        return result;


    }
}
