package com.sweetshop.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutofStockException.class)
    public ResponseEntity<Map<String, Object>> handleOutOfStock(OutofStockException ex) {
        return buildError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return buildError(
                "Invalid username or password",
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        return buildError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        ex.printStackTrace();
        return buildError(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InvalidRestockAmountException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidRestockAmountException(InvalidRestockAmountException ex)
    {
        return buildError(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SweetNotFoundException.class)
    public  ResponseEntity<Map<String,Object>> handleSweetNotFoundException(SweetNotFoundException ex)
    {
        return buildError(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Map<String, Object>> buildError(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", message);
        error.put("status", status.value());
        return new ResponseEntity<>(error, status);
    }
}
