package com.example.frontend.login;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private static final String BACKEND_URL = "http://localhost:8080/api/auth";
    private final HttpClient httpClient;
    private final Gson gson;

    public AuthService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String register(String username, String password) {
        try {
            AuthRequest requestData = new AuthRequest(username, password);
            String jsonBody = gson.toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Cannot connect with a server.";
        }
    }

    public String login(String username, String password) {
        try {
            AuthRequest requestData = new AuthRequest(username, password);
            String jsonBody = gson.toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            return "Cannot connect with a server.";
        }
    }
}
