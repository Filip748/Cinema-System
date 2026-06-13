package com.example.backend.ticketSales;

import com.example.backend.HallCreator.model.Seat;
import com.example.backend.HallCreator.model.CinemaHall;
import com.example.backend.schedule.Screening;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    private String customerEmail;

    public Ticket() {}
    public Ticket(Screening screening, Seat seat, String customerEmail) {
        this.screening = screening;
        this.seat = seat;
        this.customerEmail = customerEmail;
    }


    public Long getId() { return id; }
    public Screening getScreening() { return screening; }
    public Seat getSeat() { return seat; }
    public String getCustomerEmail() { return customerEmail; }

    public void setId(Long id) { this.id = id; }
    public void setScreening(Screening screening) { this.screening = screening; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}
