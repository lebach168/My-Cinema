package com.example.MyCinema.exception;

import org.springframework.security.access.AccessDeniedException;

public class AuthenticationFailedException extends AccessDeniedException {
    public AuthenticationFailedException(String msg) {
        super(msg);
    }

    public AuthenticationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
