package com.nayax.intern.microservices.receiver.controller;

import com.nayax.intern.microservices.receiver.dto.*;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.service.DocumentService;
import com.nayax.intern.microservices.receiver.service.EventService;
import com.nayax.intern.microservices.receiver.service.ItemService;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import com.nayax.intern.microservices.receiver.utils.ItemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
public class MvcController {

    @Autowired
    EventService eventService;
    @Autowired
    DocumentService documentService;
    @Autowired
    ItemService itemService;

    @PostMapping(value = "/file",consumes = {"multipart/form-data"})
    public String upload(@RequestPart("file") MultipartFile file, @RequestParam Long customerId,
                                    @RequestParam String type,@RequestParam String documentType,@RequestParam Long eventId) throws IOException {

        RequestDto req = new RequestDto();
        if (file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                || file.getContentType().equals("application/vnd.ms-excel")){
            System.out.println("OK");

            req.setCustomerId(customerId);
            req.setType(type);
            req.setDocument(file.getBytes());
            req.setDocumentType(documentType);
            Long newEventId = eventService.addEvent(req);
            System.out.println(newEventId);

        }

        System.out.println(customerId);

        return "form_add";
    }

    @GetMapping("/file")
    public String showForm(){

         return "form_add.html";
    }

    @GetMapping("/")
    public String main(Model model){
        List<TaskDto> tasks = new ArrayList<>();
        List<EventDto> events = eventService.getWithParams(new EventFilter());
        if (events.size()>0) {
            List<Long> ids = events.stream().map(e -> e.getDocumentId()).collect(Collectors.toList());
            List<DocumentDto> documents = documentService.getDocumentByIds(ids);

            for (EventDto e : events) {
                for (DocumentDto d : documents) {
                    if (e.getDocumentId() == d.getId())
                        tasks.add(new TaskDto(e.getId(), d.getId(), d.getDataType(), d.getContentType(), d.getCustomerId(),e.getStateId()));
                }
            }

            model.addAttribute("tasks",tasks);
        }

        return "index";
    }

    @GetMapping("/items")
    public String returnItem(Model model){
        List<ItemDto> items = itemService.getItems(new ItemFilter());
        model.addAttribute("items",items);
        return "Items";
    }



}
