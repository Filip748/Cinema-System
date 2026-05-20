package com.example.frontend.schedule;

public class ScreeningItem {
    private String date;
    private String time;
    private String movieTitle;
    private String hallName;

    public ScreeningItem(String date, String time, String movieTitle, String hallName) {
        this.date = date;
        this.time = time;
        this.movieTitle = movieTitle;
        this.hallName = hallName;
    }

    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getMovieTitle() { return movieTitle; }
    public String getHallName() { return hallName; }
}
