package com.example.backend.stats;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/stats")
public class StatsController {

    public StatsController() {}

    @GetMapping("/tickets-by-movie")
    public ResponseEntity<List<MovieStatsDto>> getTicketsByMovie() {
        List<MovieStatsDto> stats = new ArrayList<>();
        stats.add(new MovieStatsDto("Diuna 2", 450));
        stats.add(new MovieStatsDto("Batman", 280));
        stats.add(new MovieStatsDto("Iron Man", 120));
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/tickets-by-employee")
    public ResponseEntity<List<EmployeeStatsDto>> getTicketsByEmployee() {
        List<EmployeeStatsDto> stats = new ArrayList<>();
        stats.add(new EmployeeStatsDto("Filip Sobieraj", 310));
        stats.add(new EmployeeStatsDto("Paweł Wdowik", 290));
        stats.add(new EmployeeStatsDto("Paweł Polak", 150));
        return ResponseEntity.ok(stats);
    }
}
