package com.example.frontend.schedule;

public class ScreeningDto {

    private Long id;
    private MovieDto movie;
    private int roomNumber;
    private String startTime;
    private String endTime;

    public ScreeningDto() {}

    public Long getId() { return id; }
    public MovieDto getMovie() { return movie; }
    public int getRoomNumber() { return roomNumber; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public void setId(Long id) { this.id = id; }
    public void setMovie(MovieDto movie) { this.movie = movie; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}