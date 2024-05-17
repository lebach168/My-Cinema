package com.example.MyCinema.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;

@Getter

public class CinemaRequestDTO implements Serializable {
    @NotBlank(message = "Cinema's name must not be blank")
    String name;
    @NotBlank(message = "Cinema's address must not be blank")
    String address;

}
