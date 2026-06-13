package com.example.backend.dto;

public class AuthResponse {
    private String message;
    private String role;
    private Long employeeId;
    private String employeeName;

    public AuthResponse(String message, String role, Long employeeId, String employeeName) {
        this.message = message;
        this.role = role;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
    public Long getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
}