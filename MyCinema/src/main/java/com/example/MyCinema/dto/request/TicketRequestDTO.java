package com.example.MyCinema.dto.request;

import com.example.MyCinema.model.Discount;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.model.Showtime;
import com.example.MyCinema.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TicketRequestDTO {

    @NotNull(message = "seat id is required")
    long seatId;
    @NotNull (message = "showtime id is required")
    long showtimeId;
    @NotNull (message = "customerId is required")
    long customerId;
    Discount discount;
    @NotNull (message = "price is required")
    long price;

}
