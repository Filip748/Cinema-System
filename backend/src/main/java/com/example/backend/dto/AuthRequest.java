package com.example.backend.dto;

public class AuthRequest {
    private String username;
    private String password;

    AuthRequest() {};

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

}
