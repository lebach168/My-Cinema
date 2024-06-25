package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.StaffRequestDTO;
import com.example.MyCinema.dto.response.StaffDetailResponse;

public interface StaffService {
    StaffDetailResponse getDetailAccount(String staffId);
    String createAccount(StaffRequestDTO request);
    void updateStaffAccount(String staffId, StaffRequestDTO request);
    void deleteStaffAccount(String staffId);
}
