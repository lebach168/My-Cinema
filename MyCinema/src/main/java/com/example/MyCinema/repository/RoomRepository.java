package com.example.MyCinema.repository;

import com.example.MyCinema.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "Select * from Room r where r.cinema_id =:cinemaId" ,nativeQuery = true)
    List<Room> findAllByCinemaId(@Param("cinemaId")Long cinemaId);


}
