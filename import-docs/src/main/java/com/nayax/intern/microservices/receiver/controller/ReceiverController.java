package com.nayax.intern.microservices.receiver.controller;

import com.nayax.intern.microservices.receiver.dto.*;
import com.nayax.intern.microservices.receiver.mapper.EventMapper;
import com.nayax.intern.microservices.receiver.service.DocumentService;
import com.nayax.intern.microservices.receiver.service.EventService;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import com.nayax.intern.microservices.receiver.wrapper.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/receiver")
public class ReceiverController {
    private final Logger logger = LoggerFactory.getLogger(ReceiverController.class);
    @Autowired
    private EventService eventService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private EventMapper eventMapper;


    @GetMapping
    public String ifConnect() {
        return "Connect ok";
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<ResponseDto> deleteEvent(@PathVariable long id) {
        logger.info("Delete event By id:{}",id);
        eventService.deleteEvent(id);
        ResponseWrapper<Long> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(id), null), null);
    }


    @GetMapping("/events")
    public ResponseEntity<ResponseDto> getWithParametr(EventFilter filter) {
        logger.info("Get Events with filter STATE:{} ISDELETED:{} AND IDS:{}", filter.getState(), filter.getIsDeleted(), filter.getIds());
        List<EventDto> dtos = eventService.getWithParams(filter);
        ResponseWrapper<EventDto> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(dtos, null), null);

    }

    @PostMapping("/events")
    public ResponseEntity<ResponseDto> addEvent(@Valid @RequestBody RequestDto requestDto) {
        ResponseWrapper<Long> responseWrapper = new ResponseWrapper<>();
        Long eventId = eventService.addEvent(requestDto);
        logger.info("ADD EVENT:{} FROM RequestDTO: TYPE:{} FILE TYPE:{}", eventId,requestDto.getDocumentType(),requestDto.getType());
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(eventId), null), null);

    }

    @PostMapping("/events/status")
    public ResponseEntity<ResponseDto> updateEvent( @RequestBody  List<DocumentStatusDto> documentStatusDtos) {
        List<Long> ids = documentStatusDtos.stream().map(DocumentStatusDto::getEventId).collect(Collectors.toList());
        eventService.saveDocumentStatusDto(documentStatusDtos);
        logger.info("Updating EVENTIDS:{} ", ids);
        ResponseWrapper<Long> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(ids, null), null);

    }

    @GetMapping("/documents")
    public ResponseEntity<ResponseDto> sendDoc(@RequestParam("ids") List<Long> ids) {
        logger.info("Querying Documents by IDS:{}",ids);
        List<DocumentDto> documents = documentService.getDocumentByIds(ids);
        ResponseWrapper<DocumentDto> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(documents, null), null);
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file, @RequestPart("event") RequestDto requestDto) {
        if (!file.isEmpty()) {
            try {
                requestDto.setDocument(file.getBytes());
                this.eventService.addEvent(requestDto);
            } catch (IOException ex) {
                return new ResponseEntity<>("Error file is empty", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
