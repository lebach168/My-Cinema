package com.example.MyCinema.exception;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String message) {
        super(message);
    }
    public RateLimitExceededException(){}
}
