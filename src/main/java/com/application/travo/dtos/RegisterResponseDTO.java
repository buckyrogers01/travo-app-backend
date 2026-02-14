package com.application.travo.dtos;

import com.application.travo.Entity.UserEntity;
import com.application.travo.Entity.Role;
import lombok.Getter;

@Getter
public class RegisterResponseDTO {

    private final Long id;
    private final String name;
    private final String email;
    private final Role role;
    private final String phone;

    public RegisterResponseDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.phone = user.getPhone();
    }
}