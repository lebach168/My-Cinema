package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.ShowtimeRequestDTO;
import com.example.MyCinema.dto.response.ShowtimeScheduleResponse;
import com.example.MyCinema.model.Showtime;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {
    Long createShowtime(ShowtimeRequestDTO showtimeDTO);
    void updateShowtime(long showtimeId, ShowtimeRequestDTO showtimeDTO);
    void deleteShowtime(long showtimeId);
    Showtime getShowtimeById(long showtimeId);
    List<ShowtimeScheduleResponse> getScheduleShowtimeByCinema(long cinemaId, LocalDate selectedDate);
    List<ShowtimeScheduleResponse> getScheduleShowtimeByMovie(long movieId);
}
