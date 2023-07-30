package com.example.cinema.dto;

import com.example.cinema.model.Projection;

import java.time.LocalDateTime;

public class ProjectionDto {

    private Long id;

    private MovieDto movieDto;

    private TheaterDto theaterDto;

    private LocalDateTime startDateAndTime;

    private int ticketPrice;

    private int numberOfAvailableSeats;

    public ProjectionDto() {
    }

    public ProjectionDto(Long id, MovieDto movieDto, TheaterDto theaterDto, LocalDateTime startDateAndTime, int ticketPrice, int numberOfAvailableSeats) {
        this.id = id;
        this.movieDto = movieDto;
        this.theaterDto = theaterDto;
        this.startDateAndTime = startDateAndTime;
        this.ticketPrice = ticketPrice;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public ProjectionDto(Projection projection) {
        this.id = projection.getId();
        this.movieDto = new MovieDto(projection.getMovie());
        this.theaterDto = new TheaterDto(projection.getTheater());
        this.startDateAndTime = projection.getStartDateAndTime();
        this.ticketPrice = projection.getTicketPrice();
        this.numberOfAvailableSeats = projection.getNumberOfAvailableSeats();
    }

    public Long getId() {
        return id;
    }

    public MovieDto getMovieDto() {
        return movieDto;
    }

    public TheaterDto getTheaterDto() {
        return theaterDto;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    @Override
    public String toString() {
        return "ProjectionDto{" +
                "id=" + id +
                ", movieDto=" + movieDto +
                ", theaterDto=" + theaterDto +
                ", startDateAndTime=" + startDateAndTime +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

}
