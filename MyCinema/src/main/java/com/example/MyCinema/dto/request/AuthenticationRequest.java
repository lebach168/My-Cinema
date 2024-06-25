package com.example.MyCinema.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthenticationRequest {
    @NotNull
    String email;// or name, phone number
    @NotNull
    String password;
}
