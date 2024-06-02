package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.SeatRequestDTO;
import com.example.MyCinema.dto.response.SeatResponseDTO;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/seat")
@RequiredArgsConstructor

@Slf4j
public class SeatController {
    private final SeatService seatService;

    @GetMapping(path = "/list/{roomId}")
    public ApiResponse<?> getAllSeatsByRoomId(@PathVariable("roomId") long roomId){
        log.info("request get all seats by room: {}",roomId);
        SeatResponseDTO responseData = seatService.getAllSeatByRoom(roomId);
        return new ApiResponse<>(HttpStatus.OK,"list of seats", responseData);
    }

    @GetMapping(path = "/{seatId}")
    public ApiResponse<?> getSeatById(@PathVariable("seatId") Long seatId){
        log.info("request get seat by id : {}",seatId);
        Seat responseData = seatService.getSeatById(seatId);
        return new ApiResponse<>(HttpStatus.OK,"seat", responseData);
    }

    @PostMapping(path = "/create")
    public ApiResponse<?> createRowOfSeats(@Valid @RequestBody SeatRequestDTO rowSeat){
        log.info("request create row of seats for room : {}", rowSeat.getRoomId());
        seatService.addRowOfSeats(rowSeat);
        return new ApiResponse<>(HttpStatus.OK,"Row of seats added successfully");
    }

    @PutMapping(path = "/update")
    public ApiResponse<?> updateSeatType( @Valid @RequestBody SeatRequestDTO seat){
        log.info("request update type row of seats for room : {}", seat.getRoomId());
        try{
            seatService.updateSeatTypeOfRow(seat);
            return new ApiResponse<>(HttpStatus.OK,"Row of seats updated successfully");
        }
        catch (Exception ex){
            log.error("errorMessage={}", ex.getMessage(), ex.getCause());
            throw new RuntimeException("Update seats failed");
        }
    }
    @PutMapping(path = "/update_row_name/{roomId}/{oldName}/{newName}")
    public ApiResponse<?> updateRowName( @PathVariable("roomId") Long roomId,
                                         @PathVariable("oldName") String oldName,
                                         @PathVariable("newName") String newName){
        log.info("request update row name of seats for room : {}", roomId);
        try{
            seatService.updateRowName(roomId,oldName,newName);
            return new ApiResponse<>(HttpStatus.OK,"Row of seats updated successfully");
        }
        catch (Exception ex){
            log.error("errorMessage={}", ex.getMessage(), ex.getCause());
            throw new RuntimeException("Update seats failed");
        }
    }
    @DeleteMapping(path = "/{seatId}")
    public ApiResponse<?> deleteSeat(@PathVariable(name = "seatId") long seatId){
        log.info("Request delete seat {}",seatId);
        try {
            seatService.deleteSeatById(seatId);
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Seat deleted successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            throw new RuntimeException("Delete seat fail");
        }
    }
}
