package com.example.backend.schedule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DatabaseSeeder(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {

            Movie m1 = new Movie();
            m1.setTitle("Batman");
            m1.setGenre("Akcja");
            m1.setDurationMinutes(120);

            Movie m2 = new Movie();
            m2.setTitle("Diuna: Część Druga");
            m2.setGenre("Sci-Fi");
            m2.setDurationMinutes(166);

            Movie m3 = new Movie();
            m3.setTitle("Oppenheimer");
            m3.setGenre("Dramat");
            m3.setDurationMinutes(180);

            movieRepository.saveAll(List.of(m1, m2, m3));

        }
    }
}