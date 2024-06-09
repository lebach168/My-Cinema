package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.ShowtimeRequestDTO;
import com.example.MyCinema.dto.response.ShowtimeScheduleResponse;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Movie;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Showtime;
import com.example.MyCinema.repository.ShowtimeRepository;
import com.example.MyCinema.service.CinemaService;
import com.example.MyCinema.service.MovieService;
import com.example.MyCinema.service.RoomService;
import com.example.MyCinema.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieService movieService;
    private final RoomService roomService;
    private final CinemaService cinemaService;

    @Override
    public Long createShowtime(ShowtimeRequestDTO showtimeDTO) {
        Movie movie = movieService.getMovieById(showtimeDTO.getMovieId());
        Room room = roomService.getRoomById(showtimeDTO.getRoomId());
        Cinema cinema =cinemaService.getCinemaById(showtimeDTO.getCinemaId());
        LocalDateTime startTime = showtimeDTO.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(movie.getLength()+15);
        if(!isRoomAvailable(room.getId(),startTime,endTime)){
            throw  new RuntimeException("This room is not available at time");
        }
        Showtime showtime = Showtime.builder()
                .room(room)
                .cinema(cinema)
                .movie(movie)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Long id  = showtimeRepository.save(showtime).getId();
        log.info("new showtime id={} has been saved ",id);
        return id;
    }

    @Override
    public void updateShowtime(long showtimeId, ShowtimeRequestDTO showtimeDTO) {
        Showtime showtime = getShowtimeById(showtimeId);
        int movieLength=120;
        if(showtime.getRoom().getId()!= showtimeDTO.getRoomId()){
            Room room = roomService.getRoomById(showtimeDTO.getRoomId());
            showtime.setRoom(room);
        }
        if(showtime.getMovie().getId()!= showtimeDTO.getMovieId()){
            Movie movie = movieService.getMovieById(showtimeDTO.getMovieId());
            movieLength=movie.getLength();
            showtime.setMovie(movie);
        }
        if(showtime.getCinema().getId()!= showtimeDTO.getCinemaId()){
           Cinema cinema = cinemaService.getCinemaById(showtimeDTO.getCinemaId());
           showtime.setCinema(cinema);
        }
        LocalDateTime startTime = showtimeDTO.getStartTime();
        LocalDateTime endTime =  startTime.plusMinutes(movieLength+15);
        if(!isRoomAvailable(showtimeDTO.getRoomId(),startTime,endTime)){
            throw  new RuntimeException("This room is not available at time");
        }
        showtime.setStartTime(startTime);
        showtime.setEndTime(endTime);
        showtimeRepository.save(showtime);
        log.info("showtime id={} has been updated",showtimeId);
    }

    @Override
    public void deleteShowtime(long showtimeId) {
        showtimeRepository.deleteById(showtimeId);
        log.info("Showtime id={} has been deleted ",showtimeId);
    }


    public List<ShowtimeScheduleResponse> convertToResponseType(List<Showtime> showtimes){
        return showtimes.stream()
                .map(showtime ->ShowtimeScheduleResponse.builder()
                        .id(showtime.getId())
                        .cinema(showtime.getCinema())
                        .room(showtime.getRoom())
                        .movie(showtime.getMovie())
                        .startTime(showtime.getStartTime())
                        .endTime(showtime.getEndTime())
                        .build()).toList();
    }

    @Override
    public List<ShowtimeScheduleResponse> getScheduleShowtimeByCinema(long cinemaId, LocalDate selectedDate) {
        if(selectedDate == null) {
            selectedDate = LocalDate.now();
        }
        List<Showtime> showtimes = showtimeRepository.findAllByCinemaAndAndStartTimeOrderByStartTime(cinemaId,selectedDate);
        return convertToResponseType(showtimes);
    }

    @Override
    public List<ShowtimeScheduleResponse> getScheduleShowtimeByMovie(long movieId ) {

        List<Showtime> showtimes = showtimeRepository.findAllByMovieOrderByStartTime(movieId);
        return convertToResponseType(showtimes);
    }

    public boolean isRoomAvailable(long roomId, LocalDateTime startTime, LocalDateTime endTime){
        List<Showtime> showtimes = showtimeRepository.findAllByRoomAndStartTimeBetween(roomId, startTime.minusMinutes(30), endTime.plusMinutes(30));
        return showtimes.isEmpty();
    }
    public Showtime getShowtimeById(long showtimeId){
        return showtimeRepository.findById(showtimeId).orElseThrow(()->new ResourceNotFoundException("showtime id=" + showtimeId+" not found"));
    }

}
