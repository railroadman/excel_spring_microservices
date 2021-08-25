package com.nayax.intern.microservices.document_manager.webclient;

import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "itemSenderClient", url = "${nayax.microservices.itemRepository.url}")
public interface ItemSenderClient  {

    @RequestMapping(method = RequestMethod.POST, value = "/item/saveMerge")
    ResponseDto sendItemDtos(List<ItemDto> itemDtos);

}

