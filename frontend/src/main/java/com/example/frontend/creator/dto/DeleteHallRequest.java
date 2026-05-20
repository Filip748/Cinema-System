package com.example.frontend.creator.dto;

public class DeleteHallRequest {
    private final Long hallId;

    public DeleteHallRequest(Long hallId) {
        this.hallId = hallId;
    }
    public Long getHallId() { return hallId; }
}
