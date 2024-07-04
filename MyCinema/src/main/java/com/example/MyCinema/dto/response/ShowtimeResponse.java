package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Movie;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ShowtimeResponse {
    private long showtimeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Cinema cinema;
    private Room room;
    private Movie movie;
}
