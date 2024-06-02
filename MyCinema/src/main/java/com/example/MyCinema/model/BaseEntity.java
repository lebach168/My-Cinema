package com.example.MyCinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @JsonIgnore
    @Column(name ="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name ="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
