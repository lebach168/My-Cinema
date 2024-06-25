package com.example.MyCinema.exception;

import com.example.MyCinema.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleRuntimeExceptionError(RuntimeException ex){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
        return apiErrorResponse;
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNotValidationException(MethodArgumentNotValidException ex, WebRequest request){
        String path = request.getDescription(false);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), path);
        return apiErrorResponse;
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgumentException(IllegalArgumentException ex,WebRequest request){
        String path = request.getDescription(false);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),path);
        return apiErrorResponse;
    }
    @ExceptionHandler(value = DataAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflictException(DataAlreadyExistsException ex,WebRequest request){
        String path = request.getDescription(false);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.CONFLICT,ex.getMessage(), path);
        return apiErrorResponse;
    }
    @ExceptionHandler(value = AuthenticationFailedException.class)
    public ApiErrorResponse handleAuthenticationFailedException(AccessDeniedException ex,WebRequest request){
        return new ApiErrorResponse(HttpStatus.UNAUTHORIZED,ex.getMessage(),request.getDescription(false));
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException ex,WebRequest request){
        return new ApiErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage(),request.getDescription(false));
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ApiErrorResponse handleAuthenticationException(Exception ex) {
        return new ApiErrorResponse(HttpStatus.UNAUTHORIZED,ex.getMessage());
    }
}
