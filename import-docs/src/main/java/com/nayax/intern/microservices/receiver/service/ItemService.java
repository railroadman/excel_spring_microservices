package com.nayax.intern.microservices.receiver.service;


import com.nayax.intern.microservices.receiver.dto.ItemDto;
import com.nayax.intern.microservices.receiver.mapper.ItemMapper;
import com.nayax.intern.microservices.receiver.repository.ItemDao;
import com.nayax.intern.microservices.receiver.utils.ItemFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemDao itemDao;
    @Autowired
    ItemMapper itemMapper;

    @Transactional
    public List<Long> save (List<ItemDto> itemDtos){
       return itemDao.save(this.itemMapper.map(itemDtos));
    }


    //@Transactional(isolation = Isolation.SERIALIZABLE)
    @Transactional
    public List<Long> saveMerge(List<ItemDto> itemDtos){
        return itemDao.saveMerge(this.itemMapper.map(itemDtos));
    }

    public List<ItemDto> getItems(ItemFilter filter){
        return this.itemMapper.mapDto(itemDao.getItems(filter));
    }


    public void exportFromDbToExcel(ItemFilter filter) throws SQLException, IOException {

        List<ItemDto> itemDtos = this.itemMapper.mapDto(itemDao.getItems(filter));

        //Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Item Data");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("code");
        row.createCell(2).setCellValue("description");
        row.createCell(3).setCellValue("isDeleted");
        int index = 1;
        for (ItemDto item: itemDtos){
            row = sheet.createRow(index++);
            row.createCell(0).setCellValue(item.getId());
            row.createCell(1).setCellValue(item.getCode());
            row.createCell(2).setCellValue(item.getDescription());
            row.createCell(3).setCellValue(item.getIsDeleted());

        }

        FileOutputStream fos = new FileOutputStream("C:\\tmp\\tmp_excel.xls");
        workbook.write(fos);
        workbook.close();
        fos.close();




    }


}
