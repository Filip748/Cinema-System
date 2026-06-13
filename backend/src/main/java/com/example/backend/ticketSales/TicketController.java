package com.example.backend.ticketSales;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @GetMapping("/screening/{screeningId}/seats")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long screeningId) {
        return ResponseEntity.ok(ticketService.getAvailableSeats(screeningId));
    }
    @PostMapping("/buy")
    public ResponseEntity<String> buyTicket(@RequestBody TicketDto request) {
        return ResponseEntity.ok(ticketService.buyTicket(request));
    }

}
