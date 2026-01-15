package com.application.travo.dtos;

import com.application.travo.Entity.Role;
import com.application.travo.Entity.UserEntity;
import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private final String token;
    private final Long id;
    private final String name;
    private final String email;
    private final Role role;

    public LoginResponseDTO(String token, UserEntity user) {
        this.token = token;
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
