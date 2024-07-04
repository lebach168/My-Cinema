package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name="role")
    private String role;

}
