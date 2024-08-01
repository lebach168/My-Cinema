package com.example.MyCinema.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder

public class ShowtimeDetailResponse {
    
    Set<SeatResponse> seats;
}
