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

        // check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        // create new user
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN); // or request.getRole() if coming from UI

        UserEntity savedUser = userRepository.save(user);

        return new RegisterResponseDTO(savedUser);
    }

    public UserEntity createUserIfNotExists(String phone) {

        return userRepository.findByPhone(phone)
                .orElseGet(() -> {
                    UserEntity user = new UserEntity();
                    user.setPhone(phone);
                    user.setPhoneVerified(true);
                    user.setRole(Role.GUIDE); // or USER → GUIDE later
                    return userRepository.save(user);
                });
    }
}
