package it.epicode.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
