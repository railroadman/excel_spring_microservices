package com.nayax.intern.microservices.document_manager.helper;

import com.nayax.intern.microservices.document_manager.enums.LineTypeEnum;
import java.util.List;

public class DocLine {

    private List<LineCell> lineCells;
    private LineTypeEnum lineType;

    public List<LineCell> getCells() {
        return lineCells;
    }

    public void setCells(List<LineCell> lineCells) {
        this.lineCells = lineCells;
    }

    public LineTypeEnum getLineType() {
        return lineType;
    }

    public void setLineType(LineTypeEnum lineType) {
        this.lineType = lineType;
    }

}
