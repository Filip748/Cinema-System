package com.example.backend.schedule;

import java.time.LocalDateTime;

public class ScreeningRequest {
    private Long movieId;
    private Long cinemaHallId;
    private LocalDateTime startTime;

    public ScreeningRequest() {}

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long roomNumber) {
        this.cinemaHallId = roomNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}