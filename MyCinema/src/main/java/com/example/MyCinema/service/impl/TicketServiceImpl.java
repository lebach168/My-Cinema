package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.TicketRequestDTO;
import com.example.MyCinema.dto.response.TicketDetailResponse;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.*;
import com.example.MyCinema.repository.TicketRepository;
import com.example.MyCinema.service.SeatService;
import com.example.MyCinema.service.ShowtimeService;
import com.example.MyCinema.service.TicketService;
import com.example.MyCinema.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final SeatService seatService;
    private final ShowtimeService showtimeService;

    public TicketDetailResponse toTicketDetail(Ticket ticket){
        return TicketDetailResponse.builder()
                .id(ticket.getId())
                .seatName(ticket.getSeat().toStringName())
                .showtime(ticket.getShowtime())
                .price(ticket.getPrice())
                .build();
    }

    @Override
    public List<TicketDetailResponse> getAllTicketByUser(long userId) {
        List<Ticket> tickets = ticketRepository.findAllByCustomer(userId);
        tickets.sort(Comparator.comparing(BaseEntity::getCreatedAt));
        return tickets.stream().map(this::toTicketDetail).toList();
    }

    @Override
    public TicketDetailResponse getTicketDetail(long ticketId) {
        Ticket ticket = getTicketById(ticketId);
        return toTicketDetail(ticket);
    }

    @Override
    public Long createTicket(TicketRequestDTO ticketRequest) {
        Seat seat = seatService.getSeatById(ticketRequest.getSeatId());
        User customer = userService.getUserById(ticketRequest.getCustomerId());
        Showtime showtime = showtimeService.getShowtimeById(ticketRequest.getShowtimeId());
        // Discount discount;
        Ticket ticket = Ticket.builder()
                .customer(customer)
                .showtime(showtime)
                .seat(seat)
                .price(ticketRequest.getPrice())
                .build();
        Long id = ticketRepository.save(ticket).getId();
        log.info("ticket id={} has saved",id);
        return id;
    }

    @Override
    public void updateTicket(long ticketId,TicketRequestDTO ticketRequest) {
        Ticket ticket = getTicketById(ticketId);
        Seat seat = seatService.getSeatById(ticketRequest.getSeatId());
        User customer = userService.getUserById(ticketRequest.getCustomerId());
        Showtime showtime = showtimeService.getShowtimeById(ticketRequest.getShowtimeId());
        // Discount discount;
        ticket.setCustomer(customer);
        ticket.setSeat(seat);
        ticket.setShowtime(showtime);
        ticket.setPrice(ticketRequest.getPrice());
        ticketRepository.save(ticket);
        log.info("ticket id={} updated successfully",ticketId);
    }

    @Override
    public void deleteTicket(long ticketId) {
        ticketRepository.deleteById(ticketId);
        log.info("Ticket id={} has been deleted ",ticketId);
    }

    public Ticket getTicketById(long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticket not found"));
    }
}
