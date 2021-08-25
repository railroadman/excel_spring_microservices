package com.nayax.intern.microservices.document_manager.helper;

import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Helper {

    public static <T> T toField(List<LineCell> lineCells, String fieldName, Class<T> tClass) throws DocumentFormatException {

        if (Long.class.equals(tClass)) {
            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    try {
                        if (lineCell.getCellKeyValue().getValue()==null){
                            return null;
                        }
                        return tClass.cast((Long) Math.round((Double) lineCell.getCellKeyValue().getValue()));
                    } catch (Exception ex){
                        return tClass.cast(Long.valueOf((String) lineCell.getCellKeyValue().getValue()));
                    }
                }
            }
        } else if (java.util.Date.class.equals(tClass)) {
            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    try {
                        TimeZone tz = TimeZone.getTimeZone("GMT+0:00");
                        return tClass.cast(DateUtil.getJavaDate((double) lineCell.getCellKeyValue().getValue(), tz));
                    } catch (Exception ex){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        return tClass.cast(LocalDate.parse((String) lineCell.getCellKeyValue().getValue(), formatter));
                    }
                }
            }
        } else if (Integer.class.equals(tClass)) {
            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    if (lineCell.getCellKeyValue().getValue().equals(true) || lineCell.getCellKeyValue().getValue().equals("TRUE")) {
                        return tClass.cast(1);
                    } else if (lineCell.getCellKeyValue().getValue().equals(false) || lineCell.getCellKeyValue().getValue().equals("FALSE")){
                        return tClass.cast(0);
                    } else{
                        return tClass.cast((Integer) lineCell.getCellKeyValue().getValue());
                    }
                }
            }
        } else if (Double.class.equals(tClass)) {
            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    try {
                        return tClass.cast(lineCell.getCellKeyValue().getValue());
                    } catch (Exception exception){
                        return tClass.cast(Double.valueOf((String) lineCell.getCellKeyValue().getValue()));
                    }
                }
            }
        } else if(String.class.equals(tClass)){

            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    try {
                        return tClass.cast(lineCell.getCellKeyValue().getValue());
                    } catch (Exception exception){
                        NumberFormat nf = DecimalFormat.getInstance();
                        nf.setMaximumFractionDigits(0);
                        nf.setGroupingUsed(false);
                        return tClass.cast(nf.format(lineCell.getCellKeyValue().getValue()));
                    }
                }
            }

        }

        else {
            for (LineCell lineCell : lineCells) {
                if (lineCell.getCellKeyValue().getKey().equals(fieldName)) {
                    return tClass.cast(lineCell.getCellKeyValue().getValue());
                }
            }
        }
        throw new DocumentFormatException("Document format isn't acceptable");
//        return null;
    }

}

