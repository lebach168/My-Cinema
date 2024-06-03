package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.MovieRequestDTO;
import com.example.MyCinema.dto.response.MovieDetailResponse;
import com.example.MyCinema.dto.response.PaginationResponse;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Movie;
import com.example.MyCinema.repository.MovieRepository;
import com.example.MyCinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public long addMovie(MovieRequestDTO movieDTO) {
        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .releaseDate(movieDTO.getReleaseDate())
                .genres(movieDTO.getGenres())
                .ageLimit(movieDTO.getAgeLimit())
                .length(movieDTO.getLength())
                .description(movieDTO.getDescription())
                .posterPath(movieDTO.getPosterPath())
                .language(movieDTO.getLanguage())
                .build();
        movie = movieRepository.save(movie);
        log.info("movie id={} has saved",movie.getId());
        return movie.getId();
    }

    @Override
    public MovieDetailResponse getMovieInfo(long movieId) {
        Movie movie = getMovie(movieId);

        return MovieDetailResponse.builder()
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .genres(movie.getGenres())
                .ageLimit(movie.getAgeLimit())
                .length(movie.getLength())
                .description(movie.getDescription())
                .posterPath(movie.getPosterPath())
                .language(movie.getLanguage())
                .build();
    }

    @Override
    public void deleteMovie(long movieId) {
        movieRepository.deleteById(movieId);
        log.info("Movie id={} has been deleted ",movieId);
    }

    @Override
    public void updateMovie(long movieId, MovieRequestDTO movieDTO) {
        Movie movie = getMovie(movieId);
        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setGenres(movieDTO.getGenres());
        movie.setAgeLimit(movieDTO.getAgeLimit());
        movie.setLength(movieDTO.getLength());
        movie.setDescription(movieDTO.getDescription());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setLanguage(movieDTO.getLanguage());
        movieRepository.save(movie);
        log.info("movie id={} updated successfully",movieId);
    }

    @Override
    public PaginationResponse<?> getNowShowingMovie(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(new Sort.Order(Sort.Direction.DESC,sortBy)));//sort by release date newest
        LocalDate today = LocalDate.now();
        Page<Movie> moviePage = movieRepository.findByReleaseDateLessThanEqualOrderByReleaseDateDesc(today, pageable);

        return convertPageToPageResponse(moviePage,pageNo,pageSize);
    }

    @Override
    public PaginationResponse<?> getUpcomingMovie(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(new Sort.Order(Sort.Direction.ASC,sortBy)));//sort by release date newest
        LocalDate today = LocalDate.now();
        Page<Movie> moviePage = movieRepository.findByReleaseDateGreaterThanOrderByReleaseDateDesc(today, pageable);
        return convertPageToPageResponse(moviePage,pageNo,pageSize);
    }

    public PaginationResponse<?> convertPageToPageResponse(Page<Movie> page, int pageNo, int pageSize){
        List<MovieDetailResponse> response = page.stream().map(movie -> MovieDetailResponse.builder()
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .genres(movie.getGenres())
                .length(movie.getLength())
                .ageLimit(movie.getAgeLimit())
                .posterPath(movie.getPosterPath())
                .language(movie.getLanguage())
                .build()).toList();
        return PaginationResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalItems(response.size())
                .data(response)
                .build();
    }

    public Movie getMovie(long movieId){
        return movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("movie + id:"+ movieId+" not found"));
    }
}
