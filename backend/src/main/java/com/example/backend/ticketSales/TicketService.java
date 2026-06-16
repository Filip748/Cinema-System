package com.example.backend.ticketSales;

import com.example.backend.repository.EmployeeRepository;
import com.example.backend.schedule.Screening;
import com.example.backend.HallCreator.model.Seat;
import com.example.backend.schedule.ScreeningRepository;
import com.example.backend.HallCreator.repository.SeatRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class TicketService {
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final EmailService emailService;
    private final EmployeeRepository employeeRepository;

    public TicketService(ScreeningRepository screeningRepository, TicketRepository ticketRepository, SeatRepository seatRepository, EmailService emailService, EmployeeRepository employeeRepository) {
        this.screeningRepository = screeningRepository;
        this.ticketRepository = ticketRepository ;
        this.seatRepository = seatRepository;
        this.emailService = emailService;
        this.employeeRepository = employeeRepository;
    }

    public List<SeatDto> getAvailableSeats(Long screeningId) {
        List<Ticket> soldTickets = ticketRepository.findByScreening_Id(screeningId);
        List<Long> takenSeatsIds = soldTickets.stream().map(ticket -> ticket.getSeat().getId()).toList();

        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new RuntimeException("Screening not found"));
        Long cinemaHallId = screening.getCinemaHall().getId();

        List<Seat> realSeats = seatRepository.findByCinemaHallId(cinemaHallId);
        List<SeatDto> toShowSeats = new ArrayList<>();
        for(Seat seat : realSeats){
            boolean isTaken = takenSeatsIds.contains(seat.getId());
            toShowSeats.add(new SeatDto(seat.getId(), seat.getRowNum(), seat.getSeatNum(), !isTaken));
        }
        return toShowSeats;
    }

    //zakup biletu i zebranie info do maila
    public String buyTicket(TicketDto request) {
        Screening screening = screeningRepository.findById(request.getScreeningId()).orElseThrow(() -> new RuntimeException("Screening not found"));
        Employee employee = null;
        if (request.getEmployeeId() != null) {
            employee = employeeRepository.findById(request.getEmployeeId()).orElse(null);
        }
        StringBuilder seatsInfo = new StringBuilder();
        String movieTitle = screening.getMovie().getTitle();
        String roomName = screening.getCinemaHall().getName();


        for(Long seatId : request.getSeatIds()){
            Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
            Ticket ticket = new Ticket(screening, seat, employee, request.getCustomerEmail());
            ticketRepository.save(ticket);

            seatsInfo.append("- Row ").append(seat.getRowNum()).append(", Seat ").append(seat.getSeatNum()).append("\n");
        }

        if (request.getCustomerEmail() != null && !request.getCustomerEmail().trim().isEmpty()) {
            try {
                emailService.sendTicketConfirmation(request.getCustomerEmail(), movieTitle, roomName, seatsInfo.toString());
            } catch (Exception e) {
                System.err.println("Error with mail" + e.getMessage());
            }
        }
        return request.getSeatIds().size() + " tickets has been purchased";
    }
}
