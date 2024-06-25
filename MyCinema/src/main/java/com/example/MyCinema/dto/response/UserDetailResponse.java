package com.example.MyCinema.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Builder
public class UserDetailResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDate dob;
    private int point;
    private String level;
}
