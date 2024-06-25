package com.example.MyCinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter

public class StaffRequestDTO implements Serializable {
    @NotNull
    @NotBlank
    String name;

    String password;
    @NotNull
    String role;
}
