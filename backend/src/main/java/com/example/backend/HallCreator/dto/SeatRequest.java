package com.example.backend.HallCreator.dto;

public class SeatRequest {
    private int row;
    private int seat;

    public SeatRequest() {}

    public int getRow() { return row; }
    public int getSeat() { return seat; }


    public void setRows(int row) { this.row = row; }
    public void setSeats(int seat) { this.seat = seat; }

}
