package com.example.cinema.mapper;

import com.example.cinema.dto.ReservationDto;
import com.example.cinema.model.Reservation;

import java.util.List;

public final class ReservationMapper {

    private ReservationMapper() {}

    public static List<ReservationDto> reservationsToReservationDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationDto::new)
                .toList();
    }
}
