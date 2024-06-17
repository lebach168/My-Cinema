package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Showtime;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class TicketDetailResponse {
    long id;
    String seatName;
    Showtime showtime;
    long price;

}
