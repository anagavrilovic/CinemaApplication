package com.example.cinema.constants;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

public class MovieConstants {

    public static final int MOVIE_LENGTH = 150;
    public static final LocalDateTime THREE_DAYS_BEFORE = LocalDateTime.now().minusDays(3);
    public static final LocalDateTime TWO_DAYS_BEFORE = LocalDateTime.now().minusDays(2);
    public static final LocalDateTime TWO_DAYS_AFTER = LocalDateTime.now().plusDays(2);
    public static final LocalDateTime TEN_MINUTES_BEFORE = LocalDateTime.now().minusMinutes(10);

    public static final int DB_COUNT = 7;
    public static final Long DB_ID = 1L;
    public static final String DB_DIRECTOR = "Christopher Nolan";
    public static final Integer DB_LENGTH = 148;
    public static final String DB_NAME = "Inception";
    public static final Long DB_NON_EXISTING_ID = 150L;
    public static final String DB_DESCRIPTION = "Some description";
    public static final Long DB_MOVIE_ID_WITH_ACTIVE_PROJECTION = 7L;


    public static Movie getMovieWithAllPassedProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(MOVIE_LENGTH);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(THREE_DAYS_BEFORE);
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(TWO_DAYS_BEFORE);
        projection2.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2));
        return expected;
    }

    public static Movie getMovieWithActiveProjections(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(MOVIE_LENGTH);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(THREE_DAYS_BEFORE);
        projection1.setMovie(expected);

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(TWO_DAYS_BEFORE);
        projection2.setMovie(expected);

        Projection projection3 = new Projection();
        projection3.setStartDateAndTime(TWO_DAYS_AFTER);
        projection3.setMovie(expected);

        expected.setProjections(List.of(projection1, projection2, projection3));
        return expected;
    }

    public static Movie getMovieWithCurrentlyActiveProjection(Long id) {
        Movie expected = new Movie();
        expected.setId(id);
        expected.setDeleted(false);
        expected.setLength(MOVIE_LENGTH);

        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(TEN_MINUTES_BEFORE);
        projection1.setMovie(expected);

        expected.setProjections(List.of(projection1));
        return expected;
    }

    public static Movie getSimpleMovie(Long id, String name) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setDeleted(false);

        return movie;
    }

    public static MovieDto getSimpleMoviedto(Long id, String name) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setName(name);

        return movieDto;
    }

}
