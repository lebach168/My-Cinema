package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class SeatResponse {
    Room room;
    List<Seat> seats;
}
