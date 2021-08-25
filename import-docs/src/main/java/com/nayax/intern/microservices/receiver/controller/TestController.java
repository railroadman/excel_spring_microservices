package com.nayax.intern.microservices.receiver.controller;

import com.nayax.intern.microservices.receiver.dto.DocumentDto;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.entity.Document;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import com.nayax.intern.microservices.receiver.mapper.EventMapper;
import com.nayax.intern.microservices.receiver.service.DocumentService;
import com.nayax.intern.microservices.receiver.service.EventService;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping("/test")
public class TestController {

    private EventService eventService;
    private DocumentService documentService;
    private EventMapper eventMapper;

    @Autowired
    public TestController(EventService eventService, DocumentService documentService,EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.documentService = documentService;

    }

    @GetMapping("/testMapper")
    public List<EventDto> testDtoMapper(){
        List<EventDto>  dtoEvents = new ArrayList<>();
        EventFilter filter = new EventFilter();
        List<EventDto> events = eventService.getWithParams(filter);

        return events;
    }

    /*@PostMapping("/testDtotoEntity")
    public void testEntitytoMapper(@RequestBody RequestDto dto){
        EventEntity entity = this.eventMapper.EventDtotoEventEntity(dto);
        System.out.println("here datatype");
        System.out.println(entity.getDocument().getDataType());
        System.out.println("here contenttype");
        System.out.println(entity.getDocument().getContentType());
       eventService.saveEvent(entity);
       return entity;
    }*/

    @GetMapping("/save_doc/{id}")
    public ResponseEntity<String> saveDocTOFile(@PathVariable long id) throws IOException {

        List<DocumentDto> docEntities = documentService.getDocumentByIds(List.of(id));

        try {
            Files.write(Path.of("c:\\tmp\\output.jpg"), docEntities.get(0).getDocValue());
        }
        catch (IOException exc){
            return new ResponseEntity<String>(exc.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("File saved",HttpStatus.OK);

    }

}
