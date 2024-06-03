package com.example.MyCinema.dto.request;

import com.example.MyCinema.model.SeatType;
import com.example.MyCinema.util.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SeatRequestDTO implements Serializable {
    @NotNull (message = "room id is required")
    Long roomId;
    @NotBlank (message = "row name must be not blank")
    String rowName;
    @NotNull (message = "quantity must be not null")
    int quantity;//generate n seat number  1 -> quantity
    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = SeatType.class, className = "Seat Type")
    String type ;//default value = standard
}
