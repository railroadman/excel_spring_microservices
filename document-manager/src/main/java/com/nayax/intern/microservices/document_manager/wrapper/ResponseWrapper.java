package com.nayax.intern.microservices.document_manager.wrapper;


import com.nayax.intern.microservices.document_manager.dto.ErrorDto;
import com.nayax.intern.microservices.document_manager.dto.ResponseDto;
import com.nayax.intern.microservices.document_manager.enums.ResponseStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@Component
public class ResponseWrapper<T>{

    public ResponseDto<T> wrap(List<T> payload, List<ErrorDto> errors){
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setPayload(emptyIfNull(payload));
        responseDto.setErrors(emptyIfNull(errors));
        responseDto.setStatus(ResponseStatusEnum.SUCCESS);

        if(!emptyIfNull(errors).isEmpty()){
            responseDto.setStatus(ResponseStatusEnum.FAILED);
        }

        return responseDto;

    }

    public ResponseEntity<ResponseDto> wrapToResponse(Supplier<ResponseDto> supplier, HttpStatus status){
        Optional<HttpStatus> httpStatus = Optional.ofNullable(status);
        return  new ResponseEntity<ResponseDto>(supplier.get(),httpStatus.orElse(HttpStatus.OK));
    }
}
