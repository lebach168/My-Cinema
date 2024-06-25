package com.example.MyCinema.service.impl;

import com.example.MyCinema.dto.request.AuthenticationRequest;
import com.example.MyCinema.dto.response.AuthenticationResponse;
import com.example.MyCinema.exception.AuthenticationFailedException;
import com.example.MyCinema.exception.ResourceNotFoundException;
import com.example.MyCinema.model.Staff;
import com.example.MyCinema.model.User;
import com.example.MyCinema.repository.StaffRepository;
import com.example.MyCinema.repository.UserRepository;
import com.example.MyCinema.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.*;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SECRET_KEY_SIGNER;

    @NonFinal
    @Value("${jwt.validDuration}")
    int DURATION_TIME;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AuthenticationFailedException("Email or password is incorrect");
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public boolean introspect(String token) {
        return false;
    }

    @Override
    public AuthenticationResponse staffAuthenticate(AuthenticationRequest request) {
        Staff staff = staffRepository.findByName(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("account not found"));
        if(!passwordEncoder.matches(request.getPassword(), staff.getPassword())){
            throw new AuthenticationFailedException("Email or password is incorrect");
        }
        String token = generateToken(staff);
        return AuthenticationResponse.builder().token(token).build();
    }

    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .issuer("my-cinema")
                .issueTime(new Date())
                .subject(String.valueOf(user.getId()))
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
    public String generateToken(Staff staff){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .issuer("my-cinema")
                .issueTime(new Date())
                .subject(String.valueOf(staff.getId()))
                .expirationTime(new Date(Instant.now().plusSeconds(DURATION_TIME* 24L).toEpochMilli()))
                .claim("scope",staff.getRole())
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
    public boolean verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SECRET_KEY_SIGNER.getBytes());

        SignedJWT signedJWT =SignedJWT.parse(token);
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return signedJWT.verify(verifier) && expiredTime.after(new Date());
    }

}
