package com.example.MyCinema.service;


import com.example.MyCinema.dto.request.CinemaRequestDTO;
import com.example.MyCinema.dto.response.PaginationResponse;
import com.example.MyCinema.model.Cinema;

public interface CinemaService {
    Long addCinema(CinemaRequestDTO requestDTO);

    void updateCinema(long cinemaId, CinemaRequestDTO requestDTO);

    void changeAddress(long cinemaId,String address);

    void deleteCinema(long cinemaId);

    Cinema getCinemaById(long cinemaId);

    PaginationResponse<?> getAllCinemas(int pageNumber, int pageSize, String sortBy);

}
