package com.springboot.hotel_management.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String reservationNotFound) {
    }
}
