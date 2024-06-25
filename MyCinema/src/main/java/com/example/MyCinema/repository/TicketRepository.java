package com.example.MyCinema.repository;

import com.example.MyCinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM Ticket t where t.customer_id =:userId")
    List<Ticket> findAllByCustomer(String userId);
}
