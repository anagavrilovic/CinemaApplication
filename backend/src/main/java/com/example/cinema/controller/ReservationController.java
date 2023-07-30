package com.example.cinema.controller;

import com.example.cinema.dto.ReservationCreationDto;
import com.example.cinema.dto.ReservationDto;
import com.example.cinema.mapper.ReservationMapper;
import com.example.cinema.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public List<ReservationDto> create(@Valid @RequestBody ReservationCreationDto reservationCreationDto) {
        return ReservationMapper.reservationsToReservationDtos(reservationService.create(reservationCreationDto));
    }

    @PutMapping("/{id}/cancel")
    public Long cancel(@PathVariable("id") Long id) {
        return reservationService.cancel(id);
    }

    @GetMapping
    public List<ReservationDto> findAllActive() {
        return ReservationMapper.reservationsToReservationDtos(reservationService.findAllActive());
    }

    @GetMapping("/{id}")
    public ReservationDto findById(@PathVariable("id") Long id) {
        return new ReservationDto(reservationService.findById(id));
    }

}
