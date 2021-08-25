package com.nayax.intern.microservices.document_manager.facade;

import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.dto.ItemDto;
import com.nayax.intern.microservices.document_manager.webclient.ItemSenderClient;
import com.nayax.intern.microservices.document_manager.webclient.ReceiverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemFacade {

    @Autowired
    private ItemSenderClient itemSenderClient;

    @Autowired
    private ReceiverClient receiverClient;


    public ResponseDto sendDtos(List<ItemDto> itemDtos){
        return itemSenderClient.sendItemDtos(itemDtos);
    }



}
