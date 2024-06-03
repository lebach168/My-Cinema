package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {
    Cinema cinema;
    List<Room> rooms;
}
