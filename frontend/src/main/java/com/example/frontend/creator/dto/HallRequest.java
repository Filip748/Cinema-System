package com.example.frontend.creator.dto;

public class HallRequest {
    private String name;

    public HallRequest(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
