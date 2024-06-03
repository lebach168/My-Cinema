package com.example.MyCinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RoomRequestDTO implements Serializable {
    @NotNull(message = "cinema id is required")
    Long cinemaId;

    @NotBlank(message = "row name must be not blank")
    String name;
}
