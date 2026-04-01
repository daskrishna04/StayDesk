package com.springboot.hotel_management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    public String reservationNotFound(ReservationNotFoundException reservation){
        return "error/reservation-not-found";
    }
}
