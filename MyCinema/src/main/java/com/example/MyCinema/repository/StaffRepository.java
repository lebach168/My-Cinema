package com.example.MyCinema.repository;

import com.example.MyCinema.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Employee,Long> {
}
