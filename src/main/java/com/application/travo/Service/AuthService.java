package com.application.travo.Service;

import com.application.travo.Entity.Role;
import com.application.travo.Entity.UserEntity;
import com.application.travo.Repo.UserRepository;
import com.application.travo.dtos.LoginRequestDTO;
import com.application.travo.dtos.LoginResponseDTO;
import com.application.travo.Utility.JwtUtil;
import com.application.travo.dtos.RegisterRequestDTO;
import com.application.travo.dtos.RegisterResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(token, user);
    }
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Role role = Role.USER;
        if (request.getRole() != null) {
            role = Role.valueOf(request.getRole().toUpperCase());
        }

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        return new RegisterResponseDTO(user);
    }
}
