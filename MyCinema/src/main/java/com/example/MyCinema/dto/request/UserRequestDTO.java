package com.example.MyCinema.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "email must be not blank")
    private String email;

    private String password;

    @NotBlank(message = "name must be not blank")
    private String name;

    @NotNull(message = "date of birth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private int point =0;
    private String level="MEMBER";//MEMBER | VIP | VVIP


}
