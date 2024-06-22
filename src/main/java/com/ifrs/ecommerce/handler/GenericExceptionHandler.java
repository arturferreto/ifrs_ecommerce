package com.ifrs.ecommerce.handler;

import com.ifrs.ecommerce.responses.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleGenericExceptions(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericExceptions(Exception exception, WebRequest request) {
        log.error("handleGenericExceptions() - message <{}> - error: ", exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new DefaultResponse(
                        null,
                        exception.getMessage(),
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<Object> handleResponseStatusExceptions(ResponseStatusException exception, WebRequest request) {
        log.error("handleResponseStatusExceptions() - message <{}> - error: ", exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatusCode()).body(
                new DefaultResponse(
                        null,
                        String.valueOf(exception.getStatusCode().value()),
                        exception.getReason())
        );
    }

}