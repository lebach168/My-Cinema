package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.StaffRequestDTO;
import com.example.MyCinema.dto.response.StaffDetailResponse;
import com.example.MyCinema.exception.DataAlreadyExistsException;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Staff;
import com.example.MyCinema.repository.StaffRepository;
import com.example.MyCinema.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("#staffId ==authentication.name || hasRole('ADMIN')")
    public StaffDetailResponse getDetailAccount(String staffId) {
        Staff staff = getStaffById(staffId);
        return StaffDetailResponse.builder()
                .name(staff.getName())
                .role(staff.getRole())
                .build();
    }

    @Override
    public String createAccount(StaffRequestDTO request) {
        Optional<Staff> existedStaff = staffRepository.findByName(request.getName());
        if(existedStaff.isPresent()) throw new DataAlreadyExistsException("name has been already used");
        Staff staff = Staff.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return staffRepository.save(staff).getId();
    }

    @Override
    public void updateStaffAccount(String staffId, StaffRequestDTO request) {
        Staff staff = getStaffById(staffId);
        staff.setName(request.getName());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setRole(request.getRole());
        staffRepository.save(staff);
    }

    @Override
    public void deleteStaffAccount(String staffId) {
        staffRepository.deleteById(staffId);
    }
    public Staff getStaffById(String staffId){
        return staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staff not found"));
    }
}
