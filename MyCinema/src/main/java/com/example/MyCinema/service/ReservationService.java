package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.BookingRequest;
import com.example.MyCinema.dto.request.ShowtimeDTO;
import com.example.MyCinema.dto.response.ShowtimeDetailResponse;

public interface  ReservationService {
    ShowtimeDetailResponse getShowtimeDetailState(ShowtimeDTO showtimeDTO);
    void createReservation(BookingRequest request);

}

  
