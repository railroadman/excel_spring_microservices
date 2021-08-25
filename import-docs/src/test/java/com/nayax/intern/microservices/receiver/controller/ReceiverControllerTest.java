package com.nayax.intern.microservices.receiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.dto.ResponseDto;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import com.nayax.intern.microservices.receiver.enums.ResponseStatusEnum;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.mapper.DocumentMapper;
import com.nayax.intern.microservices.receiver.mapper.EventMapper;
import com.nayax.intern.microservices.receiver.repository.EventDao;
import com.nayax.intern.microservices.receiver.service.DocumentService;
import com.nayax.intern.microservices.receiver.service.EventService;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ReceiverController.class)
class ReceiverControllerTest {
    private final Logger logger = LoggerFactory.getLogger(ReceiverControllerTest.class);
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private EventService eventService;
    @MockBean
    private DocumentService documentService;
    @MockBean
    private EventMapper eventMapper;
    @MockBean
    private EventDao eventDao;


    @Test
    @DisplayName("Delete event via mockMvc")
    void deleteEvent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/receiver/events/{id}", 10083L)
                        .contentType("application.json"))
                .andExpect(status().isOk()).andReturn();
        verify(eventService, times(1)).deleteEvent(10083L);


    }

    @Test
    @DisplayName("select events using filters")
    void getWithParametr() throws Exception {
        EventFilter filter = new EventFilter(0, StateTypeEnum.NEW);
        EventDto eventDto = new EventDto(1231L, 21212L, "NEW");
        when(eventService.getWithParams(filter)).thenReturn(List.of(eventDto));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/receiver/events").param("isDeleted", "0")
                .param("state", "NEW")).andExpect(status().isOk()).andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
        verify(eventService, times(1)).getWithParams(refEq(filter));

    }

    @Test
    void addEvent() throws Exception {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        RequestDto requestDto = new RequestDto(123L, "CUSTOMER", b, "XLS");
        requestDto.setDocumentId(3232L);
        Long eventId = 121113L;

        when(eventService.addEvent(requestDto)).thenReturn(eventId);
        //when(eventDao.saveEvent(this.eventMapper.RequestDtotoEventEntity(requestDto))).thenReturn(eventId);
        String requestDtoJson = new ObjectMapper().writeValueAsString(requestDto);
        logger.info("RequestDto Json:{} ", requestDtoJson);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/receiver/events").contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJson)).andExpect(status().isOk()).andReturn();
        logger.info("MvcResult response:{}", mvcResult.getResponse().getContentAsString());

        verify(eventService, times(1)).addEvent(refEq(requestDto));
    }

    @Test
    void updateEvent() {
    }

    @Test
    void sendDoc() {
    }

    @Test
    void upload() {
    }


}