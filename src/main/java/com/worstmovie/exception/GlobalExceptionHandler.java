package com.worstmovie.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomErrorResponse> businessExceptionHandler(NoSuchElementException businessException) {
        log.info("GlobalExceptionHandler::businessExceptionHandler::Param: {} ", businessException.getMessage());

        return new ResponseEntity<>(new CustomErrorResponse(businessException.getMessage()),
                HttpStatus.NO_CONTENT);
    }
}
