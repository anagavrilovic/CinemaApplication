package com.example.cinema.dto;

import com.example.cinema.model.Reservation;

public class ReservationDto {

    private Long id;

    private UserDto userDto;

    private ProjectionDto projectionDto;

    public ReservationDto() {
    }

    public ReservationDto(Long id, UserDto userDto, ProjectionDto projectionDto) {
        this.id = id;
        this.userDto = userDto;
        this.projectionDto = projectionDto;
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.projectionDto = new ProjectionDto(reservation.getProjection());
        this.userDto = new UserDto(reservation.getUser());
    }

    public Long getId() {
        return id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public ProjectionDto getProjectionDto() {
        return projectionDto;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "id=" + id +
                ", userDto=" + userDto +
                ", projectionDto=" + projectionDto +
                '}';
    }

}
