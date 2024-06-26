package com.example.MyCinema.repository;

import com.example.MyCinema.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {

    Optional<Staff> findByName(String name);
}
