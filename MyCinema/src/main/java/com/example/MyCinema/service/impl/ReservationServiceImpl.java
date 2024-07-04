package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.BookingRequest;
import com.example.MyCinema.dto.request.ShowtimeDTO;
import com.example.MyCinema.dto.response.SeatResponse;
import com.example.MyCinema.dto.response.ShowtimeDetailResponse;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.model.Showtime;
import com.example.MyCinema.redis.DAO.RateLimiterDao;
import com.example.MyCinema.redis.DAO.ReservationDao;
import com.example.MyCinema.redis.DAO.ShowtimeDao;
import com.example.MyCinema.redis.RedisSchema;
import com.example.MyCinema.repository.SeatRepository;
import com.example.MyCinema.service.ReservationService;
import com.example.MyCinema.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final ShowtimeDao showtimeDao;
    private final RateLimiterDao rateLimiterDao;

    @Override
    public ShowtimeDetailResponse getShowtimeDetailState(ShowtimeDTO showtimeDto) {
        Set<SeatResponse> seats = showtimeDao.getCurrentState(showtimeDto);
        return ShowtimeDetailResponse.builder().seats(seats).build();
    }

    @Override
    public void createReservation(BookingRequest request) {
        rateLimiterDao.hit(request.getUserId());
        reservationDao.createReservation(request.getUserId(),request.getShowtimeId(),request.getSeatIds());
        // create payment info and ticket
    }


}
