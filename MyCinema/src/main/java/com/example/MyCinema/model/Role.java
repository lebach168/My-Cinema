package com.example.MyCinema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    String name;
    @ManyToMany
    Set<Permission> permissions;
}
