package com.springboot.hotel_management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoomExceptionHandler {
    @ExceptionHandler(RoomNotFoundException.class)
    public String roomNotFound(RoomNotFoundException room){
        return "error/room-not-found";
    }
}
