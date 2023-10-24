package com.example.cinema.dto;

import com.example.cinema.model.Movie;
import com.example.cinema.model.enums.Genre;

import java.util.ArrayList;
import java.util.List;

public class MovieDto {

    private Long id;

    private String name;

    private String director;

    private List<Genre> genres = new ArrayList<>();

    private Integer length;

    private String description;

    public MovieDto() {}

    public MovieDto(String name, String director, List<Genre> genres, Integer length, String description) {
        this.name = name;
        this.director = director;
        this.genres = genres;
        this.length = length;
        this.description = description;
    }

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.director = movie.getDirector();
        this.genres = movie.getGenres();
        this.length = movie.getLength();
        this.description = movie.getDescription();
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", genres=" + genres +
                ", length=" + length +
                ", description='" + description + '\'' +
                '}';
    }

}
