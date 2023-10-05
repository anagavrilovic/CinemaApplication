package com.example.cinema.controller;

import com.example.cinema.dto.TheaterDto;
import com.example.cinema.mapper.TheaterMapper;
import com.example.cinema.service.TheaterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("theater")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public List<TheaterDto> findAll() {
        return TheaterMapper.theatersToTheaterDtos(theaterService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public TheaterDto findById(@PathVariable("id") Long id) {
        return new TheaterDto(theaterService.findById(id));
    }

}
