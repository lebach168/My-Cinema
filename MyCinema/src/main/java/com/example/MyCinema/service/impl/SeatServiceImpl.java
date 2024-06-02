package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.SeatRequestDTO;
import com.example.MyCinema.dto.response.RoomResponseDTO;
import com.example.MyCinema.dto.response.SeatResponseDTO;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.model.SeatType;
import com.example.MyCinema.repository.RoomRepository;
import com.example.MyCinema.repository.SeatRepository;
import com.example.MyCinema.service.RoomService;
import com.example.MyCinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final RoomService roomService;
    @Override
    public void addRowOfSeats(SeatRequestDTO requestDTO) {
        Room room = roomService.getRoomById(requestDTO.getRoomId());
        for(int i =1;i<=requestDTO.getQuantity();i++){
            Seat seat = Seat.builder()
                    .room(room)
                    .number(i)
                    .rowName(requestDTO.getRowName())
                    .type(SeatType.valueOf(requestDTO.getType().toUpperCase()))
                    .build();
            seatRepository.save(seat);
        }

    }
    @Override
    public void updateSeatTypeOfRow(SeatRequestDTO requestDTO) {
        List<Seat> seats = seatRepository.findAllByRowAndRoomId(requestDTO.getRoomId(), requestDTO.getRowName());
        for(Seat seat:seats){
            seat.setType(SeatType.valueOf(requestDTO.getType().toUpperCase()));
            seatRepository.save(seat);
        }
        log.info("Row={} in room={} has been changed type to {}",requestDTO.getRowName(),requestDTO.getRoomId(), requestDTO.getType());
    }

    @Override
    public void deleteSeatById(long seatId) {
        seatRepository.deleteById(seatId);
        log.info("Seat id={} has been deleted ",seatId);
    }

    @Override
    public void deleteSeatsByRow(long roomId, String row) {
        List<Seat> seats = seatRepository.findAllByRowAndRoomId(roomId, row);
        for(Seat seat:seats){
            seatRepository.deleteById(seat.getId());
        }
        log.info("Row={} in room={} has been deleted ",row,roomId);
    }

    @Override
    public Seat getSeatById(long seatId) {
        return seatRepository.findById(seatId).orElseThrow(()->new ResourceNotFoundException("seat "+ seatId +" not found"));
    }

    @Override
    public void updateRowName(Long roomId, String oldName, String newName) {
        List<Seat> seats = seatRepository.findAllByRowAndRoomId(roomId, oldName);
        for(Seat seat:seats){
            seat.setRowName(newName);
            seatRepository.save(seat);
        }
        log.info("Row={} in room={} has been changed name to {}",oldName,roomId,newName);
    }
    @Override
    public SeatResponseDTO getAllSeatByRoom(long roomId) {
        List<Seat> seatList= seatRepository.findAllByRoomId(roomId);
        Room room = roomService.getRoomById(roomId);
        List<Seat> response = seatList.stream().map(seat-> Seat.builder()
                .id(seat.getId())
                .rowName(seat.getRowName())
                .build()).toList();
        return SeatResponseDTO.builder()
                .room(room)
                .seats(response)
                .build();
    }


}
