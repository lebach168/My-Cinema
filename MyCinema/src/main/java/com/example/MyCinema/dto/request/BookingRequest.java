package com.example.MyCinema.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
public class BookingRequest implements Serializable {
    Long userId;
    Long showtimeId;
    List<Long> seatIds;
}
