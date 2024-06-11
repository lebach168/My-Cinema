package com.example.MyCinema.service;

import com.example.MyCinema.dto.request.AuthenticationRequest;

public interface AuthenticationService {

    Object authenticate(AuthenticationRequest request);
}
