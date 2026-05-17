package com.example.frontend.schedule;

public class MovieRequest {
    private String title;
    private String genre;
    private int durationMinutes;

    public MovieRequest(String title, String genre, int durationMinutes) {
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() { return title; }
    public String getGenre() { return  genre; }
    public int getDurationMinutes() { return durationMinutes; }

}
