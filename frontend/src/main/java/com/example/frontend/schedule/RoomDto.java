package com.example.frontend.schedule;

public class RoomDto {
    private int id;
    private String name;

    public RoomDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
