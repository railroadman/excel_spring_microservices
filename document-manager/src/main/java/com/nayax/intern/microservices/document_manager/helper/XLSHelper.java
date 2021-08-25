package com.nayax.intern.microservices.document_manager.helper;

import com.nayax.intern.microservices.document_manager.enums.LineTypeEnum;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSHelper {

    private static InputStream EXCELSTREAM;

    public static boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        DataFormatter dataFormatter = new DataFormatter();
        if(row != null) {
            for(Cell cell: row) {
                if(dataFormatter.formatCellValue(cell).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }


    public static List<DocLine> readFiles(InputStream is) throws IOException {

        List<DocLine> listDocLines = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        List<String> titles = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Short totalColumns = 0;
        {
            while (rowIterator.hasNext()) {   //
                DocLine docLine = new DocLine();
                List<LineCell> lineCells = new ArrayList<>();
                Row row = rowIterator.next();
                if (isRowEmpty(row)) break;
                int countCells = 0;

                if (row.getCell(row.getFirstCellNum()).getStringCellValue().equals("titles")) {
                    docLine.setLineType(LineTypeEnum.TITLES);
                    totalColumns = row.getLastCellNum();


                    for (int cn = 0; cn < totalColumns; cn++) {

                        Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        if (countCells != 0) {
                            titles.add(cell.getStringCellValue());
                            LineCell lineCell = new LineCell(new Pair<>(cell.getStringCellValue(), cell.getStringCellValue()));
                            lineCells.add(lineCell);
                        }
                        countCells++;
                    }
                    docLine.setCells(lineCells);
                } else {
                    int count = 0;
                    docLine.setLineType(LineTypeEnum.LINE);


                    for (int cn = 0; cn < totalColumns; cn++) {
                        Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        LineCell lineCell = null;

                        if (countCells != 0) {

                            CellType cellType = cell.getCellType();
                            switch (cellType) {
                                case NUMERIC:
                                    lineCell = new LineCell(new Pair<>(titles.get(count), cell.getNumericCellValue()));
                                    break;
                                case STRING:
                                    lineCell = new LineCell(new Pair<>(titles.get(count), cell.getStringCellValue()));
                                    break;
                                case BOOLEAN:
                                    lineCell = new LineCell(new Pair<>(titles.get(count), cell.getBooleanCellValue()));
                                    break;
                                case FORMULA:
                                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                    switch (evaluator.evaluateFormulaCell(cell)) {
                                        case BOOLEAN:
                                            lineCell = new LineCell(new Pair<>(titles.get(count), cell.getBooleanCellValue()));
                                            break;
                                        case NUMERIC:
                                            lineCell = new LineCell(new Pair<>(titles.get(count), cell.getNumericCellValue()));
                                            break;
                                        case STRING:
                                            lineCell = new LineCell(new Pair<>(titles.get(count), cell.getStringCellValue()));
                                            break;
                                    }
                                    break;
                                default:
                                    lineCell = new LineCell(new Pair<>(titles.get(count), null));
                                    break;
                            }
                            count++;
                            lineCells.add(lineCell);
                        }
                        countCells++;
                    }
                    docLine.setCells(lineCells);
                    System.out.println("");
                }
                listDocLines.add(docLine);
                System.out.println("");
            }
        }
        listDocLines.remove(0);
        return listDocLines;
    }
}




