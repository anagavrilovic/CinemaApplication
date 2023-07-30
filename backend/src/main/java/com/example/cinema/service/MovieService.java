package com.example.cinema.service;

import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.model.Movie;

import java.util.List;

public interface MovieService {

    Movie save(Movie movieDtoToMovie);

    List<Movie> findAll();

    Movie findById(Long id);

    Long delete(Long id);

    Movie update(MovieForUpdateDto movie);

}
