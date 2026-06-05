package com.example.frontend.ticketSales;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Arrays;

public class TicketService{

    private static final String BASE_URL = "http://localhost:8080/api/tickets";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<SeatDto> getAvailableSeats(Long screeningId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/screening/" + screeningId + "/seats")).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            SeatDto[] seatsArray = objectMapper.readValue(response.body(), SeatDto[].class);
            return Arrays.asList(seatsArray);
        } else {
            throw new RuntimeException("Error: Can not access seats " + response.body());
        }
    }

    public String sendTicketPurchase(TicketDto ticketDto) throws Exception {
        String requestBody = objectMapper.writeValueAsString(ticketDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/buy"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error with saving tickets:  " + response.statusCode());
        }
    }
}