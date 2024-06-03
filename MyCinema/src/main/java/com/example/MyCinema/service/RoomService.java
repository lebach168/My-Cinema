package com.example.MyCinema.service;

import com.example.MyCinema.dto.response.RoomResponse;
import com.example.MyCinema.model.Room;

public interface RoomService {

    Long createRoom(Long cinemaId, String name);
    void deleteRoom(long roomId);

    Room getRoomById(long roomId);
    RoomResponse getAllRoomsByCinema(Long cinemaId);
}
