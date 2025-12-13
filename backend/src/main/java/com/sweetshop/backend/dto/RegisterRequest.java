package com.sweetshop.backend.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}
