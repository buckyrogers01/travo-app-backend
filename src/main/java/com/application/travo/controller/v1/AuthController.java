package com.application.travo.controller.v1;

import com.application.travo.Service.AuthService;
import com.application.travo.dtos.LoginRequestDTO;
import com.application.travo.dtos.LoginResponseDTO;
import com.application.travo.dtos.RegisterRequestDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // REGISTER
//    @PostMapping("/register")
//    public ResponseEntity<?> register(
//            @RequestBody RegisterRequestDTO request) {
//
//        authService.register(request);
//        return ResponseEntity.ok("User registered successfully");
//    }
}
