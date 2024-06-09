package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.CinemaRequestDTO;
import com.example.MyCinema.dto.response.PaginationResponse;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.repository.CinemaRepository;
import com.example.MyCinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    @Override
    public Long addCinema(CinemaRequestDTO requestDTO) {
        Cinema cinema =Cinema.builder()
                .name(requestDTO.getName())
                .address(requestDTO.getAddress())
                .build();
        cinema = cinemaRepository.save(cinema);
        log.info("cinema id={} has saved",cinema.getId());
        return cinema.getId();
    }

    @Override
    public void updateCinema(long cinemaId, CinemaRequestDTO requestDTO) {
        Cinema cinema = getCinemaById(cinemaId);
        if(!requestDTO.getName().isEmpty() && !requestDTO.getName().isBlank()){
            cinema.setName(requestDTO.getName());
        }
        if(!requestDTO.getAddress().isEmpty() && !requestDTO.getAddress().isBlank()){
            cinema.setAddress(requestDTO.getAddress());
        }
        cinemaRepository.save(cinema);
        log.info("Cinema id={} updated successfully",cinemaId);
    }

    @Override
    public void changeAddress(long cinemaId, String address) {
        Cinema cinema = getCinemaById(cinemaId);
        cinema.setAddress(address);
        cinemaRepository.save(cinema);
        log.info("Cinema's address changed");
    }

    @Override
    public void deleteCinema(long cinemaId) {
        cinemaRepository.deleteById(cinemaId);
        log.info("Cinema id={} has been deleted ",cinemaId);
    }

    @Override
    public Cinema getCinemaById(long cinemaId) {
        return cinemaRepository.findById(cinemaId).orElseThrow(()-> new ResourceNotFoundException("Cinema " +cinemaId + " not found"));
    }

    @Override
    public PaginationResponse<?> getAllCinemas(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(new Sort.Order(Sort.Direction.ASC,sortBy)));
        Page<Cinema> cinemaPage =  cinemaRepository.findAll(pageable);
        List<Cinema> response = cinemaPage.stream().map(cinema -> Cinema.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .build()).toList();
        return PaginationResponse.builder()
                .pageNo(pageNumber)
                .pageSize(pageSize)
                .totalItems(response.size())
                .data(response)
                .build();
    }
}
