package com.example.backend.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    public final MovieRepository movieRepository;
    public final ScreeningRepository screeningRepository;

    public ScheduleController(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PostMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @PostMapping("/screenings")
    public ResponseEntity<?> addScreening(@RequestBody ScreeningRequest request) {

        Optional<Movie> movieOptional = movieRepository.findById(request.getMovieId());

        if(movieOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Movie not found");
        }

        Movie movie = movieOptional.get();

        LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDurationMinutes() + 30);

        List<Screening> conflicts = screeningRepository.findOverLappingScreenings(
                request.getRoomNumber(),
                request.getStartTime(),
                endTime
        );

        if(!conflicts.isEmpty()) {
            return ResponseEntity.badRequest().body("Room " + request.getRoomNumber() + "is reserved");
        }

        Screening screening = new Screening(movie, request.getRoomNumber(), request.getStartTime(), endTime);
        screeningRepository.save(screening);

        return ResponseEntity.ok("Success movie added to the schedule");
    }

    // czy nie lepiej wyswietlac tylko dostepne sale
    // jak dodajemy cos do schedukle to chcemy zaznaczyc zeby seans byl grany od kiedy do kiedy
    // czyli date jeszcze i nie wiem czestottliwosc czy cos albo zeby sie dalo wiecej godzin odrazu zaznaczyc
    // ta wersja jest za prosta chyba 

}
