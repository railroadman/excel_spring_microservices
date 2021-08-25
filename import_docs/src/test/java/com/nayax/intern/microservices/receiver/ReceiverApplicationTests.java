package com.nayax.intern.microservices.receiver;

import com.nayax.intern.microservices.receiver.dto.DocumentStatusDto;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.service.EventService;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReceiverApplicationTests {

    @Autowired
    private EventService eventService;

    @Test
    public void AddEvent() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        RequestDto requestDto = new RequestDto(125L, "CUSTOMER", b, "XLS");
        Long id = Long.valueOf(eventService.addEvent(requestDto));
        EventFilter filter = new EventFilter(0, StateTypeEnum.NEW, List.of(id));
        List<EventDto> dtos = eventService.getWithParams(filter);
        Assert.assertTrue(dtos.get(0).getId() == id);


    }

    @Test
    public void checkAddEventError() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        Throwable exception_docType = Assert.assertThrows(IllegalArgumentException.class, () -> eventService.addEvent(new RequestDto(125L, "CUSTOMER", b, "XLdS")));
        assertThat(exception_docType.getMessage().contains("enums.ContentTypeEnum"), CoreMatchers.is(true));
        Throwable exception_Customer = Assert.assertThrows(IllegalArgumentException.class, () -> eventService.addEvent(new RequestDto(125L, "CUSTDOMER", b, "XLS")));
        assertThat(exception_Customer.getMessage().contains("No enum"), CoreMatchers.is(true));
    }



    @Test
    public void createAndCheck() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        RequestDto requestDto = new RequestDto(12115L, "CUSTOMER", b, "XLS");
        System.out.println(requestDto);
        Long id = eventService.addEvent(requestDto);
        EventFilter filter = new EventFilter(0, StateTypeEnum.NEW);
        List<EventDto> dtos = eventService.getWithParams(filter);
        Assert.assertTrue(dtos.stream().count() > 0);

    }

    @Test
    public void checkSaveDocumentStatusDto() throws Exception {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        Long document_id;
        Long event_id;
        List<DocumentStatusDto> dtoStatuses = new ArrayList<>();
        List<Long> created_ids =  new ArrayList<>();
        StateTypeEnum status = StateTypeEnum.PARSED;

        Long newId = eventService.addEvent(new RequestDto(12223L, "CUSTOMER", b, "XLS"));
        List<EventDto> createdDtos = eventService.getWithParams(new EventFilter(0, StateTypeEnum.NEW,List.of(newId)));
        if (createdDtos.isEmpty()){
            throw new Exception(" Empty answer after creating event. Please check create method");
        }
        else {
            document_id = createdDtos.get(0).getDocumentId();
            event_id = createdDtos.get(0).getId();
            dtoStatuses.add(new DocumentStatusDto(document_id, status, "saved",event_id));
        }

        eventService.saveDocumentStatusDto(dtoStatuses);
        // check that status setted properly

            List<EventDto> checkedDtos = eventService.getWithParams(new EventFilter(0, status, List.of(event_id)));

        if(checkedDtos.size()==0){
            throw new Exception("Could not get saved eventId. Please check if the event Creating");
        }
        else{
            Assert.assertTrue(document_id.equals(checkedDtos.get(0).getDocumentId()));
            Assert.assertTrue(event_id.equals(checkedDtos.get(0).getId()));
        }

    }

    @Test
    void contextLoads() {
    }

}
