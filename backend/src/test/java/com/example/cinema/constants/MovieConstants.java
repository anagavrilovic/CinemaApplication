package com.example.cinema.constants;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;

import java.time.LocalDateTime;
import java.util.List;

public class MovieConstants {

    public static Movie getMovieWithAllPassedProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusDays(3));
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(LocalDateTime.now().minusDays(2));
        projection2.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2));
        return expected;
    }

    public static Movie getMovieWithActiveProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusDays(3));
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(LocalDateTime.now().minusDays(2));
        projection2.setMovie(expected);

        Projection projection3 = new Projection();
        projection3.setStartDateAndTime(LocalDateTime.now().plusDays(2));
        projection3.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2, projection3));
        return expected;
    }

    public static Movie getMovieWithCurrentlyActiveProjection(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(150);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(LocalDateTime.now().minusMinutes(10));
        projection1.setMovie(expected);

        expected.setProjections(List.of(projection1));
        return expected;
    }

    public static Movie getSimpleMovie(Long id, String name) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);

        return movie;
    }

    public static MovieDto getSimpleMoviedto(Long id, String name) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setName(name);

        return movieDto;
    }

}
