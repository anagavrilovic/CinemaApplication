package com.example.cinema.service;

import com.example.cinema.dto.ReservationCreationDto;
import com.example.cinema.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> create(ReservationCreationDto reservationCreationDto);

    Long cancel(Long id);

    List<Reservation> findAllActive();

    Reservation findById(Long id);

}
