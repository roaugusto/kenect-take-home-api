package com.kenect.contact.aggregator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class RequestHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> handlerNotFoundException(WebRequest req, NotFoundException e) {
        log.error(e.getMessage());
        return sendNotFoundException(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handlerException(WebRequest req, Exception e) {
        log.error(e.getMessage());
        return sendInternalServerError(e.getMessage());
    }

    private ResponseEntity<ErrorResponse> sendInternalServerError(String message) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),  message);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> sendNotFoundException(String message) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
