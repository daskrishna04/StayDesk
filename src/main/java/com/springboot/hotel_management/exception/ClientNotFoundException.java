package com.springboot.hotel_management.exception;

import lombok.RequiredArgsConstructor;


public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String msg){

    }
}
