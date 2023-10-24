package com.example.cinema.constants;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.ProjectionDto;
import com.example.cinema.dto.TheaterDto;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Projection;
import com.example.cinema.model.Theater;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectionConstants {

    public static Movie getMovieForProjection(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setLength(150);

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
