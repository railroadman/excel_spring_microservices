package com.nayax.intern.microservices.document_manager.helper;

import com.nayax.intern.microservices.document_manager.enums.LineTypeEnum;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    /*public static void main(String[] args) throws IOException {
        String csvFileName = "C:\\Users\\ukr_temp\\OneDrive - Nayax LTD\\Customers.csv";
        InputStream inputStream = new FileInputStream(csvFileName);
        List<DocLine> docLineList = readFiles(inputStream);
        for (DocLine dcl : docLineList) {
            for (LineCell lineCell : dcl.getCells()) {
                System.out.println(lineCell.getCellKeyValue());
            }
        }
    }*/

    public static List<DocLine> readFiles(InputStream is) {
        List<DocLine> listDocLines = new ArrayList<>();
        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            CSVParser csvParser = CSVParser.parse(is, StandardCharsets.UTF_8, csvFormat);
            List<String> titlesOld = csvParser.getHeaderNames();
            List<String> titles = new ArrayList<>(titlesOld);
            titles.remove(0);
            {
                DocLine docLine = new DocLine();
                List<LineCell> lineCells = new ArrayList<>();
                docLine.setLineType(LineTypeEnum.TITLES);
                for (String title : titles) {
                    LineCell lineCell = new LineCell(new Pair<>(title, title));
                    lineCells.add(lineCell);
                }
                docLine.setCells(lineCells);
                listDocLines.add(docLine);
            }
            for (CSVRecord csvRecord : csvParser) {
                DocLine docLine = new DocLine();
                List<LineCell> lineCells = new ArrayList<>();
                docLine.setLineType(LineTypeEnum.LINE);
                for (int i = 0; i < titles.size(); i++) {
                    if (csvRecord.get(i + 1).equals("")) {
                        LineCell lineCell = new LineCell(new Pair<>(titles.get(i), null));
                        lineCells.add(lineCell);
                    } else {
                        LineCell lineCell = new LineCell(new Pair<>(titles.get(i), csvRecord.get(i + 1)));
                        lineCells.add(lineCell);
                    }
                }
                docLine.setCells(lineCells);
                listDocLines.add(docLine);
            }
            is.close();
            csvParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listDocLines.remove(0);
        return listDocLines;
    }
}




