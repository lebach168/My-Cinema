package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="movie")
public class Movie extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;

    @Column(name = "genres")
    private String genres;

    @Column(name = "age_limit")
    private int ageLimit;

    @Column(name = "length")
    private int length;

    @Column(name = "description")
    private String description;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "language")
    private String language;
}
