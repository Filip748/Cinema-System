package com.example.backend.stats;

public class MovieStatsDto {
    private String movieTitle;
    private long ticketsSold;

    public MovieStatsDto() {}

    public MovieStatsDto(String movieTitle, long ticketsSold) {
        this.movieTitle = movieTitle;
        this.ticketsSold = ticketsSold;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public long getTicketsSold() {
        return ticketsSold;
    }
}