package com.example.MyCinema.exception;

public class SeatAlreadyReservedException extends  RuntimeException{
    public SeatAlreadyReservedException(String message){
        super(message);
    }
}
