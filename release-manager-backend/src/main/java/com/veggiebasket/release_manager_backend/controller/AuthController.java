package com.veggiebasket.release_manager_backend.controller;

import com.veggiebasket.release_manager_backend.dto.LoginRequest;
import com.veggiebasket.release_manager_backend.dto.LoginResponse;
import com.veggiebasket.release_manager_backend.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}