package com.nayax.intern.microservices.document_manager.helper;

import org.apache.commons.math3.util.Pair;

public class LineCell {
    private Pair<String,? > cellKeyValue;

    public LineCell(Pair<String, Object> stringObjectPair) {
        cellKeyValue = stringObjectPair;
    }

    public Pair<String, ?> getCellKeyValue() {
        return cellKeyValue;
    }

    public void setCellKeyValue(Pair<String, ?> cellKeyValue) {
        this.cellKeyValue = cellKeyValue;
    }
}
