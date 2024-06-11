package com.example.MyCinema.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    String token;
}
