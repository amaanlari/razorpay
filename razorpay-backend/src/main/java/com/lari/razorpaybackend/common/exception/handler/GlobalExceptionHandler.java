package com.lari.razorpaybackend.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lari.razorpaybackend.common.exception.DuplicateResourceException;
import com.lari.razorpaybackend.common.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex) {

        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of(
            errorCode.getCode(),
            errorCode.getDescription()
        ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        
    
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(
            ex.getCode(),
            ex.getLocalizedMessage()
        ));
    }
}
