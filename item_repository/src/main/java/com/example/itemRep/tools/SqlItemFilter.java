package com.example.itemRep.tools;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class SqlItemFilter {

    private String Sql;
    private MapSqlParameterSource params;

    public SqlItemFilter(String sql, MapSqlParameterSource params) {
        Sql = sql;
        this.params = params;
    }

    public String getSql() {
        return Sql;
    }

    public void setSql(String sql) {
        Sql = sql;
    }

    public MapSqlParameterSource getParams() {
        return params;
    }

    public void setParams(MapSqlParameterSource params) {
        this.params = params;
    }
}
