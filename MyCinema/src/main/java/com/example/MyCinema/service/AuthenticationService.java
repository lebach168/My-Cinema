package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.AuthenticationRequest;
import com.example.MyCinema.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    boolean introspect(String token);

    AuthenticationResponse staffAuthenticate(AuthenticationRequest request);
}
