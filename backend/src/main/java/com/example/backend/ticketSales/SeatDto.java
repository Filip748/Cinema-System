package com.example.backend.ticketSales;

public class SeatDto {
    private Long id;
    private int row;
    private int seat;
    private boolean isAvailable;

    public SeatDto(Long id, int row, int seat, boolean isAvailable) {
        this.id = id;
        this.row = row;
        this.seat = seat;
        this.isAvailable = isAvailable;
    }
    public Long getId() { return id; }
    public int getRow() { return row; }
    public int getSeat() { return seat; }
    public boolean isAvailable() { return isAvailable; }

    public void setId(Long id) { this.id = id; }
    public void setRow(int row) { this.row = row; }
    public void setSeat(int seat) { this.seat = seat; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

}
