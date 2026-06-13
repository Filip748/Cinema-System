package com.example.frontend.login;

public class AuthResponseDto {

    private String message;
    private String role;
    private Long employeeId;
    private String username;

    public AuthResponseDto() {}
    public AuthResponseDto(String message, String role, Long employeeId, String username) {
        this.message = message;
        this.role = role;
        this.employeeId = employeeId;
        this.username = username;
    }

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
    public Long getEmployeeId() { return employeeId; }
    public String getUsername() { return username; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public void setUsername(String username) { this.username = username; }
}