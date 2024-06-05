package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.ShowtimeRequestDTO;
import com.example.MyCinema.dto.response.ShowtimeScheduleResponse;
import com.example.MyCinema.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/showtime")
@Validated
public class ShowtimeController {
    @Autowired
    private final ShowtimeService showtimeService;

    //get showtime by cinema, by movie ->order by time, find by day,
    @GetMapping(path = "/schedule/{cinemaId}")
    public ApiResponse<?> getScheduleByCinema(@PathVariable(name = "cinemaId") long cinemaId,
                                              @RequestBody(required = false) LocalDate selectedDate ){
        log.info("request get showtime schedule of cinema id={}",cinemaId);
        List<?> response = showtimeService.getScheduleShowtimeByCinema(cinemaId,selectedDate);
        return new ApiResponse<>(HttpStatus.OK,"success",response);
    }
    @GetMapping(path = "/{movieId}")
    public ApiResponse<?> getScheduleByMovie(@PathVariable(name = "movieId") long movieId,
                                              @RequestBody(required = false) LocalDate selectedDate ){
        log.info("request get all showtime of movie id={}",movieId);
        List<?> response = showtimeService.getScheduleShowtimeByMovie(movieId);
        return new ApiResponse<>(HttpStatus.OK,"success",response);
    }
    //create new showtime
    @PostMapping(path = "/create")
    public ApiResponse<?> createShowtime(@Valid @RequestBody ShowtimeRequestDTO showtimeDTO){
        log.info("request create new showtime");
        long response = showtimeService.createShowtime(showtimeDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"success",response);
    }
    //update showtime
    @PutMapping(path = "/{showtimeId}")
    public ApiResponse<?> updateShowtime(@PathVariable("showtimeId") long showtimeId,
            @Valid @RequestBody ShowtimeRequestDTO showtimeDTO){
        log.info("request update showtime id={}",showtimeId);
        showtimeService.updateShowtime(showtimeId,showtimeDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"updated");
    }
    //delete showtime
    @DeleteMapping(path = "/{showtimeId}")
    public ApiResponse<?> deleteShowtime(@PathVariable("showtimeId") long showtimeId){
        log.info("request delete showtime id={}",showtimeId);
        showtimeService.deleteShowtime(showtimeId);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"deleted");
    }


}
