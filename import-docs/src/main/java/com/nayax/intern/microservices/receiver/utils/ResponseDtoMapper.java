package com.nayax.intern.microservices.receiver.utils;

import com.nayax.intern.microservices.receiver.dto.ResponseDto;
import com.nayax.intern.microservices.receiver.enums.ResponseStatusEnum;

import java.util.Arrays;
import java.util.List;

public class ResponseDtoMapper<T> {

    public ResponseDto toResponseDto(T type){

        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setPayload(Arrays.asList(type));
        responseDto.setStatus(ResponseStatusEnum.SUCCESS);
        return responseDto;

    }

    public ResponseDto toResponseDto(List<T> type){

        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setPayload(type);
        responseDto.setStatus(ResponseStatusEnum.SUCCESS);
        return responseDto;

    }
}
