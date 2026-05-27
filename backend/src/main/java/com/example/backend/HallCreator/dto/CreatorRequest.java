package com.example.backend.HallCreator.dto;
import java.util.List;

public class CreatorRequest {
    private Long hallId;
    private List<SeatRequest> selectedSeats;
    public CreatorRequest() {}

    public Long getHallId() {return hallId; }
    public List<SeatRequest> getSelectedSeats() { return selectedSeats; }

    public void setHallId(Long hallId) { this.hallId = hallId; }
    public void setSelectedSeats(List<SeatRequest> selectedSeats) { this.selectedSeats = selectedSeats; }


}
