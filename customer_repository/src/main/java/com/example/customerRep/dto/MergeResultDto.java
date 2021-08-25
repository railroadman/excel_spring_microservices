package com.example.customerRep.dto;

import java.util.List;

public class MergeResultDto {

    private List<Long> insertRow;
    private List<Long> updateRow;
    private String log;

    public MergeResultDto(List<Long> insertRow, List<Long> updateRoc, String log) {
        this.insertRow = insertRow;
        this.updateRow = updateRoc;
        this.log = log;
    }

    public List<Long> getInsertRow() {
        return insertRow;
    }

    public void setInsertRow(List<Long> insertRow) {
        this.insertRow = insertRow;
    }

    public List<Long> getUpdateRow() {
        return updateRow;
    }

    public void setUpdateRow(List<Long> updateRow) {
        this.updateRow = updateRow;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
