package com.example.cinema.service;

import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.model.Projection;

import java.util.List;

public interface ProjectionService {

    Projection create(ProjectionCreationDto projectionCreationDto);

    Long delete(Long id);

    List<Projection> findAllAvailable();

    List<Projection> findAll();

    Projection findById(Long id);
}
