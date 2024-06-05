package com.example.MyCinema.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class MovieRequestDTO implements Serializable {
    @NotBlank(message = "title must be not blank")
    private String title;

    @NotNull(message = "release date must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;


    @NotNull(message = "genres must be not null")
    private String genres;

    private int ageLimit =6;
    @NotNull(message = "movie's length must be not null")
    private int length;

    private String description;
    @NotNull(message = "poster path must be not null")
    private String posterPath;
    @NotNull(message = "movie language must be not null")
    private String language;
}
