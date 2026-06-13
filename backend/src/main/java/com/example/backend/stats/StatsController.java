package com.example.backend.stats;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.ticketSales.TicketRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.model.Employee;
import com.example.backend.schedule.MovieRepository;
import com.example.backend.schedule.Movie;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/stats")
public class StatsController {

    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;
    private final MovieRepository movieRepository;

    public StatsController(TicketRepository ticketRepository, EmployeeRepository employeeRepository, MovieRepository movieRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.movieRepository = movieRepository;
    }

    //podmienione sztywne dane na obecne info z bazy pobierane i nastepnie zliczane.
    @GetMapping("/tickets-by-movie")
    public ResponseEntity<List<MovieStatsDto>> getTicketsByMovie() {
        List<MovieStatsDto> stats = new ArrayList<>();
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            long soldTickets = ticketRepository.countByScreening_Movie(movie);
            stats.add(new MovieStatsDto(movie.getTitle(), soldTickets));
        }

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/tickets-by-employee")
    public ResponseEntity<List<EmployeeStatsDto>> getTicketsByEmployee() {
        List<EmployeeStatsDto> stats = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            long soldTickets = ticketRepository.countByEmployee(employee);
            stats.add(new EmployeeStatsDto(employee.getUsername(), soldTickets));
        }

        return ResponseEntity.ok(stats);
    }
}
