package com.example.MyCinema.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class StaffDetailResponse implements Serializable {
    String name;
    String role;
}
