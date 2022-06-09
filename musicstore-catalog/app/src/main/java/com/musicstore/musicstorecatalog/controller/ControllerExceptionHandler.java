package com.musicstore.musicstorecatalog.controller;


import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.exception.UnprocessableRequestException;
import com.musicstore.musicstorecatalog.model.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(value = UnprocessableRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleUnprocessableException(UnprocessableRequestException ure) {
        CustomErrorResponse error = new CustomErrorResponse(ure.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleProductNotFoundException(EntityNotFoundException enfe) {
        CustomErrorResponse error = new CustomErrorResponse(enfe.getMessage(), HttpStatus.NOT_FOUND);
        ResponseEntity<CustomErrorResponse> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}