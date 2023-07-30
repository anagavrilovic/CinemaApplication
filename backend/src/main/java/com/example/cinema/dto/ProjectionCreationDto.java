package com.example.cinema.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class ProjectionCreationDto {

    @NotNull(message = "Movie id is mandatory.")
    private Long movieId;

    @NotNull(message = "Theater id is mandatory.")
    private Long theaterId;

    @NotNull(message = "Start date and time is mandatory.")
    @Future(message = "Start date and time must be in the future.")
    private LocalDateTime startDateAndTime;

    @PositiveOrZero(message = "Ticket price cannot be negative.")
    private int ticketPrice;

    public ProjectionCreationDto() {
    }

    public ProjectionCreationDto(Long movieId, Long theaterId, LocalDateTime startDateAndTime, int ticketPrice) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startDateAndTime = startDateAndTime;
        this.ticketPrice = ticketPrice;
    }

    public Long getMovieId() {
        return movieId;
    }

    public Long getTheaterId() {
        return theaterId;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "ProjectionDto{" +
                ", movieId=" + movieId +
                ", theaterId=" + theaterId +
                ", startDateAndTime=" + startDateAndTime +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

}
