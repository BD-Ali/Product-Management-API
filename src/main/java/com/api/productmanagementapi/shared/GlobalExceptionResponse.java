package com.api.productmanagementapi.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionResponse {
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        var errors = new GlobalResponse.ErrorItem("Resource not found");
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.NOT_FOUND);
    }
}
