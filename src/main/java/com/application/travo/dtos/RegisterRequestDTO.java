package com.application.travo.dtos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role; // USER / GUIDE / ADMIN (optional)
}
