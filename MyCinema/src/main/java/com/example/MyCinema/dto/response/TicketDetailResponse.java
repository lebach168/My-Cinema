package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Showtime;
import com.example.MyCinema.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Setter
@Getter
public class TicketDetailResponse {
    long id;
    String seatName;
    Showtime showtime;
    long price;
    User customer;
}
