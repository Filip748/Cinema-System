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
        // Przygotowanie mocków, aby nie łączyć się z prawdziwą bazą danych
        this.movieRepository = Mockito.mock(MovieRepository.class);
        this.screeningRepository = Mockito.mock(ScreeningRepository.class);
        this.cinemaHallRepository = Mockito.mock(CinemaHallRepository.class);

        // Wstrzyknięcie zmockowanych repozytoriów do testowanego kontrolera
        this.scheduleController = new ScheduleController(
                getMovieRepository(),
                getScreeningRepository(),
                getCinemaHallRepository()
        );
    }

    @Test
    public void testAddMovieSuccess() {
        // 1. Arrange (Przygotowanie danych)

        // Obiekt symulujący dane przychodzące z frontendu (bez ID)
        Movie inputMovie = new Movie();
        inputMovie.setTitle("TestFilm");
        inputMovie.setDurationMinutes(100);

        // Obiekt symulujący odpowiedź z bazy danych (z nadanym automatycznie ID)
        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle("TestFilm");
        savedMovie.setDurationMinutes(100);

        // Uczymy mocka, jak ma się zachować:
        // "Kiedy ktoś wywoła metodę save() z dowolnym obiektem Movie, zwróć nasz obiekt savedMovie"
        when(getMovieRepository().save(any(Movie.class))).thenReturn(savedMovie);

        // 2. Act (Wykonanie testowanej logiki)
        ResponseEntity<?> response = getScheduleController().addMovie(inputMovie);

        // 3. Assert (Sprawdzenie wyników)

        // Weryfikujemy, czy kontroler zwrócił kod 200 OK
        assertEquals(200, response.getStatusCode().value());

        // Weryfikujemy, czy w ciele odpowiedzi cokolwiek jest
        assertNotNull(response.getBody());

        // Rzutujemy ciało odpowiedzi na obiekt Movie i sprawdzamy jego parametry
        Movie responseBody = (Movie) response.getBody();
        assertEquals(1L, responseBody.getId());
        assertEquals("TestFilm", responseBody.getTitle());
        assertEquals(100, responseBody.getDurationMinutes());
    }
}
