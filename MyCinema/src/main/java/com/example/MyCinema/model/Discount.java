package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="discount")
public class Discount extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "event")
    private String event;

    @Column(name = "percent")
    private int percent;

    @Column(name = "code")
    private String code;

}
