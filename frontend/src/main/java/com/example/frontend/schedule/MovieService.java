package com.example.frontend.schedule;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieService {
    private static final String BACKEND_URL = "http://localhost:8080/api/movies";
    private final HttpClient httpClient;
    private final Gson gson;

    public MovieService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String addMovie(String title, String genre, int durationMinutes) {
        try {
            MovieRequest requestData = new MovieRequest(title, genre, durationMinutes);
            String jsonBody = gson.toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return "Success: " + response.body();
            } else {
                return "Server error: " + response.statusCode();
            }
        } catch (Exception e) {
            return "No connection with a sever";
        }
    }
}