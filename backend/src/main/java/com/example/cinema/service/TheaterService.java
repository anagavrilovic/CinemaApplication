package com.example.cinema.service;

import com.example.cinema.model.Theater;

import java.util.List;

public interface TheaterService {

    List<Theater> findAll();

    Theater findById(Long id);

}
