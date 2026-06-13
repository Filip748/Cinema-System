package com.example.backend.schedule;

import com.example.backend.HallCreator.repository.CinemaHallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ScheduleControllerTest {

    private MovieRepository movieRepository;
    private ScreeningRepository screeningRepository;
    private CinemaHallRepository cinemaHallRepository;
    private ScheduleController scheduleController;

    public ScheduleControllerTest() {}

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public ScreeningRepository getScreeningRepository() {
        return screeningRepository;
    }

    public CinemaHallRepository getCinemaHallRepository() {
        return cinemaHallRepository;
    }

    public ScheduleController getScheduleController() {
        return scheduleController;
    }

    @BeforeEach
    public void setUp() {
        this.movieRepository = Mockito.mock(MovieRepository.class);
        this.screeningRepository = Mockito.mock(ScreeningRepository.class);
        this.cinemaHallRepository = Mockito.mock(CinemaHallRepository.class);

        this.scheduleController = new ScheduleController(
                getMovieRepository(),
                getScreeningRepository(),
                getCinemaHallRepository()
        );
    }

    @Test
    public void testAddMovieSuccess() {
        Movie inputMovie = new Movie();
        inputMovie.setTitle("TestFilm");
        inputMovie.setDurationMinutes(100);

        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle("TestFilm");
        savedMovie.setDurationMinutes(100);

        when(getMovieRepository().save(any(Movie.class))).thenReturn(savedMovie);

        ResponseEntity<?> response = getScheduleController().addMovie(inputMovie);


        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());

        Movie responseBody = (Movie) response.getBody();
        assertEquals(1L, responseBody.getId());
        assertEquals("TestFilm", responseBody.getTitle());
        assertEquals(100, responseBody.getDurationMinutes());
    }

    @Test
    public void testAddScreeningSuccess() {
        // 1. Arrange (Przygotowanie danych wejściowych)
        ScreeningRequest request = new ScreeningRequest();
        request.setMovieId(1L);
        request.setCinemaHallId(1L);
        request.setStartTime(java.time.LocalDateTime.of(2026, 12, 10, 15, 0));

        // Tworzymy atrapy obiektów z bazy
        Movie mockMovie = new Movie();
        mockMovie.setId(1L);
        mockMovie.setTitle("Incepcja");
        mockMovie.setDurationMinutes(148);

        com.example.backend.HallCreator.model.CinemaHall mockHall = new com.example.backend.HallCreator.model.CinemaHall();
        mockHall.setId(1L);
        mockHall.setName("Sala VIP");

        Screening savedScreening = new Screening();
        savedScreening.setId(100L);
        savedScreening.setMovie(mockMovie);
        savedScreening.setCinemaHall(mockHall);
        savedScreening.setStartTime(request.getStartTime());


        when(getMovieRepository().findById(1L)).thenReturn(java.util.Optional.of(mockMovie));

        when(getCinemaHallRepository().findById(1L)).thenReturn(java.util.Optional.of(mockHall));

        when(getScreeningRepository().findOverLappingScreenings(
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any()
        )).thenReturn(java.util.Collections.emptyList());

        when(getScreeningRepository().save(org.mockito.ArgumentMatchers.any(Screening.class))).thenReturn(savedScreening);

        ResponseEntity<?> response = getScheduleController().addScreening(request);

        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
    }
}
