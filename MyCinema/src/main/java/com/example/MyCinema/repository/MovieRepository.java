package com.example.MyCinema.repository;

import com.example.MyCinema.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Page<Movie> findByReleaseDateLessThanEqualOrderByReleaseDateDesc(LocalDate currentDate, Pageable pageable);

    Page<Movie> findByReleaseDateGreaterThanOrderByReleaseDateDesc(LocalDate currentDate, Pageable pageable);
}
