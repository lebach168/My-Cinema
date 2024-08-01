package com.example.MyCinema.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ShowtimeRequestDTO implements Serializable {

    @NotNull(message = "start time must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern="yyyy-MM-dd HH:mm")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm")
    LocalDateTime startTime;
    @NotBlank
    Long cinemaId;
    @NotBlank
    Long roomId;
    @NotBlank
    Long movieId;
}
