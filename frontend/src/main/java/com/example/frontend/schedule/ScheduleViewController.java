package com.example.frontend.schedule;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;

@Getter
public class ScheduleViewController {

    @FXML
    private ComboBox<MovieDto> movieComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField timeField;

    @FXML
    private ComboBox<Integer> roomComboBox;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        messageLabel.setText("Loading movies from server...");
        loadMovies();

        movieComboBox.valueProperty().addListener((observable, oldValue, newValue) -> fetchAvailableRooms());
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> fetchAvailableRooms());
        timeField.textProperty().addListener((observable, oldValue, newValue) -> fetchAvailableRooms());
    }

    private void loadMovies() {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();

        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create("http://localhost:8080/api/schedule/movies"))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                Type listType = new com.google.gson.reflect.TypeToken<java.util.List<MovieDto>>() {
                }.getType();
                List<MovieDto> movies = gson.fromJson(response.body(), listType);

                javafx.application.Platform.runLater(() -> {
                    movieComboBox.getItems().clear();
                    movieComboBox.getItems().addAll(movies);
                    messageLabel.setText("Ready");
                    messageLabel.setStyle("-fx-text-fill: green;");
                });
            } else {
                javafx.application.Platform.runLater(() -> {
                    messageLabel.setText("Error: " + response.statusCode());
                    messageLabel.setStyle("-fx-text-fill: red;");
                });
            }
        }).exceptionally(e -> {
            javafx.application.Platform.runLater(() -> {
                messageLabel.setText("Nie można połączyć z serwerem!");
                messageLabel.setStyle("-fx-text-fill: red;");
            });
            return null;
        });
    }

    private void fetchAvailableRooms() {
        if (movieComboBox.getValue() == null || startDatePicker.getValue() == null || timeField.getText().length() < 5) {
            return;
        }

        try {
            java.time.LocalDate date = startDatePicker.getValue();
            java.time.LocalTime time = java.time.LocalTime.parse(timeField.getText());
            java.time.LocalDateTime startDateTime = java.time.LocalDateTime.of(date, time);
            Long movieId = movieComboBox.getValue().getId();

            String url = "http://localhost:8080/api/schedule/available-rooms?movieId=" + movieId + "&startTime=" + startDateTime;

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .GET()
                    .build();

            client.sendAsync(request, java.net.http.HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            com.google.gson.Gson gson = new com.google.gson.Gson();
                            java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<java.util.List<Integer>>(){}.getType();
                            java.util.List<Integer> rooms = gson.fromJson(response.body(), listType);

                            javafx.application.Platform.runLater(() -> {
                                roomComboBox.getItems().clear();
                                roomComboBox.getItems().addAll(rooms);
                                roomComboBox.setPromptText("Chose room (" + rooms.size() + " available)");
                            });
                        }
                    });
        } catch (Exception e) {
            // XD
        }
    }

    private void clearForm() {
        movieComboBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        timeField.clear();

        roomComboBox.getItems().clear();
        roomComboBox.setValue(null);
        roomComboBox.setPromptText("...");
    }


    // UWAGA TA METODA POTRZEBUJE JESZCZE POPRAWKI I SPOJRZENIA
    @FXML
    public void handleSave() {
        if (movieComboBox.getValue() == null || startDatePicker.getValue() == null ||
                timeField.getText().isEmpty() || roomComboBox.getValue() == null) {
            messageLabel.setText("fill all fields");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Long movieId = movieComboBox.getValue().getId();
            java.time.LocalDate startDate = startDatePicker.getValue();
            java.time.LocalDate endDate = endDatePicker.getValue();
            java.time.LocalTime time = java.time.LocalTime.parse(timeField.getText());
            Integer roomNumber = roomComboBox.getValue();

            com.google.gson.Gson gson = new com.google.gson.Gson();
            String jsonPayload;
            String targetUrl;
            java.util.Map<String, Object> requestData = new java.util.HashMap<>();

            if (endDate != null && endDate.isAfter(startDate)) {
                targetUrl = "http://localhost:8080/api/schedule/screenings/bulk";
                requestData.put("movieId", movieId);
                requestData.put("roomNumber", roomNumber);
                requestData.put("startDate", startDate.toString());
                requestData.put("endDate", endDate.toString());
                requestData.put("startTime", time.toString() + ":00");
            } else {
                targetUrl = "http://localhost:8080/api/schedule/screenings";
                java.time.LocalDateTime startDateTime = java.time.LocalDateTime.of(startDate, time);
                requestData.put("movieId", movieId);
                requestData.put("roomNumber", roomNumber);
                requestData.put("startTime", startDateTime.toString());
            }

            jsonPayload = gson.toJson(requestData);


            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(targetUrl))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            client.sendAsync(request, java.net.http.HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        javafx.application.Platform.runLater(() -> {

                            if (response.statusCode() == 200 || response.statusCode() == 201) {
                                messageLabel.setText("Harmonogram saved");
                                messageLabel.setStyle("-fx-text-fill: green;");
                                clearForm();
                            } else {
                                messageLabel.setText("not saved: " + response.statusCode());
                                messageLabel.setStyle("-fx-text-fill: red;");
                            }
                        });
                    }).exceptionally(e -> {
                        javafx.application.Platform.runLater(() -> {
                            messageLabel.setText("Can not connect with a sever");
                            messageLabel.setStyle("-fx-text-fill: red;");
                        });
                        return null;
                    });

        } catch (java.time.format.DateTimeParseException e) {
            messageLabel.setText("Bad form enter XX:YY");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

}
