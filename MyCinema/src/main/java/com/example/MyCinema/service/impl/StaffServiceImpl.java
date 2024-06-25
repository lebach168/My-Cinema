package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.StaffRequestDTO;
import com.example.MyCinema.exception.DataAlreadyExistsException;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Staff;
import com.example.MyCinema.repository.StaffRepository;
import com.example.MyCinema.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public long createAccount(StaffRequestDTO request) {
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
    public void updateStaffAccount(long staffId, StaffRequestDTO request) {
        Staff staff = getStaffById(staffId);
        staff.setName(request.getName());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setRole(request.getRole());
        staffRepository.save(staff);
    }

    @Override
    public void deleteStaffAccount(long staffId) {
        staffRepository.deleteById(staffId);
    }
    public Staff getStaffById(long staffId){
        return staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("staff not found"));
    }
}
