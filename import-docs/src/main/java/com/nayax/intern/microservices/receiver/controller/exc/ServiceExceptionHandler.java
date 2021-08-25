package com.nayax.intern.microservices.receiver.controller.exc;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nayax.intern.microservices.receiver.dto.ErrorDto;
import com.nayax.intern.microservices.receiver.dto.RequestDto;
import com.nayax.intern.microservices.receiver.dto.ResponseDto;
import com.nayax.intern.microservices.receiver.wrapper.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class ServiceExceptionHandler {
    private static  final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<RequestDto> handleInvalidFormatException(InvalidFormatException ex){
        HttpHeaders responseHeaders = new HttpHeaders();
        logger.info("FIred  InvalidFormatException");
        //MyExceptiion.add()
        //responsewrapper.wrap(null),new ErrorDto(ex.getMessage(), "333", "receiver"))
        return new ResponseEntity<RequestDto>(HttpStatus.OK);

    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDto> handleBindException(BindException ex){
        List<ErrorDto> errors = new ArrayList<>();
        System.out.println("here in bind exception");
        for (ObjectError error: ex.getAllErrors()){
            errors.add(new ErrorDto(error.toString(),error.getCode(),"Reciever"));
        }
        ResponseWrapper<List> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(Collections.emptyList()),errors),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ResponseDto> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex){
        List<ErrorDto> errors = new ArrayList<>();
        System.out.println("here in incorrect result exception");

            errors.add(new ErrorDto(ex.getMessage(),null,"Reciever"));

        ResponseWrapper<List> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(Collections.emptyList()),errors),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleException(Exception ex){
        List<ErrorDto> errors = new ArrayList<>();
        System.out.println("here");
        errors.add(new ErrorDto(ex.getMessage(),null,"Receiver Exception"));

        ResponseWrapper<List> responseWrapper =  new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(Collections.emptyList()),errors),HttpStatus.BAD_REQUEST);


    }

    /*@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ResponseDto> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex){
        List<ErrorDto> errors = new ArrayList<>();
        System.out.println("here in incorrect result exception");

        errors.add(new ErrorDto(ex.getMessage(),null,"Reciever"));

        ResponseWrapper<List> responseWrapper = new ResponseWrapper<>();
        return responseWrapper.wrapToResponse(() -> responseWrapper.wrap(Arrays.asList(Collections.emptyList()),errors),HttpStatus.BAD_REQUEST);

    }*/




}
