package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.CinemaRequestDTO;
import com.example.MyCinema.dto.response.PaginationResponse;
import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.service.CinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/cinema")
@RequiredArgsConstructor
@Slf4j
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping(path = "/add")
    public ApiResponse<Long> addCinema(@Valid @RequestBody CinemaRequestDTO cinema){
        log.info("Request add cinema: name= {} address= {}",cinema.getName(),cinema.getAddress());
        Long cinemaId = cinemaService.addCinema(cinema);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"cinema successfully added",cinemaId);
    }


    @GetMapping(path = "/list")
    public ApiResponse<PaginationResponse<?>> getAllCinemas(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                            @RequestParam(required = false, defaultValue = "20") int pageSize,
                                                            @RequestParam(required = false, defaultValue = "name") String sortBy){
        log.info("Request get all cinemas");
        PaginationResponse<?> response = cinemaService.getAllCinemas(pageNo,pageSize,sortBy);
        return new ApiResponse<>(HttpStatus.OK,"cinemas", response);
    }
    @GetMapping(path = "/{cinemaId}")
    public ApiResponse<?> getCinemaById(@PathVariable(name = "cinemaId") long cinemaId){
        log.info("Request get cinema by id= {}",cinemaId);
        Cinema response = cinemaService.getCinemaById(cinemaId);
        return new ApiResponse<>(HttpStatus.OK,"cinema id: "+ cinemaId, response);
    }
    @PutMapping(path = "/{cinemaId}")
    public ApiResponse<?> updateCinemaById(@PathVariable(name = "cinemaId") long cinemaId,
                                           @RequestBody CinemaRequestDTO cinemaDTO){
        log.info("Request update cinema id= {}",cinemaId);
        try {
            cinemaService.updateCinema(cinemaId,cinemaDTO);
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Cinema updated successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            throw new RuntimeException("Update cinema failed");
        }
    }

    @DeleteMapping(path = "/{cinemaId}")
    public ApiResponse<?> deleteCinemaById(@PathVariable(name = "cinemaId") long cinemaId){
        log.info("Request delete cinema id= {}",cinemaId);
        try {
            cinemaService.deleteCinema(cinemaId);
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Cinema deleted successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            throw new RuntimeException("Delete cinema failed");
        }
    }

//    @GetMapping(path = "/now-showing")
//    @GetMapping(path = "upcoming")
}
