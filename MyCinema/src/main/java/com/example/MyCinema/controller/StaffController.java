package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.StaffRequestDTO;
import com.example.MyCinema.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/staffs")
@Valid
public class StaffController {
    private final StaffService staffService;
    //create
    @PostMapping(path = "")
    ApiResponse<?> createStaffAccount(@Valid @RequestBody StaffRequestDTO staffRequest){
        long response = staffService.createAccount(staffRequest);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"ok",response);
    }

    //update
    @PutMapping(path = "/{staffId}")
    ApiResponse<?> updateStaffAccount(@PathVariable() long staffId,
                                    @Valid @RequestBody StaffRequestDTO staffRequest){
        staffService.updateStaffAccount(staffId, staffRequest);
        return new ApiResponse<>(HttpStatus.OK,"");
    }
    //delete
    @DeleteMapping(path = "/{staffId}")
    ApiResponse<?> deleteStaffAccount(@PathVariable(value = "staffId") long staffId){
        staffService.deleteStaffAccount(staffId);
        return new ApiResponse<>(HttpStatus.OK,"");
    }

}
