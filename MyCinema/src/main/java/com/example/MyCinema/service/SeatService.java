package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.SeatRequestDTO;
import com.example.MyCinema.dto.response.SeatResponse;
import com.example.MyCinema.model.Seat;

public interface SeatService {
    void addRowOfSeats(SeatRequestDTO requestDTO);

    void updateSeatTypeOfRow(SeatRequestDTO requestDTO);

    void deleteSeatById(long seatId);
    void deleteSeatsByRow (long roomId, String row);
    Seat getSeatById(long seatId);

    SeatResponse getAllSeatByRoom(long roomId);

    void updateRowName(Long roomId, String oldName, String newName);
}
