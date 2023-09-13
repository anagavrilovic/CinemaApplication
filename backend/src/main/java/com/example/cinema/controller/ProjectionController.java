package com.example.cinema.controller;

import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.mapper.ProjectionMapper;
import com.example.cinema.service.ProjectionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("projection")
public class ProjectionController {

    private final ProjectionService projectionService;

    public ProjectionController(ProjectionService projectionService) {
        this.projectionService = projectionService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ProjectionDto create(@Valid @RequestBody ProjectionCreationDto projectionCreationDto) {
        return new ProjectionDto(projectionService.create(projectionCreationDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return projectionService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/available")
    public List<ProjectionDto> findAllAvailableProjections() {
        return ProjectionMapper.projectionsToProjectionDtos(projectionService.findAllAvailable());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public List<ProjectionDto> findAll() {
        return ProjectionMapper.projectionsToProjectionDtos(projectionService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ProjectionDto findById(@PathVariable("id") Long id) {
        return new ProjectionDto(projectionService.findById(id));
    }

}
