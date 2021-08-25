package com.nayax.intern.microservices.receiver.service;

import com.nayax.intern.microservices.receiver.dto.DocumentStatusDto;
import com.nayax.intern.microservices.receiver.dto.EventDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.dto.ResponseDto;
import com.nayax.intern.microservices.receiver.entity.EventEntity;
import com.nayax.intern.microservices.receiver.enums.StateTypeEnum;
import com.nayax.intern.microservices.receiver.mapper.EventMapper;
import com.nayax.intern.microservices.receiver.repository.EventDao;
import com.nayax.intern.microservices.receiver.utils.EventFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    Logger logger = LoggerFactory.getLogger(EventService.class);
    @Autowired
    private EventDao eventDao;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private DocumentService documentService;


    @Transactional
    public Long addEvent(RequestDto requestDto) {
        logger.info("save event to DB");
        System.out.println(requestDto);
        requestDto.setDocumentId(documentService.saveDocumment(requestDto));
        return eventDao.saveEvent(this.eventMapper.RequestDtotoEventEntity(requestDto));
    }


    public boolean deleteEvent(long id) {
        eventDao.deleteEvent(id);
        return  true;
    }

    public int updateEvent(List<Long> id, StateTypeEnum state) {
        return eventDao.updateStatusEvent(id, state);
    }

    public List<EventDto> getWithParams(EventFilter filter) {
        List<EventEntity> entities = this.eventDao.getEventsWithParams(filter);
        return this.eventMapper.map(entities);
    }

    public void saveDocumentStatusDto(List<DocumentStatusDto> documentStatusDtos) {
        List<Long> ids = new ArrayList<>();
        for (DocumentStatusDto dtoStatus : documentStatusDtos) {
            int count = updateEvent(List.of(dtoStatus.getEventId()), dtoStatus.getStatus());

            logger.info("SAVE DOCUMENT STATUS IDS:{} STATUS:{}", dtoStatus.getEventId(), dtoStatus.getStatus());

        }
        eventDao.saveDocumentStatus(documentStatusDtos);


    }

}