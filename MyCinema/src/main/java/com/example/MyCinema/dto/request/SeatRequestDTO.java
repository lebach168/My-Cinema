package com.example.MyCinema.dto.request;

import com.example.MyCinema.model.SeatType;
import com.example.MyCinema.util.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SeatRequestDTO implements Serializable {
    @NotNull
    Long roomId;
    @NotBlank
    String rowName;
    @NotNull
    int quantity;//generate n seat number  1 -> quantity
    @EnumValue(name = "type", enumClass = SeatType.class, className = "Seat Type")
    String type ;//default value = standard
}
