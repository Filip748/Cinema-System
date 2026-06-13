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

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public Gson getGson() {
        return gson;
    }

    public String register(String username, String password) {
        try {
            AuthRequest requestData = new AuthRequest(username, password);
            String jsonBody = getGson().toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Cannot connect with a server";
        }
    }

    public AuthResponseDto login(String username, String password) {
        try {
            AuthRequest requestData = new AuthRequest(username, password);
            String jsonBody = getGson().toJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return getGson().fromJson(response.body(), AuthResponseDto.class);
            } else {
                AuthResponseDto errorDto = new AuthResponseDto();
                errorDto.setMessage("Incorrect login or password");
                return errorDto;
            }

        } catch (Exception e) {
            AuthResponseDto errorDto = new AuthResponseDto();
            errorDto.setMessage("Cannot connect with a server");
            return errorDto;
        }
    }
}