package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.AuthenticationRequest;
import com.example.MyCinema.dto.response.AuthenticationResponse;
import com.example.MyCinema.exception.AuthenticationFailedException;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.User;
import com.example.MyCinema.repository.UserRepository;
import com.example.MyCinema.service.AuthenticationService;
import com.example.MyCinema.service.UserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SECRET_KEY_SIGNER;

    @NonFinal
    @Value("${jwt.validDuration}")
    int DURATION_TIME;
    @Override
    public Object authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AuthenticationFailedException("Email or password is incorrect");
        }
        String token = generateToken(user);
        return new AuthenticationResponse(token);
    }

    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .issuer("my-cinema")
                .expirationTime(new Date())
                .subject(user.getEmail())
                .expirationTime(new Date(Instant.now().plusSeconds(DURATION_TIME).toEpochMilli()))
                .claim("scope","USER")
                .build();
        JWSObject jws = new JWSObject(header, new Payload(payload.toJSONObject()));
        try {
            jws.sign(new MACSigner(SECRET_KEY_SIGNER.getBytes()));
            return jws.serialize();
        } catch (JOSEException e) {
            log.error("Failed to create token", e);
            throw new RuntimeException(e);
        }
    }
}
