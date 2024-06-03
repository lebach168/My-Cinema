package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.MovieRequestDTO;
import com.example.MyCinema.dto.response.MovieDetailResponse;
import com.example.MyCinema.dto.response.PaginationResponse;

public interface MovieService {
    long addMovie(MovieRequestDTO movieDTO);
    MovieDetailResponse getMovieInfo(long movieId);
    void deleteMovie(long movieId);
    void updateMovie(long movieId, MovieRequestDTO movieDTO);
    PaginationResponse<?> getNowShowingMovie(int pageNo, int pageSize, String sortBy);

    PaginationResponse<?> getUpcomingMovie(int pageNo, int pageSize, String sortBy);
}
