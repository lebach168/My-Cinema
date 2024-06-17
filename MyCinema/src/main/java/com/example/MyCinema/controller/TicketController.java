package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.TicketRequestDTO;
import com.example.MyCinema.dto.response.TicketDetailResponse;
import com.example.MyCinema.dto.response.UserDetailResponse;
import com.example.MyCinema.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/tickets")
@Validated
public class TicketController {
    private final TicketService ticketService;

    //get all tickets by user, sort by date,
    @GetMapping(path = "/{userId}")
    ApiResponse<?> getAllTicketsByUser(@PathVariable Long userId){
        log.info("request get all tickets of user id={}",userId);
        List<TicketDetailResponse> response = ticketService.getAllTicketByUser(userId);
        return new ApiResponse<>(HttpStatus.OK,"ok",response);
    }

    //get ticket detail by id
    @GetMapping(path = "/{ticketId}")
    ApiResponse<?> getTicketDetail(@PathVariable Long ticketId){
        log.info("request get detail ticket of id={}",ticketId);
        TicketDetailResponse response = ticketService.getTicketDetail(ticketId);
        return new ApiResponse<>(HttpStatus.OK,"ok",response);
    }
    //create ticket
    @PostMapping(path = "")
    ApiResponse<?> createTicket(@Valid @RequestBody TicketRequestDTO ticketRequest){
        log.info("request create new ticket");
        Long response = ticketService.createTicket(ticketRequest);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"successfully",response);
    }
    //update ticket
    @PutMapping(path = "{ticketId}")
    ApiResponse<?> updateTicket(@PathVariable Long ticketId,
                                @Valid @RequestBody TicketRequestDTO ticketRequest){
        log.info("request update ticket id={}",ticketId);
        ticketService.updateTicket(ticketId, ticketRequest);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"successfully");
    }
    //update patch ticket

    //delete ticket
    @DeleteMapping(path = "{ticketId}")
    public ApiResponse<?> deleteTicket(@PathVariable Long ticketId){
        log.info("request delete movie id:{}",ticketId);
        ticketService.deleteTicket(ticketId);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"ticket successfully deleted ");
    }

}
