package com.example.cinema.mapper;

import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.model.Projection;

import java.util.ArrayList;
import java.util.List;

public final class ProjectionMapper {

    private ProjectionMapper() {
    }

    public static List<ProjectionDto> projectionsToProjectionDtos(List<Projection> projections) {
        List<ProjectionDto> projectionDtos = new ArrayList<>();

        for(Projection projection : projections) {
            projectionDtos.add(new ProjectionDto(projection));
        }

        return projectionDtos;
    }

}
