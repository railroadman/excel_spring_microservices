package com.nayax.intern.microservices.document_manager.webclient;

import com.nayax.intern.microservices.document_manager.dto.DocumentDto;
import com.nayax.intern.microservices.document_manager.dto.DocumentStatusDto;
import com.nayax.intern.microservices.document_manager.dto.EventDto;
import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.enums.StateTypeEnum;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "receiverClient", url = "${nayax.microservices.receiver.url}")
public interface ReceiverClient  {

    @RequestMapping(method = RequestMethod.GET, value = "/receiver/events?state=NEW")
    ResponseDto<EventDto> getNewEvents();

    @RequestMapping(method = RequestMethod.GET, value = "/receiver/documents?ids={ids}")
    @CollectionFormat(feign.CollectionFormat.CSV)
    ResponseDto<DocumentDto> getDocuments(@PathVariable(value = "ids") String ids);

    @RequestMapping(method = RequestMethod.POST, value = "/receiver/events/status", consumes = "application/json")
    ResponseDto updateStatus(List<DocumentStatusDto> documentStatusDto); ///ids

}
