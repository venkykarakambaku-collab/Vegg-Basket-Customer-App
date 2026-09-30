package com.veggiebasket.release_manager_backend.service;

import com.veggiebasket.release_manager_backend.dto.LoginRequest;
import com.veggiebasket.release_manager_backend.dto.LoginResponse;
import com.veggiebasket.release_manager_backend.entity.Developer;
import com.veggiebasket.release_manager_backend.repository.DeveloperRepository;
import com.veggiebasket.release_manager_backend.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            DeveloperRepository developerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.developerRepository = developerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Developer developer = developerRepository
                .findByEmailAndActiveTrue(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                developer.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                developer.getEmail()
        );

        return new LoginResponse(
                token,
                developer.getEmail()
        );
    }
}