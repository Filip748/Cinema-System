package com.example.backend.HallCreator.model;
import jakarta.persistence.*;


@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "row_num")
    private int rowNumber;
    @Column(name = "seat_num")
    private int seatNumber;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private CinemaHall cinemaHall;

    public Seat() {}
    public Seat(int rowNumber, int seatNumber, CinemaHall cinemaHall) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.cinemaHall = cinemaHall;
    }

    public Long getId() { return id; }
    public int getRowNum() { return rowNumber; }
    public int getSeatNum() { return seatNumber; }
    public CinemaHall getCinemaHall() { return cinemaHall; }

    public void setId(Long id) { this.id = id; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public void setCinemaHall(CinemaHall cinemaHall) { this.cinemaHall = cinemaHall; }

}
