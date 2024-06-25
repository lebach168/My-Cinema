package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.StaffRequestDTO;

public interface StaffService {
    long createAccount(StaffRequestDTO request);
    void updateStaffAccount(long staffId, StaffRequestDTO request);
    void deleteStaffAccount(long staffId);
}
