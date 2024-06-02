package com.example.MyCinema.repository;

import com.example.MyCinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    @Query(nativeQuery = true,
    value = "select * from Seat s where s.room_id=:roomId and s.row=:row")
    List<Seat> findAllByRowAndRoomId(@Param("roomId") long roomId,@Param("row") String row);

    @Query(nativeQuery = true,
    value = "select * from Seat s where s.room_id=:roomId")
    List<Seat> findAllByRoomId(@Param("roomId") long roomId);
}
