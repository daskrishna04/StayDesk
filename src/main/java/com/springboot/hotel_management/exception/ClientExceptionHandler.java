package com.springboot.hotel_management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler {
    @ExceptionHandler(ClientNotFoundException.class)
    public String clientNotFound(ClientNotFoundException client){
        return "error/client-not-found";
    }
}
