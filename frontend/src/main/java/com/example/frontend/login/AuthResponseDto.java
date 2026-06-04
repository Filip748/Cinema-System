package com.example.frontend.login;

public class AuthResponseDto {

    private String message;
    private String role;

    public AuthResponseDto() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}