package com.example.MyCinema.dto;

import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
public class ApiErrorResponse extends ApiResponse{
    private LocalDateTime timestamp;
    private String path;
    private String message;


    public ApiErrorResponse(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
        this.timestamp= LocalDateTime.now();
    }
    public ApiErrorResponse(HttpStatus httpStatus, String message,String path) {
        super(httpStatus, message);
        this.timestamp= LocalDateTime.now();
        this.path= path;
    }
}
