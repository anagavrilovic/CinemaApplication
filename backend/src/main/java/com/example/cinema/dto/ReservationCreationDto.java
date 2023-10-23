package com.example.cinema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReservationCreationDto {

    @NotNull(message = "Projection id is mandatory.")
    private Long projectionId;

    @NotNull(message = "User id is mandatory.")
    private Long userId;

    @Min(value = 1, message = "Minimum number of tickets is 1.")
    private int numberOfTickets;

    public ReservationCreationDto() {
    }

    public ReservationCreationDto(Long projectionId, Long userId, int numberOfTickets) {
        this.projectionId = projectionId;
        this.userId = userId;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    @Override
    public String toString() {
        return "ReservationCreationDto{" +
                "projectionId=" + projectionId +
                ", userId=" + userId +
                ", numberOfTickets=" + numberOfTickets +
                '}';
    }

}
