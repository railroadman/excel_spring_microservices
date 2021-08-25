package com.example.itemRep.controller;

import com.example.itemRep.dto.ResponseDto;
import com.example.itemRep.dto.ItemDto;
import com.example.itemRep.service.ItemService;
import com.example.itemRep.tools.ItemFilter;
import com.example.itemRep.wrapper.ResponseWrapper;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequestMapping("/item")
@RestController
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    ItemService itemService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveItem(@RequestBody List<ItemDto> itemDtos) {
        List<Long> ids = itemService.save(itemDtos);
        ResponseWrapper<Long> wrapper = new ResponseWrapper<>();
        return wrapper.wrapToResponse(() -> wrapper.wrap(ids, null), null);


    }

    @PostMapping("/saveMerge")
    public ResponseEntity<ResponseDto> saveMerge(@RequestBody List<ItemDto> itemDtos) {
        //  for(ItemDto itemDto: itemDtos){
        //      System.out.println(itemDto.getId());
        //      if (itemDto.getId().equals(0))
        //         itemDto.setId(null);
        // }
        System.out.println(itemDtos.get(0).getId());
        ResponseWrapper<Long> wrapper = new ResponseWrapper<>();
        List<Long> ids = itemService.saveMerge(itemDtos);
        return wrapper.wrapToResponse(() -> wrapper.wrap(ids, null), null);


    }




    @GetMapping()
    public ResponseEntity<ResponseDto> getWithParametr(ItemFilter filter) {
        logger.info("Get Items with filter IDS:{} ISDELETED:{}  CODE:{}, Description like:{}", filter.getIds(), filter.getIsDeleted(),
                filter.getCode(), filter.getDescription());
        List<ItemDto> dtos = itemService.getItems(filter);
        ResponseWrapper<ItemDto> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(dtos, null), null);

    }


    @GetMapping("/export/{customerId}")
    public ResponseEntity<String> exportFromDbToExcel(@PathVariable long customerId,ItemFilter filter){
        String contentType = "application/octet-stream";
        boolean error = false;
        String error_msg = "";
        filter.setCustomerId(customerId);
        logger.info("Get Items with filter FOR CUSTOMERID:{} IDS:{} ISDELETED:{}  CODE:{}, Description like:{}", customerId, filter.getIds(), filter.getIsDeleted(),
                filter.getCode(), filter.getDescription());
        try {
            itemService.exportFromDbToExcel(filter);

        }
        catch (Exception ex){
            error_msg = ex.getMessage();
            error = true;
        }
        if (!error){
            try {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
                InputStream fileContent = new FileInputStream("C:\\tmp\\tmp_excel.xls");
                InputStreamResource inputStreamResource = new InputStreamResource(fileContent);
                httpHeaders.setContentLength(fileContent.available());
                return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
            }
            catch (Exception ex){
                return new ResponseEntity(error_msg,HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity(error_msg,HttpStatus.BAD_REQUEST);
        }

    }


}
