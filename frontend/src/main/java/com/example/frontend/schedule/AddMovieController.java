package com.example.frontend.schedule;

import com.example.frontend.schedule.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddMovieController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField durationField;

    @FXML
    private Label statusLabel;

    private MovieService movieService;

    public AddMovieController() {}

    @FXML
    public void initialize() {
        this.movieService = new MovieService();
    }

    @FXML
    public void handleSave() {
        String title = titleField.getText();
        String genre = genreField.getText();
        String durationText = durationField.getText();

        if (title.isEmpty() || genre.isEmpty() || durationText.isEmpty()) {
            statusLabel.setText("Fill al fields");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int duration = Integer.parseInt(durationText);

            String response = movieService.addMovie(title, genre, duration);

            statusLabel.setText(response);

            if (response.startsWith("Success")) {
                statusLabel.setStyle("-fx-text-fill: green;");
                titleField.clear();
                genreField.clear();
                durationField.clear();
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Time has to be a number");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getGenreField() {
        return genreField;
    }

    public TextField getDurationField() {
        return durationField;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public MovieService getMovieService() {
        return movieService;
    }
}
