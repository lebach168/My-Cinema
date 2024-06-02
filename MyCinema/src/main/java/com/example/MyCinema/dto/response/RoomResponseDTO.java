package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponseDTO {
    Cinema cinema;
    List<Room> rooms;
}
