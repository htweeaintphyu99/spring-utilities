package com.tutorial.springaudit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = {RuntimeException.class})
//    public ResponseEntity<Object> RunTimeExceptionHandler(RuntimeException e) {
//
//        throw e;
//    }
//
//}