package com.example.MyCinema.repository;

import com.example.MyCinema.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
    @Query(nativeQuery = true,
    value = "select * from Showtime st where st.room_id = :roomId and ((st.start_time between :from and :to) or (st.end_time between :from and :to))")
    List<Showtime> findAllByRoomAndStartTimeBetween(@Param("roomId")long roomId,@Param("from") LocalDateTime from, @Param("to")LocalDateTime to);

    @Query(nativeQuery = true,
    value = "select * from Showtime st where st.cinema_id =:cinemaId and st.start_time<= :date and st.start_time<dateadd(day,1,selectedDate order by st.start_time")
    List<Showtime> findAllByCinemaAndAndStartTimeOrderByStartTime(@Param("cinemaId") long cinemaId, @Param("date") LocalDate selectedDate);

    @Query(nativeQuery = true,
    value = "select * from Showtime st where st.movie_id=:movieId order by st.start_time")
    List<Showtime> findAllByMovieOrderByStartTime(@Param("movieId") long movieId);
}
