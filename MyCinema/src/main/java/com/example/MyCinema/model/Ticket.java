package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ticket")
public class Ticket extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
    @Column(name = "price")
    private long price;
    @OneToOne
    @JoinColumn(name = "discount_code", referencedColumnName = "code")
    @Transient
    private Discount discount;

}
