package com.example.frontend.statistics;

public class MovieStatsDto {
    private String movieTitle;
    private long ticketsSold;

    public MovieStatsDto() {}

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
