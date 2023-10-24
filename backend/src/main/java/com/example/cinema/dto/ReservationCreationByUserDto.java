package com.example.cinema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReservationCreationByUserDto {

    @NotNull(message = "Projection id is mandatory.")
    private Long projectionId;

    @Min(value = 1, message = "Minimum number of tickets is 1.")
    private int numberOfTickets;

    public ReservationCreationByUserDto() {
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    @Override
    public String toString() {
        return "ReservationCreationDto{" +
                "projectionId=" + projectionId +
                ", numberOfTickets=" + numberOfTickets +
                '}';
    }

}
