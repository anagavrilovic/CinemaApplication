package com.example.cinema.constants;

import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;
import com.example.cinema.model.Theater;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectionConstants {

    public static final Long MOVIE_ID = 1L;
    public static final Long THEATER_ID = 1L;
    public static final Integer TICKET_PRICE = 200;
    public static final Integer MOVIE_LENGTH = 150;
    public static final LocalDateTime ONE_DAY_BEFORE_NOW = LocalDateTime.now().minusDays(1);
    public static final LocalDateTime ONE_DAY_AFTER_NOW = LocalDateTime.now().plusDays(1);

    public static final Long DB_ID = 1L;
    public static final Integer DB_COUNT = 3;
    public static final Long DB_MOVIE_ID = 7L;
    public static final Long DB_THEATER_ID = 2L;
    public static final Integer DB_TICKET_PRICE = 500;

    public static Movie getMovieForProjection(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setLength(MOVIE_LENGTH);

        return movie;
    }

    public static Theater getTheaterForProjection(Long id) {
        Theater theater = new Theater();
        theater.setId(id);

        return theater;
    }


    public static List<Projection> getProjectionsThatDoNotOverlap(LocalDateTime startDateAndTime, Integer length) {
        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(startDateAndTime.plusDays(2));

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(startDateAndTime.plusDays(5));

        Movie movie = new Movie();
        movie.setLength(length);

        projection1.setMovie(movie);
        projection2.setMovie(movie);

        return List.of(projection1, projection2);
    }

    public static List<Projection> getProjectionsThatDoOverlap(LocalDateTime startDateAndTime, Integer length) {
        Projection projection1 = new Projection();
        projection1.setStartDateAndTime(startDateAndTime.plusDays(2));

        Projection projection2 = new Projection();
        projection2.setStartDateAndTime(startDateAndTime.minusMinutes(5));

        Movie movie = new Movie();
        movie.setLength(length);

        projection1.setMovie(movie);
        projection2.setMovie(movie);

        return List.of(projection1, projection2);
    }

    public static Projection getSimpleProjection(Long id) {
        Projection projection = new Projection();
        projection.setId(id);
        projection.setMovie(getMovieForProjection(id));
        projection.setTheater(getTheaterForProjection(id));

        return projection;
    }

    public static ProjectionDto getSimpleProjectionDto(Long id) {
        ProjectionDto projectionDto = new ProjectionDto();
        projectionDto.setId(id);

        return projectionDto;
    }
}
