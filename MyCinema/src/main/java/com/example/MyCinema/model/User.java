package com.example.MyCinema.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private  String password;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private LocalDate dob;

    @Transient
    private int age;
    public int getAge(){
        LocalDate currentDate = LocalDate.now();
        this.age = (int) ChronoUnit.YEARS.between(this.dob, currentDate);
        return this.age;
    }

    @Column(name = "point")
    private int point;

    @Column(name = "level")
    private String level;
}
