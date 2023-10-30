package com.example.cinema.unit.controller;

import com.example.cinema.arguments_provider.IdAndNameArgumentsProvider;
import com.example.cinema.arguments_provider.IdArgumentsProvider;
import com.example.cinema.constants.MovieConstants;
import com.example.cinema.controller.MovieController;
import com.example.cinema.dto.MovieCreationDto;
import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.MovieForUpdateDto;
import com.example.cinema.model.Movie;
import com.example.cinema.service.MovieService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService mockMovieService;

    @InjectMocks
    private MovieController movieController;

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_CreateNewMovie(Long id, String name) {
        MovieCreationDto request = new MovieCreationDto();
        request.setName(name);

        Movie movie = MovieConstants.getSimpleMovie(id, name);
        MovieDto expected = MovieConstants.getSimpleMoviedto(id, name);

        when(mockMovieService.save(any())).thenReturn(movie);

        MovieDto actual = movieController.create(request);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ListAllMovies(Long id, String name) {
        Movie movie = MovieConstants.getSimpleMovie(id, name);
        MovieDto movieDto = MovieConstants.getSimpleMoviedto(id, name);

        List<Movie> movies = List.of(movie);
        List<MovieDto> expected = List.of(movieDto);

        when(mockMovieService.findAll()).thenReturn(movies);

        List<MovieDto> actual = movieController.findAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0).getId(), actual.get(0).getId()),
                () -> assertEquals(expected.get(0).getName(), actual.get(0).getName())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_ReturnMovieDtoWithGivenId(Long id, String name) {
        Movie movie = MovieConstants.getSimpleMovie(id, name);
        MovieDto expected = MovieConstants.getSimpleMoviedto(id, name);

        when(mockMovieService.findById(any())).thenReturn(movie);

        MovieDto actual = movieController.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdArgumentsProvider.class)
    void Should_DeleteMovieWithGivenId(Long id) {
        Long expected = id;

        when(mockMovieService.delete(any())).thenReturn(expected);

        Long actual = movieController.delete(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(IdAndNameArgumentsProvider.class)
    void Should_UpdateMovie(Long id, String name) {
        MovieForUpdateDto request = new MovieForUpdateDto();
        request.setId(id);
        request.setName(name);

        Movie movie = MovieConstants.getSimpleMovie(id, name);
        MovieDto expected = MovieConstants.getSimpleMoviedto(id, name);

        when(mockMovieService.update(any())).thenReturn(movie);

        MovieDto actual = movieController.update(request);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }
}
