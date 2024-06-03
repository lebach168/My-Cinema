package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.RoomRequestDTO;
import com.example.MyCinema.dto.response.RoomResponse;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/room")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @GetMapping(path = "/list/{cinemaId}")
    public ApiResponse<?> getAllRoomsByCinema(@PathVariable("cinemaId") Long cinemaId){
        RoomResponse responseData = roomService.getAllRoomsByCinema(cinemaId);
        log.info("Request get all rooms of cinema {}",cinemaId);
        return new ApiResponse<>(HttpStatus.OK,"rooms",responseData);
    }
    @GetMapping(path = "/{roomId}")
    public ApiResponse<?> getRoomsById(@PathVariable("roomId") Long roomId){
        Room responseData = roomService.getRoomById(roomId);
        log.info("Request get room by id {}",roomId);
        return new ApiResponse<>(HttpStatus.OK,"rooms",responseData);
    }
    @PostMapping(path = "/add")
    public ApiResponse<?> addRoomByCinemaId(@Valid @RequestBody RoomRequestDTO room){
        Long responseData = roomService.createRoom(room.getCinemaId(),room.getName());
        log.info("Request create 1 room name:{} for cinema:{}",room.getName(),room.getCinemaId());
        return new ApiResponse<>(HttpStatus.ACCEPTED,"room successfully added",responseData);
    }

    @DeleteMapping(path = "/{roomId}")
    public ApiResponse<?> deleteRoom(@PathVariable("roomId") Long roomId){
        log.info("Request delete room {}",roomId);
        try {
            roomService.deleteRoom(roomId);
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Room deleted successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            throw new RuntimeException("Delete room fail");
        }
    }
}
