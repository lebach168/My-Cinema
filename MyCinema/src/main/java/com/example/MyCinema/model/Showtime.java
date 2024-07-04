package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="showtime")
public class Showtime extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @OneToOne
    @JoinColumn(name ="room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name ="movie_id")
    private Movie movie;
}
