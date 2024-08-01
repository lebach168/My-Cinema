package com.example.MyCinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ShowtimeDTO implements Serializable {
    @NotBlank
    Long showtimeId;
    @NotBlank
    Long cinemaId;
    @NotBlank
    Long roomId;

    Long movieId;
}
