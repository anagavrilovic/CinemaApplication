package com.example.cinema.service.impl;

import com.example.cinema.dto.ProjectionCreationDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.model.Projection;
import com.example.cinema.repository.ProjectionRepository;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.ProjectionService;
import com.example.cinema.service.TheaterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectionServiceImpl implements ProjectionService {

    private final ProjectionRepository projectionRepository;

    private final MovieService movieService;

    private final TheaterService theaterService;

    public ProjectionServiceImpl(ProjectionRepository projectionRepository, MovieService movieService, TheaterService theaterService) {
        this.projectionRepository = projectionRepository;
        this.movieService = movieService;
        this.theaterService = theaterService;
    }

    @Override
    @Transactional
    public Projection create(ProjectionCreationDto projectionDto) {
        if(projectionDto.getStartDateAndTime().isBefore(LocalDateTime.now())) {
            throw new BusinessLogicException("Cannot create projection in the past.");
        }

        Projection newProjection = new Projection(
                movieService.findById(projectionDto.getMovieId()),
                theaterService.findById(projectionDto.getTheaterId()),
                projectionDto.getStartDateAndTime(),
                projectionDto.getTicketPrice());

        isOverlappingWithExistingProjection(newProjection);

        projectionRepository.save(newProjection);
        return newProjection;
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Projection projectionForDeleting = projectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no projection with this id."));

        projectionRepository.deleteLogically(projectionForDeleting.getId());
        return projectionForDeleting.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projection> findAllAvailable() {
        return projectionRepository.findAllAvailable();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projection> findAll() {
        return projectionRepository.findAll();
    }

    @Override
    public Projection findById(Long id) {
        return projectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no projection with this id."));
    }

    private void isOverlappingWithExistingProjection(Projection newProjection) {
        List<Projection> theaterProjections = projectionRepository.findByTheaterIdAndDeletedFalse(newProjection.getTheater().getId());

        for (Projection existingProjection : theaterProjections) {
            if(newProjection.getStartDateAndTime().isBefore(existingProjection.getEndDateAndTime())
                    && newProjection.getEndDateAndTime().isAfter(existingProjection.getStartDateAndTime())) {
                throw new BusinessLogicException("There is already a projection in this theater at this time.");
            }
        }
    }

}
