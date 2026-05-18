package com.example.backend.schedule;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.example.backend.HallCreator.model.CinemaHall;
import com.example.backend.HallCreator.repository.CinemaHallRepository;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final CinemaHallRepository cinemaHallRepository;

    public ScheduleController(MovieRepository movieRepository, ScreeningRepository screeningRepository, CinemaHallRepository cinemaHallRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public ScreeningRepository getScreeningRepository() {
        return screeningRepository;
    }

    public CinemaHallRepository getCinemaHallRepository() {
        return cinemaHallRepository;
    }
    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @GetMapping("/movies")
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

    @PostMapping("/screenings/bulk")
    public ResponseEntity<?> addBulkScreenings(@RequestBody BulkScreeningRequest request) {

        Optional<Movie> movieOptional = movieRepository.findById(request.getMovieId());
        if(movieOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("movie not found");
        }

        Movie movie = movieOptional.get();
        List<Screening> validScreenings = new ArrayList<>();
        List<String> skippedDates = new ArrayList<>();

        LocalDate currentDate = request.getStartDate();
        while(!currentDate.isAfter(request.getEndDate())) {
            LocalDateTime startDateTime = LocalDateTime.of(currentDate, request.getStartTime());
            LocalDateTime endaDateTime = startDateTime.plusMinutes((movie.getDurationMinutes()));

            List<Screening> conflicts = screeningRepository.findOverLappingScreenings(
                    request.getRoomNumber(),
                    startDateTime,
                    endaDateTime
            );

            if(conflicts.isEmpty()) {
                validScreenings.add(new Screening(movie, request.getRoomNumber(), startDateTime, endaDateTime));
            } else {
                skippedDates.add(currentDate.toString());
            }

            currentDate = currentDate.plusDays(1);
        }

        if(!validScreenings.isEmpty()) {
            screeningRepository.saveAll(validScreenings);
        }

        if(skippedDates.isEmpty()) {
            return ResponseEntity.ok("Success all screenings have been added");
        } else {
            String skippedMsg = String.join(", ", skippedDates);
            return ResponseEntity.ok("Screenings have benn added, but we skip these dates because room is busy");
        }
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<?> getAvailableRooms(@RequestParam Long movieId, @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {

        Optional<Movie> movieOptional = getMovieRepository().findById(movieId);
        if(movieOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("movie not found");
        }
        Movie movie = movieOptional.get();

        LocalDateTime endTime = startTime.plusMinutes(movie.getDurationMinutes());

        List<Integer> occupiedRooms = getScreeningRepository().findOccupiedRooms(startTime, endTime);
        List<CinemaHall> hallsFromDb = getCinemaHallRepository().findAll();
        List<RoomDto> availableRooms = new ArrayList<>();

        for(CinemaHall hall : hallsFromDb) {
            int hallId = hall.getId().intValue();
            if(!occupiedRooms.contains(hallId)) {
                availableRooms.add(new RoomDto(hallId, hall.getName()));
            }
        }

        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> getAllScreenings() {
        return ResponseEntity.ok(screeningRepository.findAll());
    }

    public static class RoomDto {
        private int id;
        private String name;

        public RoomDto() {}

        public RoomDto(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

