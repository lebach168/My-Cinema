package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="seat")
public class Seat  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "row")
    private String row;

    @Column(name = "number")
    private int number;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private SeatType type;//standard, vip, sweetbox, deluxe

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(
            name = "room_id",
            nullable = false
    )
    private Room room;

}
