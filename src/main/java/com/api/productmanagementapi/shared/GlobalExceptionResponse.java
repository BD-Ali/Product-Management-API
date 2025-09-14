package com.api.productmanagementapi.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionResponse {
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Resource not found"));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomResponseException(CustomResponseException ex) {
        var errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.valueOf(ex.getCode()) );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<?>> handleGenericException(Exception ex) {
        var errors = List.of(new GlobalResponse.ErrorItem("Internal server error"));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse<Void>> handleUnreadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new GlobalResponse<>(List.of(new GlobalResponse.ErrorItem("Invalid or missing request body"))));
    }

}
