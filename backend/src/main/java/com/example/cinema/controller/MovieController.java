package com.example.cinema.controller;

import com.example.cinema.dto.MovieCreationDto;
import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.model.Movie;
import com.example.cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public MovieDto create(@Valid @RequestBody MovieCreationDto movieDto) {
        Movie newMovie = movieService.save(MovieMapper.movieDtoToMovie(movieDto));
        return new MovieDto(newMovie);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<MovieDto> findAll() {
        return MovieMapper.moviesToMovieDtos(movieService.findAll());
    }

    @GetMapping("/{id}")
    public MovieDto findById(@PathVariable("id") Long id) {
        return new MovieDto(movieService.findById(id));
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return movieService.delete(id);
    }

    @PutMapping
    public MovieDto update(@Valid @RequestBody MovieForUpdateDto movie) {
        return new MovieDto(movieService.update(movie));
    }

}
