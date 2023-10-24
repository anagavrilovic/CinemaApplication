package com.example.cinema.dto;

import com.example.cinema.model.Movie;
import com.example.cinema.model.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class MovieCreationDto {

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "Director is mandatory.")
    private String director;

    @NotEmpty(message = "At least one genre is mandatory.")
    private List<Genre> genres = new ArrayList<>();

    @NotNull(message = "Length is mandatory.")
    @Positive(message = "Length cannot be negative.")
    private Integer length;

    @NotBlank(message = "Description is mandatory.")
    private String description;

    public MovieCreationDto() {}

    public MovieCreationDto(String name, String director, List<Genre> genres, Integer length, String description) {
        this.name = name;
        this.director = director;
        this.genres = genres;
        this.length = length;
        this.description = description;
    }

    public MovieCreationDto(Movie movie) {
        this.name = movie.getName();
        this.director = movie.getDirector();
        this.genres = movie.getGenres();
        this.length = movie.getLength();
        this.description = movie.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Integer getLength() {
        return length;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", genres=" + genres +
                ", length=" + length +
                ", description='" + description + '\'' +
                '}';
    }

}
