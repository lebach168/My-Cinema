package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Movie;
import com.example.MyCinema.model.Room;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
public class ShowtimeScheduleResponse {
    private long id;


    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private Cinema cinema;
    private Room room;
    private Movie movie;
}
