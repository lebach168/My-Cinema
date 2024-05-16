package com.example.MyCinema.repository;

import com.example.MyCinema.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository  extends JpaRepository<Discount,Long> {
}
