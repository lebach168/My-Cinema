package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.response.RoomResponseDTO;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.repository.RoomRepository;
import com.example.MyCinema.service.CinemaService;
import com.example.MyCinema.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CinemaService cinemaService;

    @Override
    public Long createRoom(Long cinemaId, String name) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);

        Room newRoom = Room.builder()
                .cinema(cinema)
                .name(name)
                .build();

        Room room = roomRepository.save(newRoom);
        log.info("room {} of cinema {} has saved",room.getId(),cinema.getId());
        return room.getId();
    }


    @Override
    public void deleteRoom(long roomId) {
        roomRepository.deleteById(roomId);
        log.info("room {} has been deleted",roomId);
    }

    @Override
    public Room getRoomById(long roomId) {
        return roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Room not found"));
    }

    @Override
    public RoomResponseDTO getAllRoomsByCinema(Long cinemaId) {
        List<Room> roomList= roomRepository.findAllByCinemaId(cinemaId);
        List<Room> response = roomList.stream().map(room-> Room.builder()
                .id(room.getId())
                .name(room.getName())
                .build()).toList();
        return RoomResponseDTO.builder()
                .cinema(cinemaService.getCinemaById(cinemaId))
                .rooms(response)
                .build();
    }
}
