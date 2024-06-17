package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.TicketRequestDTO;
import com.example.MyCinema.dto.response.TicketDetailResponse;
import com.example.MyCinema.model.Ticket;

import java.util.List;

public interface TicketService {
    List<TicketDetailResponse> getAllTicketByUser(long userId);

    TicketDetailResponse getTicketDetail(long ticketId);

    Long createTicket(TicketRequestDTO ticketRequest);

    void updateTicket(long ticketId, TicketRequestDTO ticketRequest);

    void deleteTicket(long ticketId);

}
