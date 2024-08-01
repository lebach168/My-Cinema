package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.BookingRequest;
import com.example.MyCinema.dto.request.ShowtimeDTO;
import com.example.MyCinema.dto.response.ShowtimeDetailResponse;
import com.example.MyCinema.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/booking")
@RequiredArgsConstructor

public class ReservationController {
    private final ReservationService reservationService;

    //get all seat's state of showtime
    @GetMapping(path= "")
    public ApiResponse<?> getAllSeatsState(@Valid @RequestBody ShowtimeDTO showtime ){
        ShowtimeDetailResponse response = reservationService.getShowtimeDetailState(showtime);
        return new ApiResponse<>(HttpStatus.OK,"message",response);
    }
    // request to create a reservation
    @PostMapping(path="")
    public ApiResponse<?> reserveSeats(@Valid @RequestBody BookingRequest request){
        reservationService.createReservation(request);
        return new ApiResponse<>(HttpStatus.OK,"OK");
    }
}
