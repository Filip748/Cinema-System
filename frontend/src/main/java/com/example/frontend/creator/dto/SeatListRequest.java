package com.example.frontend.creator.dto;
import java.util.List;

public class SeatListRequest {
    private List<SeatRequest> selectedSeats;
    private Long hallId;

    public SeatListRequest(List<SeatRequest> selectedSeats, Long hallId) {
        this.selectedSeats = selectedSeats;
        this.hallId = hallId;
    }
    public List<SeatRequest> getSelectedSeats() { return selectedSeats; }
    public Long getHallId() { return hallId; }

    public void setSelectedSeats(List<SeatRequest> selectedSeats) { this.selectedSeats = selectedSeats; }
    public void setHallId(Long hallId) { this.hallId = hallId; }
}
