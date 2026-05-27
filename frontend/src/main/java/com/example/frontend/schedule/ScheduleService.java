package com.example.frontend.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String backendUrl;

    public ScheduleService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.backendUrl = "http://localhost:8080/api/schedule/screenings";
    }

    public List<ScreeningDto> getAllScreenings() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return getObjectMapper().readValue(response.body(), new TypeReference<List<ScreeningDto>>() {});
            }
        } catch (Exception e) {
            //
        }
        return new ArrayList<>();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String getBackendUrl() {
        return backendUrl;
    }
}