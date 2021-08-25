package com.nayax.intern.microservices.document_manager.dataparser;

import com.nayax.intern.microservices.document_manager.dataparser.DataParser;
import com.nayax.intern.microservices.document_manager.dto.*;
import com.nayax.intern.microservices.document_manager.enums.DataTypeEnum;
import com.nayax.intern.microservices.document_manager.enums.StateTypeEnum;
import com.nayax.intern.microservices.document_manager.exception.DocumentFormatException;
import com.nayax.intern.microservices.document_manager.facade.ItemFacade;
import com.nayax.intern.microservices.document_manager.helper.DocLine;
import com.nayax.intern.microservices.document_manager.mapper.DocLineToItemDtoMapper;
import com.nayax.intern.microservices.document_manager.service.ParserService;
import com.nayax.intern.microservices.document_manager.webclient.ReceiverClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemParser implements DataParser {

    Logger logger = LoggerFactory.getLogger(ItemParser.class.getName());

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private DocLineToItemDtoMapper docLineToItemDtoMapper;

    private final DataTypeEnum ITEMTYPE = DataTypeEnum.ITEM;



    @Override
    public DocumentStatusDto parseData(List<DocLine> doclines, DocumentDto documentDto) {

        try {
            List<ItemDto> itemDtos = new ArrayList<>();
            //List<ItemDto> itemDtos = docLineToItemDtoMapper.docLinesToItemDtos(doclines,documentDto);
            for (DocLine docline : doclines){
                itemDtos.add(docLineToItemDtoMapper.docLineToItemDto(docline,documentDto));
            }
            ResponseDto responseDto = itemFacade.sendDtos(itemDtos);

            if (responseDto.getErrors().size() > 0) {
                List<ErrorDto> errors = responseDto.getErrors();
                String messages = errors.stream().map(e->e.getMessage()).collect(Collectors.joining("::"));

                return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, messages, null);
            } else {
                return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.SAVED, responseDto.getPayload().toString(), null);
            }
        } catch (IllegalArgumentException ex) {
            logger.info(ex.getMessage());
            return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, ex.getMessage(), null);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return new DocumentStatusDto(documentDto.getId(), StateTypeEnum.ERROR, ex.getMessage(), null);
        }
    }

    @Override
    public DataTypeEnum getTypeParser() {
        return ITEMTYPE;
    }

}
