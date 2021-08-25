package com.nayax.intern.microservices.document_manager.controller;

import com.nayax.intern.microservices.document_manager.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/parser")
public class ParserController {

    @Autowired
    private ParserService parserService;

    @GetMapping("/documents")
    public void parseDocuments() throws IOException {
        parserService.parseDocuments();
    }

}
