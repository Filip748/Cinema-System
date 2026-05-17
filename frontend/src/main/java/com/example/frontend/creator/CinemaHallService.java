package com.example.frontend.creator;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.example.frontend.creator.dto.HallRequest;
import com.example.frontend.creator.dto.SeatListRequest;
import com.example.frontend.creator.dto.SeatRequest;
import com.example.frontend.creator.dto.DeleteHallRequest;

public class CinemaHallService {
    private static final String BACKEND_URL = "http://localhost:8080/api/root/halls";
    private final HttpClient httpClient;
    private final Gson gson;

    public CinemaHallService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String createHall(String name) {
        try {
            HallRequest requestData = new HallRequest(name);
            String jsonBody = gson.toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                return "Server Error " + response.statusCode() + response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Cannot connect with a server.";
        }
    }

    public String generateSeats(String hallId, List<SeatRequest> selectedSeats) {
        try {
            SeatListRequest requestData = new SeatListRequest(selectedSeats, Long.parseLong(hallId));
            String jsonBody = gson.toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/hallCreator"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                return "Server Error " + response.statusCode() + response.body();
            }
        } catch (NumberFormatException e) {
            return "Hall ID must be a valid number.";
        } catch (Exception e) {
            return "Cannot connect with a server.";
        }
    }

    public String deleteHall(String hallId) {
        try {
            Long id = Long.parseLong(hallId);
            DeleteHallRequest requestData = new DeleteHallRequest(id);
            String jsonBody = gson.toJson(requestData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/" + hallId)).DELETE().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                return "Server Error " + response.statusCode() + response.body();
            }
        } catch (NumberFormatException e) {
            return "Hall ID must be a valid number.";
        } catch (Exception e) {
            return "Cannot connect with a server.";
        }
    }

}