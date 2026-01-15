package com.application.travo.controller;
import com.application.travo.Service.AuthService;
import com.application.travo.dtos.LoginRequestDTO;
import com.application.travo.dtos.LoginResponseDTO;
import com.application.travo.dtos.RegisterRequestDTO;
import com.application.travo.dtos.RegisterResponseDTO;
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
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody RegisterRequestDTO request) {

        RegisterResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
