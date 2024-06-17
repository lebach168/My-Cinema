package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.MovieRequestDTO;
import com.example.MyCinema.dto.response.MovieDetailResponse;
import com.example.MyCinema.dto.response.PaginationResponse;
import com.example.MyCinema.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MovieController {
    private final MovieService movieService;

    @GetMapping(path = "/{movieId}")
    public ApiResponse<?> getMovieInfo(@PathVariable Long movieId ){
        log.info("request get data info movie id:{}",movieId);
        MovieDetailResponse response = movieService.getMovieInfo(movieId);
        return new ApiResponse<>(HttpStatus.OK,"movie info",response);
    }
    @PostMapping(path = "/add")
    public ApiResponse<?> addMovie(@Valid @RequestBody MovieRequestDTO movieDTO){
        log.info("request add new movie to show list");
        Long id = movieService.addMovie(movieDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"movie successfully added ",id);
    }

    @PutMapping(path = "/{movieId}")
    public ApiResponse<?> updateMovie(@PathVariable Long movieId,
                                      @Valid @RequestBody MovieRequestDTO movieDTO){
        log.info("request update movie id:{} info",movieId);
        movieService.updateMovie(movieId,movieDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"movie successfully updated ");
    }

    @DeleteMapping(path = "/{movieId}")
    public ApiResponse<?> deleteMovie(@PathVariable Long movieId){
        log.info("request delete movie id:{}",movieId);
        movieService.deleteMovie(movieId);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"movie successfully deleted ");
    }
    @GetMapping(path = "/now-showing")
    public ApiResponse<?> getNowShowingMovie(@RequestParam(required = false, defaultValue = "0")int pageNo,
                                             @RequestParam(required = false, defaultValue = "8")int pageSize,
                                             @RequestParam(required = false, defaultValue = "release_date") String sortBy){
        log.info("request get currently showing movie");
        PaginationResponse<?> response = movieService.getNowShowingMovie(pageNo, pageSize,sortBy);
        return new ApiResponse<>(HttpStatus.OK,"now showing",response);
    }
    @GetMapping(path = "/upcoming")
    public ApiResponse<?> getUpcomingMovie(@RequestParam(required = false, defaultValue = "0")int pageNo,
                                             @RequestParam(required = false, defaultValue = "8")int pageSize,
                                             @RequestParam(required = false, defaultValue = "release_date") String sortBy){
        log.info("request get upcoming movie");
        PaginationResponse<?> response = movieService.getUpcomingMovie(pageNo, pageSize,sortBy);
        return new ApiResponse<>(HttpStatus.OK,"upcoming movie",response);
    }
//    @GetMapping(path = "genres? ") --> Paging
}
