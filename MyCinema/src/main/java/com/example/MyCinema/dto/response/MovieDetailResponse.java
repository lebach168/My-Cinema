package com.example.MyCinema.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class MovieDetailResponse {
    private String title;
    private LocalDate releaseDate;
    private String genres;
    private int ageLimit;
    private int length;
    private String description;
    private String posterPath;
    private String language;
}
