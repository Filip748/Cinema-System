package com.example.frontend;

public class User {
    private static User instance;

    private Long employeeId;
    private String username;
    private String role;

    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void login(Long employeeId, String username, String role) {
        this.employeeId = employeeId;
        this.username = username;
        this.role = role;
    }

    public void logout() {
        this.employeeId = null;
        this.username = null;
        this.role = null;
    }
    public Long getEmployeeId() { return employeeId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}