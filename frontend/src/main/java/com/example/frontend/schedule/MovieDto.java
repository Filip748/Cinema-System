package com.example.frontend.schedule;

import lombok.Getter;

@Getter
public class MovieDto {
    private Long id;
    private String title;
    private String genre;
    private int durationMinutes;

    public Long getId() {return id; }
    @Override
    public String toString() {
        return title + " (" + durationMinutes + " min)";
    }
}
