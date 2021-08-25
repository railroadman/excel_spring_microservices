package com.nayax.intern.microservices.receiver.service;
import com.nayax.intern.microservices.receiver.dto.DocumentStatusDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.mapper.DocumentMapper;
import com.nayax.intern.microservices.receiver.mapper.EventMapper;
import com.nayax.intern.microservices.receiver.repository.DocumentDao;
import com.nayax.intern.microservices.receiver.repository.EventDao;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
class EventServiceTest {

    @Autowired
    EventService eventService;


    @MockBean
    EventMapper eventMapper;
    @MockBean
    DocumentMapper documentMapper;

    @MockBean
    EventDao eventDao;
    @MockBean
    DocumentDao documentDao;


    @Test
    void addEvent() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        RequestDto requestDto = new RequestDto(123L, "ITEM", b, "XLS");
        eventService.addEvent(requestDto);
        Mockito.verify(eventDao, Mockito.times(1)).saveEvent(eventMapper.RequestDtotoEventEntity(requestDto));
        Mockito.verify(documentDao, Mockito.times(1)).save(documentMapper.RequestDtoToDocumentEntity(requestDto));


    }

    @Test
    void saveEvent() {
    }

    @Test
    void deleteEvent() {

        eventService.deleteEvent(10083L);
        Mockito.verify(eventDao, Mockito.times(1)).deleteEvent(10083L);


    }

    @Test
    void updateEvent() {
        Long id = 123L;
        StateTypeEnum state = StateTypeEnum.NEW;
        eventService.updateEvent(List.of(id), state);
        Mockito.verify(eventDao, Mockito.times(1)).updateStatusEvent(List.of(id), state);
    }

    @Test
    void getWithParams() {
        EventFilter filter = new EventFilter();
        filter.setIsDeleted(0);
        filter.setState(StateTypeEnum.NEW);
        eventService.getWithParams(filter);
        Mockito.verify(eventDao, Mockito.times(1)).getEventsWithParams(filter);
    }

    @Test
    void saveDocumentStatusDto() {
        DocumentStatusDto documentStatusDto = new DocumentStatusDto(23123L, StateTypeEnum.NEW, "all operations ok", 3123L);

        eventService.saveDocumentStatusDto(List.of(documentStatusDto));
        Mockito.verify(eventDao, Mockito.times(1)).saveDocumentStatus(List.of(documentStatusDto));


    }
}