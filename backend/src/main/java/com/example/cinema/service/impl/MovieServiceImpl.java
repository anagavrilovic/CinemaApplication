package com.example.cinema.service.impl;

import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.exception.BusinessLogicException;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.exception.ObjectAlreadyExistsException;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        assertMovieNotExists(movie);
        return movieRepository.save(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        return movieRepository.findByDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Long id) {
        return movieRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no movie with this id."));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Movie movieForDeleting = movieRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no movie with this id."));

        if(movieHasActiveProjections(movieForDeleting)) {
            throw new BusinessLogicException("Cannot delete a movie that has an active projection.");
        }

        movieRepository.deleteLogically(movieForDeleting.getId());
        return id;
    }

    private boolean movieHasActiveProjections(Movie movie) {
        return movie.getProjections()
                .stream()
                .anyMatch(p -> !p.getDeleted() && !hasProjectionPassed(p));
    }

    private boolean hasProjectionPassed(Projection projection) {
        return projection.getEndDateAndTime().isBefore(LocalDateTime.now());
    }

    @Override
    @Transactional
    public Movie update(MovieForUpdateDto movie) {
        Movie movieForUpdating = movieRepository.findByIdAndDeletedFalse(movie.getId())
                .orElseThrow(() -> new EntityNotFoundException("There is no movie with this id."));

        if(movieHasActiveProjections(movieForUpdating)) {
            throw new BusinessLogicException("Cannot update a movie that has an active projection.");
        }

        movieForUpdating.setName(movie.getName());
        movieForUpdating.setLength(movie.getLength());
        movieForUpdating.setDescription(movie.getDescription());
        movieForUpdating.setDirector(movie.getDirector());
        movieForUpdating.setGenres(movie.getGenres());

        return movieRepository.save(movieForUpdating);
    }

    private void assertMovieNotExists(Movie movie) {
        movieRepository.findByNameAndDeletedFalse(movie.getName())
                .ifPresent(m -> {
                    throw new ObjectAlreadyExistsException("This movie already exists.");
                });
    }
}
