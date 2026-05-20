package com.example.frontend.creator.dto;

public class SeatRequest {
    private int row;
    private int seat;

    public SeatRequest(int row, int seat) {
        this.row = row;
        this.seat = seat;
    }

    public int getRow() { return row; }
    public int getSeat() { return seat; }

    public void setRow(int row) { this.row = row; }
    public void setSeat(int seat) { this.seat = seat; }
}
