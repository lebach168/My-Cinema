package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Seat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SeatResponseDTO {
    Room room;
    List<Seat> seats;
}
