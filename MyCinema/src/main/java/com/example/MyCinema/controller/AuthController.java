package com.example.MyCinema.controller;

import com.example.MyCinema.dto.ApiResponse;
import com.example.MyCinema.dto.request.AuthenticationRequest;
import com.example.MyCinema.dto.request.UserRequestDTO;
import com.example.MyCinema.dto.response.AuthenticationResponse;
import com.example.MyCinema.service.AuthenticationService;
import com.example.MyCinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/login")
    ApiResponse<?> authenticate(@RequestBody AuthenticationRequest request) {
        Object result = authenticationService.authenticate(request);
        return new ApiResponse<>(HttpStatus.OK,"authenticated",result);
    }

    //create user <=> sign up
    @PostMapping(path = "/register")
    public ApiResponse<?> createNewUser(@Valid @RequestBody UserRequestDTO userDTO){
        Long id = userService.createNewAccount(userDTO);
        return new ApiResponse<>(HttpStatus.ACCEPTED,"register successfully",id);
    }
}
