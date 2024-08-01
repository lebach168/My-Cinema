package com.example.MyCinema.model;

import com.example.MyCinema.dto.response.SeatResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="seat")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seat extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_name")
    private String rowName;

    @Column(name = "number")
    private int number;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private SeatType type;//standard, vip, sweetbox, deluxe

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            nullable = false
    )
    private Room room;

    public String toStringName(){
        return  rowName+number;
    }
}
