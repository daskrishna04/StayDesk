package com.springboot.hotel_management.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String roomNotFound) {
    }
}
