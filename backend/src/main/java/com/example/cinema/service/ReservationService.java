package com.example.cinema.service;

import com.example.cinema.dto.ReservationCreationByUserDto;
import com.example.cinema.dto.ReservationCreationDto;
import com.example.cinema.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> create(ReservationCreationDto reservationCreationDto);

    Long cancel(Long id);

    List<Reservation> findAllActive();

    Reservation findById(Long id);

    List<Reservation> findAllActiveByUser(String name);

    List<Reservation> createByUser(ReservationCreationByUserDto reservationCreationDto, String name);
}
