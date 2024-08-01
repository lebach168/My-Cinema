package com.example.MyCinema.model.redisModel;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
public class Reservation {
    long showtimeId;
    long seatId;
    String userId;

}
