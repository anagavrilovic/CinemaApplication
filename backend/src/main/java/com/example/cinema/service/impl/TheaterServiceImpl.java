package com.example.cinema.service.impl;

import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.model.Theater;
import com.example.cinema.repository.TheaterRepository;
import com.example.cinema.service.TheaterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterServiceImpl(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Theater> findAll() {
        return theaterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Theater findById(Long id) {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no theater with this id."));
    }
}
